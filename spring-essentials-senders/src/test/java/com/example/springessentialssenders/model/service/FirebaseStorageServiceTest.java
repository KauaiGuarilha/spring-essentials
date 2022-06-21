package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.exceptions.EssentialsRuntimeException;
import com.example.springessentialssenders.model.exceptions.FirebaseStorageException;
import com.example.springessentialssenders.model.repository.FileLoadRepository;
import com.example.springessentialssenders.model.repository.StudentRepository;
import com.example.springessentialssenders.model.utils.InitializationUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FirebaseStorageServiceTest {

    @Mock private StudentRepository studentRepository;
    @Mock private FileLoadRepository fileLoadRepository;
    @Mock private InitializationUtils initializationUtils;

    @InjectMocks FirebaseStorageService service;

    @Test
    @DisplayName("Should load file to storage google APIs.")
    public void shouldLoadFileToStorageGoogleAPIs() throws IOException {
        doReturn("https://").when(initializationUtils).getStorageGoogleApis();

        String url = service.upload("name", "text/plain", recoverMockMultipartFile("name"));

        assertEquals(url, "https://");

        verify(initializationUtils, times(1)).getStorageGoogleApis();
    }

    @Test
    @DisplayName("Should exception on load file to storage google APIs.")
    public void shouldExceptionOnLoadFileToStorageGoogleAPIs2() {
        FirebaseStorageException thrown =
                assertThrows(FirebaseStorageException.class,
                        () -> service.upload("name", "text/plain", null));

        assertEquals(thrown.getClass(), FirebaseStorageException.class);
        assertEquals(thrown.getMessage(), "There was a problem creating the Blob on upload to Storage google APIs.");
    }

    @Test
    @DisplayName("Should exception on load file to storage google APIs.")
    public void shouldExceptionOnLoadFileToStorageGoogleAPIs() throws IOException {
        MockMultipartFile mockMultipartFile = recoverMockMultipartFile("text");

        EssentialsRuntimeException thrown =
                assertThrows(EssentialsRuntimeException.class,
                        () -> service.upload("", "", mockMultipartFile));

        assertEquals(thrown.getClass(), EssentialsRuntimeException.class);
        assertEquals(thrown.getMessage(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should exception on load file to database.")
    public void shouldExceptionOnLoadFileToDatabase() {
        EssentialsRuntimeException thrown =
                assertThrows(EssentialsRuntimeException.class,
                        () -> service.uploadDatabase("", "", null, ""));

        assertEquals(thrown.getClass(), EssentialsRuntimeException.class);
        assertEquals(thrown.getMessage(), "An unidentified problem has occurred.");
    }

    private MockMultipartFile recoverMockMultipartFile(String nomeFile) throws IOException {
        return new MockMultipartFile(
                nomeFile,
                nomeFile,
                "text/plain",
                IOUtils.toByteArray(
                        new FileInputStream(
                                ResourceUtils.getFile("classpath:application.properties"))));
    }
}
