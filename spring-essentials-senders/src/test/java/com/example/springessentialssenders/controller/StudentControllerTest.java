package com.example.springessentialssenders.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springessentialssenders.model.dto.StudentDTO;
import com.example.springessentialssenders.model.entity.Student;
import com.example.springessentialssenders.model.builder.StudentParser;
import com.example.springessentialssenders.model.service.StudentService;
import com.example.springessentialssenders.model.utils.EssentialsObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock private StudentService service;
    @Mock private StudentParser parser;

    @InjectMocks private StudentController controller;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Should save student")
    @WithMockUser(
            username = "kauai",
            password = "123",
            roles = {"USER", "ADMIN"})
    public void shouldSaveStudent() throws Exception {
        StudentDTO dto = StudentDTO.builder().name("kauai").email("kauai@kauai.com").build();

        Student student =
                Student.builder()
                        .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                        .name("kauai")
                        .email("kauai@kauai.com")
                        .build();

        StudentDTO response =
                StudentDTO.builder()
                        .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                        .name("kauai")
                        .email("kauai@kauai.com")
                        .build();

        doReturn(student).when(parser).toStudent(dto);
        doReturn(student).when(service).saveUpdate(student);
        doReturn(response).when(parser).dtoResponse(student);

        mockMvc.perform(
                        post("/v1/admin/students")
                                .content(EssentialsObjectMapper.asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));

        verify(parser, times(1)).toStudent(dto);
        verify(service, times(1)).saveUpdate(student);
        verify(parser, times(1)).dtoResponse(student);
    }

    @Test
    @DisplayName("Should update student")
    @WithMockUser(
            username = "kauai",
            password = "123",
            roles = {"USER", "ADMIN"})
    public void shouldUpdateStudent() throws Exception {
        StudentDTO dto =
                StudentDTO.builder()
                        .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                        .name("kauai")
                        .email("kauai@kauai.com")
                        .build();

        Student student =
                Student.builder()
                        .id(UUID.fromString(dto.getId()))
                        .name("kauai")
                        .email("kauai@kauai.com")
                        .build();

        StudentDTO response =
                StudentDTO.builder().id(dto.getId()).name("kauai").email("kauai@kauai.com").build();

        doReturn(student).when(parser).toStudent(dto);
        doReturn(student).when(service).saveUpdate(student);
        doReturn(response).when(parser).dtoResponse(student);

        mockMvc.perform(
                        put("/v1/admin/students/")
                                .content(EssentialsObjectMapper.asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));

        verify(parser, times(1)).toStudent(dto);
        verify(service, times(1)).saveUpdate(student);
        verify(parser, times(1)).dtoResponse(student);
    }

    @Test
    @DisplayName("Should delete student")
    @WithMockUser(
            username = "kauai",
            password = "123",
            roles = {"USER", "ADMIN"})
    public void shouldDeleteStudent() throws Exception {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/v1/admin/students/" + id)).andExpect(status().isOk());

        verify(service, times(1)).delete(id);
    }

    @Test
    @DisplayName("Should return student by ID")
    public void shouldReturnStudentByID() throws Exception {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";

        StudentDTO response =
                StudentDTO.builder().id(id).name("kauai").email("kauai@kauai.com").build();

        Student student =
                Student.builder()
                        .id(UUID.fromString(id))
                        .name("kauai")
                        .email("kauai@kauai.com")
                        .build();

        doReturn(student).when(service).studentuById(id);
        doReturn(response).when(parser).dtoResponse(student);

        mockMvc.perform(get("/v1/protected/students/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));

        verify(service, times(1)).studentuById(id);
        verify(parser, times(1)).dtoResponse(student);
    }

    @Test
    @DisplayName("Should return student by name")
    public void shouldReturnStudentByName() throws Exception {
        String name = "kauai";
        List<Student> students = new ArrayList<>();

        StudentDTO response =
                StudentDTO.builder()
                        .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                        .name(name)
                        .email("kauai@kauai.com")
                        .build();

        Student student =
                Student.builder()
                        .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                        .name(name)
                        .email("kauai@kauai.com")
                        .build();

        students.add(student);

        doReturn(students).when(service).studentByName(name);
        doReturn(response).when(parser).dtoResponse(student);

        mockMvc.perform(get("/v1/protected/students/findByName/" + name))
                .andExpect(status().isOk());

        verify(service, times(1)).studentByName(name);
        verify(parser, times(0)).dtoResponse(student);
    }
}
