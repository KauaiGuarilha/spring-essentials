package com.example.springessentialssenders.controller;

import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.dto.UserDTOResponse;
import com.example.springessentialssenders.model.entity.Users;
import com.example.springessentialssenders.model.service.UserService;
import com.example.springessentialssenders.model.utils.EssentialsObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock private UserService service;

    @InjectMocks private UserController controller;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Should save user")
    public void shouldSaveStudent() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(null)
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .name("Kauai")
                .username("kauaiTest")
                .admin(2)
                .build();

        doReturn(userDTOResponse).when(service).saveUpdate(userDTO);

        mockMvc.perform(
                        post("/v1/user")
                                .content(EssentialsObjectMapper.asJsonString(userDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userDTOResponse.getId()))
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.username").value(userDTOResponse.getUsername()))
                .andExpect(jsonPath("$.admin").value(userDTOResponse.getAdmin()));

        verify(service, times(1)).saveUpdate(userDTO);
    }

    @Test
    @DisplayName("Should update user")
    @WithMockUser(username = "kauaiUser", password = "123456", roles = {"USER", "ADMIN"})
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

        mockMvc.perform(
                        put("/v1/protected/user/update")
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
    @WithMockUser(username = "kauaiUser", password = "123456", roles = {"USER", "ADMIN"})
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

        mockMvc.perform(get("/v1/protected/user/" + userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTOResponse.getId()))
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.username").value(userDTOResponse.getUsername()))
                .andExpect(jsonPath("$.admin").value(userDTOResponse.getAdmin()));

        verify(service, times(1)).userById(userDTO.getId());
    }

    @Test
    @DisplayName("Should delete user by ID")
    @WithMockUser(username = "kauaiAdmin", password = "123456", roles = {"USER", "ADMIN"})
    public void shouldDeleteUserByID() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .build();

        doNothing().when(service).delete(userDTO.getId());

        mockMvc.perform(delete("/v1/admin/user/" + userDTO.getId()))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(userDTO.getId());
    }
}
