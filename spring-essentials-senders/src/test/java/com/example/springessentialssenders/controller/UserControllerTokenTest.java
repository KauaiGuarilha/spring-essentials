package com.example.springessentialssenders.controller;

import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.dto.UserDTOResponse;
import com.example.springessentialssenders.model.service.UserService;
import com.example.springessentialssenders.model.utils.EssentialsObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTokenTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TestRestTemplate restTemplate;

    private HttpEntity<Void> protectedHeader;
    private HttpEntity<Void> adminHeader;
    private HttpEntity<Void> wrongHeader;

    @MockBean private UserService service;

    @BeforeEach
    public void configProtectedHeaders() throws Exception {
        String kauaiUser = "{\"username\": \"kauaiUser\", \"password\": \"123456\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", kauaiUser, String.class).getHeaders();
        this.protectedHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configAdminHeaders() throws Exception {
        String kauaiAdmin = "{\"username\": \"kauaiAdmin\", \"password\": \"123456\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", kauaiAdmin, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configWrongHeaders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "000000");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Test
    @DisplayName("Should update user")
    public void shouldUpdateStudent() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("KauaiTest")
                .username("kauaiTest")
                .password("123456")
                .admin(1)
                .build();

        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("Kauai")
                .username("kauaiTest")
                .admin(1)
                .build();

        doReturn(userDTOResponse).when(service).saveUpdate(userDTO);

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                        put("/v1/protected/user/update").header("Authorization", token)
                                .content(EssentialsObjectMapper.asJsonString(userDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTOResponse.getId()))
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.username").value(userDTOResponse.getUsername()))
                .andExpect(jsonPath("$.admin").value(userDTOResponse.getAdmin()));

        verify(service, times(1)).saveUpdate(userDTO);
    }

    @Test
    @DisplayName("Should return user by ID")
    public void shouldReturnUserByID() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .build();

        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("Kauai")
                .username("kauaiTest")
                .admin(1)
                .build();

        doReturn(userDTOResponse).when(service).userById(userDTO.getId());

        String token = protectedHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                get("/v1/protected/user/" + userDTO.getId()).header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTOResponse.getId()))
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.username").value(userDTOResponse.getUsername()))
                .andExpect(jsonPath("$.admin").value(userDTOResponse.getAdmin()));

        verify(service, times(1)).userById(userDTO.getId());
    }

    @Test
    @DisplayName("Should delete user by ID")
    public void shouldDeleteUserByID() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .build();

        doNothing().when(service).delete(userDTO.getId());

        String token = adminHeader.getHeaders().get("Authorization").get(0);
        mockMvc.perform(
                delete("/v1/admin/user/" + userDTO.getId()).header("Authorization", token))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(userDTO.getId());
    }
}
