package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.dto.StudentFileDTOResponse;
import com.example.springessentialssenders.model.entity.FileLoad;
import com.example.springessentialssenders.model.entity.Student;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    @Autowired StudentRepository studentRepository;
    @Autowired FileLoadRepository fileLoadRepository;
    @Autowired InitializationUtils initializationUtils;

    public String upload(String fName, String mimiType, MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        System.out.println(bucket);

        byte[] bytes = file.getBytes();

        Blob blob = bucket.create(fName, bytes, mimiType);

        // URL public
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return String.format(initializationUtils.getStorageGoogleApis(), bucket.getName(), fName);
    }

    public StudentFileDTOResponse uploadDatabase(
            String fName, String mimiType, MultipartFile file, String idRoom) throws IOException {

        Optional<Student> student = studentRepository.findById(UUID.fromString(idRoom));

        if (!student.isPresent()) throw new RuntimeException("Could not find any student.");

        Bucket bucket = StorageClient.getInstance().bucket();
        byte[] bytes = file.getBytes();

        Blob blob = bucket.create(fName, bytes, mimiType);

        // URL public
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        FileLoad fileLoad =
                FileLoad.builder()
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
                        String.format(
                                initializationUtils.getStorageGoogleApis(),
                                bucket.getName(),
                                fName))
                .build();
    }

    @PostConstruct
    private void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream inputStream =
                    FirebaseStorageService.class.getResourceAsStream("/serviceAccountKey.json");

            System.out.println(inputStream);

            FirebaseOptions options =
                    new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(inputStream))
                            .setStorageBucket(initializationUtils.getStorageBucket())
                            .setDatabaseUrl(initializationUtils.getDatabaseUrl())
                            .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
