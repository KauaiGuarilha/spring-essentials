package com.example.springessentials.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.springessentials.model.domain.EValidation;
import com.example.springessentials.model.entity.Student;
import com.example.springessentials.model.exceptions.ResourceNotFoundException;
import com.example.springessentials.model.repository.StudentRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class StudentServiceTest {

    @Mock private StudentRepository repository;

    @InjectMocks StudentService service;

    @Test
    @DisplayName("Should save student.")
    public void shouldSaveStudent() throws Exception {
        Student studentRequest =
                Student.builder()
                        .id(null)
                        .name("kauai")
                        .email("kauai@kauai.com")
                        .build();

        Student student = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        doReturn(student).when(repository).save(studentRequest);

        Student studentResponse = service.saveUpdate(studentRequest);

        Assert.assertEquals(studentResponse.getId(), UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"));
        Assert.assertEquals(studentResponse.getName(), studentRequest.getName());
        Assert.assertEquals(studentResponse.getEmail(), studentRequest.getEmail());

        verify(repository, times(1)).save(studentRequest);
    }

    @Test
    @DisplayName("Should exception when save a null student.")
    public void shouldExceptionWhenSaveNullStudent() {
        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> service.saveUpdate(null));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should exception when update a null student.")
    public void shouldExceptionWhenUpdateNullStudent() throws Exception {
        Student studentRequest =
                Student.builder()
                        .id(UUID.fromString("de1bce8f-b24d-43b9-a2c7-de792c53cc09"))
                        .build();

        doReturn(null).when(repository).findById(studentRequest.getId());

        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> service.saveUpdate(studentRequest));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should update student.")
    public void shouldUpdateStudent() throws Exception {
        Student studentRequest = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai12")
                .email("kauai@kauai.com")
                .build();

        doReturn(Optional.of(studentRequest)).when(repository).findById(studentRequest.getId());
        doReturn(studentRequest).when(repository).save(studentRequest);

        Student studentResponse = service.saveUpdate(studentRequest);

        Assert.assertEquals(studentResponse.getId(), studentRequest.getId());
        Assert.assertEquals(studentResponse.getName(), studentRequest.getName());
        Assert.assertEquals(studentResponse.getEmail(), studentRequest.getEmail());

        verify(repository, times(1)).findById(studentRequest.getId());
        verify(repository, times(1)).save(studentRequest);
    }

    @Test
    @DisplayName("Should return student by id.")
    public void shouldReturnStudentById() throws Exception {
        Student studentRequest = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai12")
                .email("kauai@kauai.com")
                .build();

        Student student = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai12")
                .email("kauai@kauai.com")
                .build();

        doReturn(student).when(repository).findByStudentId(studentRequest.getId());

        Student studentResponse = service.studentuById(studentRequest.getId().toString());

        Assert.assertEquals(studentResponse.getId(), studentRequest.getId());
        Assert.assertEquals(studentResponse.getName(), studentRequest.getName());
        Assert.assertEquals(studentResponse.getEmail(), studentRequest.getEmail());

        verify(repository, times(1)).findByStudentId(studentRequest.getId());
    }

    @Test
    @DisplayName("Should return exception when verify students null.")
    public void shouldReturnExceptionWhenVerifyStudentNull() {
        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> service.studentuById(null));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should return ResourceNotFoundException when students by id null.")
    public void shouldResourceNotFoundExceptionWhenStudentByIdNull() throws Exception {
        Student studentRequest = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai12")
                .email("kauai@kauai.com")
                .build();

        doReturn(null).when(repository).findByStudentId(studentRequest.getId());

        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> service.studentuById(studentRequest.getId().toString()));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "Student not found for ID");
    }

    @Test
    @DisplayName("Should return student by name.")
    public void shouldReturnStudentByName() throws Exception {
        List<Student> students = new ArrayList<>();
        Student student = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai12")
                .email("kauai@kauai.com")
                .build();

        students.add(student);

        doReturn(students).when(repository).findByNameIgnoreCaseContaining(student.getName());

        List<Student> studentResponse = service.studentByName(student.getName());

        Assert.assertEquals(studentResponse.get(0).getId(), UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"));
        Assert.assertEquals(studentResponse.get(0).getName(), "kauai12");
        Assert.assertEquals(studentResponse.get(0).getEmail(), "kauai@kauai.com");

        verify(repository, times(1)).findByNameIgnoreCaseContaining(student.getName());
    }

    @Test
    @DisplayName("Should return exception when verify name students null.")
    public void shouldReturnExceptionWhenVerifyNameStudentNull() {
        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () ->service.studentByName(null));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should delete student by id.")
    public void shouldDeleteStudentById() throws Exception {
        Student studentRequest = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .build();

        doNothing().when(repository).deleteById(studentRequest.getId());

        service.delete(studentRequest.getId().toString());

        verify(repository, times(1)).deleteById(studentRequest.getId());
    }

    @Test
    @DisplayName("Should return exception when delete students by id null.")
    public void shouldReturnExceptionWhenDeleteStudentByIdNull() {
        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> service.delete(null));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "An unidentified problem has occurred.");
    }
}
