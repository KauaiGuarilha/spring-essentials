package com.example.springessentialssenders.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.springessentialssenders.model.builder.StudentBuilder;
import com.example.springessentialssenders.model.dto.StudentDTO;
import com.example.springessentialssenders.model.entity.Student;
import com.example.springessentialssenders.model.exceptions.ResourceNotFoundException;
import com.example.springessentialssenders.model.queue.senders.QueueStudentSender;
import com.example.springessentialssenders.model.repository.StudentRepository;
import org.junit.Assert;
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

    @Mock private StudentBuilder parser;
    @Mock private StudentRepository repository;
    @Mock private QueueStudentSender queueStudentSender;

    @InjectMocks StudentService service;

    @Test
    @DisplayName("Should save student.")
    public void shouldSaveStudent() {
        StudentDTO studentDTO = StudentDTO.builder()
                .id(null)
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        StudentDTO studentDTOFinal = StudentDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        Student student = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        doNothing().when(queueStudentSender).sendMessage(studentDTO);
        doReturn(student).when(parser).toStudent(studentDTO);
        doReturn(student).when(repository).save(student);
        doReturn(studentDTOFinal).when(parser).dtoResponse(student);

        StudentDTO studentDTOResponse = service.saveUpdate(studentDTO);

        assertEquals(studentDTOResponse.getId(), studentDTOFinal.getId());
        assertEquals(studentDTOResponse.getName(), studentDTOFinal.getName());
        assertEquals(studentDTOResponse.getEmail(), studentDTOFinal.getEmail());

        verify(queueStudentSender, times(1)).sendMessage(studentDTO);
        verify(parser, times(1)).toStudent(studentDTO);
        verify(repository, times(1)).save(student);
        verify(parser, times(1)).dtoResponse(student);
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
                assertThrows(ResourceNotFoundException.class, () -> service.saveUpdate(null));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getDescription(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should update student.")
    public void shouldUpdateStudent() {
        StudentDTO studentDTO = StudentDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        Student student = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        doReturn(student).when(parser).toStudent(studentDTO);
        doReturn(Optional.of(student)).when(repository).findById(UUID.fromString(studentDTO.getId()));
        doReturn(studentDTO).when(parser).dtoResponse(student);
        doNothing().when(queueStudentSender).sendMessage(studentDTO);
        doReturn(student).when(parser).toStudent(studentDTO);
        doReturn(student).when(repository).save(student);
        doReturn(studentDTO).when(parser).dtoResponse(student);

        StudentDTO studentResponse = service.saveUpdate(studentDTO);

        assertEquals(studentResponse.getId(), studentDTO.getId());
        assertEquals(studentResponse.getName(), studentDTO.getName());
        assertEquals(studentResponse.getEmail(), studentDTO.getEmail());

        verify(parser, times(2)).toStudent(studentDTO);
        verify(repository, times(1)).findById(UUID.fromString(studentDTO.getId()));
        verify(parser, times(2)).dtoResponse(student);
        verify(queueStudentSender, times(1)).sendMessage(studentDTO);
        verify(parser, times(2)).toStudent(studentDTO);
        verify(repository, times(1)).save(student);
        verify(parser, times(2)).dtoResponse(student);
    }

    @Test
    @DisplayName("Should return student by id.")
    public void shouldReturnStudentById() throws Exception {
        StudentDTO studentDTO = StudentDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("kauai")
                .email("kauai@kauai.com")
                .build();

        Student student = Student.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("kauai12")
                .email("kauai@kauai.com")
                .build();

        doReturn(student).when(repository).findByStudentId(UUID.fromString(studentDTO.getId()));
        doReturn(studentDTO).when(parser).dtoResponse(student);

        StudentDTO studentDTOResponse = service.studentuById(studentDTO.getId());

        assertEquals(studentDTOResponse.getId(), studentDTO.getId());
        assertEquals(studentDTOResponse.getName(), studentDTO.getName());
        assertEquals(studentDTOResponse.getEmail(), studentDTO.getEmail());

        verify(repository, times(1)).findByStudentId(UUID.fromString(studentDTO.getId()));
        verify(parser, times(1)).dtoResponse(student);
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
