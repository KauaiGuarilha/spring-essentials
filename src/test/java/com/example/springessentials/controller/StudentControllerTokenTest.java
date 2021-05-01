package com.example.springessentials.controller;

import com.example.springessentials.model.dto.StudentDTO;
import com.example.springessentials.model.entity.Student;
import com.example.springessentials.model.parser.StudentParser;
import com.example.springessentials.model.service.StudentService;
import com.example.springessentials.model.utils.EssentialsObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTokenTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TestRestTemplate restTemplate;

    private HttpEntity<Void> protectedHeader;
    private HttpEntity<Void> adminHeader;
    private HttpEntity<Void> wrongHeader;

    @MockBean private StudentService service;
    @MockBean private StudentParser parser;

    @BeforeEach
    public void configProtectedHeaders() throws Exception {
        String kauaiUser = "{\"username\": \"kauaiUser\", \"password\": \"12\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", kauaiUser, String.class).getHeaders();
        this.protectedHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configAdminHeaders() throws Exception {
        String kauai = "{\"username\": \"kauai\", \"password\": \"123\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", kauai, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configWrongHeaders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "000000");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Test
    @DisplayName("Should save student")
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

        String token = adminHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                        post("/v1/admin/students").header("Authorization", token)
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
    @DisplayName("Should not save student")
    public void shouldNotSaveStudent() throws Exception {
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

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                post("/v1/admin/students").header("Authorization", token)
                        .content(EssentialsObjectMapper.asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should update student")
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

        String token = adminHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                        put("/v1/admin/students/").header("Authorization", token)
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
    @DisplayName("Should not update student")
    public void shouldNotUpdateStudent() throws Exception {
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

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                put("/v1/admin/students/").header("Authorization", token)
                        .content(EssentialsObjectMapper.asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should delete student")
    public void shouldDeleteStudent() throws Exception {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";
        doNothing().when(service).delete(id);

        String token = adminHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(delete("/v1/admin/students/" + id).header("Authorization", token))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(id);
    }

    @Test
    @DisplayName("Should not delete student")
    public void shouldNotDeleteStudent() throws Exception {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";
        doNothing().when(service).delete(id);

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(delete("/v1/admin/students/" + id).header("Authorization", token))
                .andExpect(status().isForbidden());
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

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(get("/v1/protected/students/" + id).header("Authorization", token))
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

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(get("/v1/protected/students/findByName/" + name).header("Authorization", token))
                .andExpect(status().isOk());

        verify(service, times(1)).studentByName(name);
        verify(parser, times(0)).dtoResponse(student);
    }
}
