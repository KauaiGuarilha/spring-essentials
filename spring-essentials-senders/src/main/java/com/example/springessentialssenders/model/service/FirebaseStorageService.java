package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.StudentFileDTOResponse;
import com.example.springessentialssenders.model.entity.FileLoad;
import com.example.springessentialssenders.model.entity.Student;
import com.example.springessentialssenders.model.exceptions.EssentialsRuntimeException;
import com.example.springessentialssenders.model.exceptions.FirebaseStorageException;
import com.example.springessentialssenders.model.exceptions.StudentNotFoundException;
import com.example.springessentialssenders.model.repository.FileLoadRepository;
import com.example.springessentialssenders.model.repository.StudentRepository;
import com.example.springessentialssenders.model.utils.InitializationUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FirebaseStorageService {

    @Autowired StudentRepository studentRepository;
    @Autowired FileLoadRepository fileLoadRepository;
    @Autowired InitializationUtils initializationUtils;

    public String upload(String fName, String mimiType, MultipartFile file) throws IOException {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            System.out.println(bucket);

            if (Objects.isNull(file))
                throw new FirebaseStorageException(EValidation.BLOB_STORAGE_NOT_FOUND);//TODO Remover description

            byte[] bytes = file.getBytes();

            Blob blob = bucket.create(fName, bytes, mimiType);

            blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)); // URL public

            return String.format(initializationUtils.getStorageGoogleApis(), bucket.getName(), fName);
        } catch (IOException | FirebaseStorageException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying upload to Storage google APIs.", ExceptionUtils.getStackTrace(e));
            throw new EssentialsRuntimeException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public StudentFileDTOResponse uploadDatabase(
            String fName, String mimiType, MultipartFile file, String idStudent) throws IOException {
        try {
            Optional<Student> student = studentRepository.findById(UUID.fromString(idStudent));

            if (!student.isPresent())
                throw new StudentNotFoundException(EValidation.STUDENT_NOT_FOUND_FOR_ID, idStudent);

            Bucket bucket = StorageClient.getInstance().bucket();
            byte[] bytes = file.getBytes();

            Blob blob = bucket.create(fName, bytes, mimiType);

            if (Objects.isNull(blob))
                throw new FirebaseStorageException(EValidation.BLOB_DATABASE_NOT_FOUND);

            blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            FileLoad fileLoad = FileLoad.builder()
                            .student(student.get())
                            .pathFile(
                                    String.format(
                                            initializationUtils.getStorageGoogleApis(),
                                            bucket.getName(),
                                            fName))
                            .build();

            fileLoadRepository.save(fileLoad);

            return StudentFileDTOResponse.builder()
                    .base64(Base64.getEncoder().encodeToString(file.getBytes()))
                    .url(
                            String.format(initializationUtils.getStorageGoogleApis(),
                                    bucket.getName(),
                                    fName))
                    .build();
        } catch (IOException | StudentNotFoundException | FirebaseStorageException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying upload to Database.", ExceptionUtils.getStackTrace(e));
            throw new EssentialsRuntimeException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    @PostConstruct
    private void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream inputStream =
                    FirebaseStorageService.class.getResourceAsStream("/serviceAccountKey.json");

            System.out.println(inputStream);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setStorageBucket(initializationUtils.getStorageBucket())
                    .setDatabaseUrl(initializationUtils.getDatabaseUrl())
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
