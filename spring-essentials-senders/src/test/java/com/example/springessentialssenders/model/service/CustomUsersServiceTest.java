package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.entity.Users;
import com.example.springessentialssenders.model.exceptions.EssentialsRuntimeException;
import com.example.springessentialssenders.model.repository.UsersReporitory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomUsersServiceTest {

    @Mock private UsersReporitory repository;

    @InjectMocks CustomUsersService service;

    @Test
    @DisplayName("Should load by username.")
    public void shouldLoadUserByUsername(){
        Users users = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        doReturn(users).when(repository).findByUsername(users.getUsername());

        UserDetails userDetails = service.loadUserByUsername(users.getUsername());

        assertEquals(userDetails.getUsername(), users.getUsername());
        assertEquals(userDetails.getPassword(), users.getPassword());

        verify(repository, times(1)).findByUsername(users.getUsername());
    }

    @Test
    @DisplayName("Should exception when find user by username.")
    public void shouldExceptionWhenFindUserByUsername(){
        Users users = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        UsernameNotFoundException thrown =
                assertThrows(UsernameNotFoundException.class,
                        () -> service.loadUserByUsername(users.getUsername()));

        assertEquals(thrown.getClass(), UsernameNotFoundException.class);
        assertEquals(thrown.getMessage(), "User not found, check username.");

        verify(repository, times(1)).findByUsername(any(String.class));
    }

    @Test
    @DisplayName("Should exception when load null permission user.")
    public void shouldExceptionWhenLoadNullPermissionUser(){
        Users users = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(null)
                .build();

        doReturn(users).when(repository).findByUsername(any(String.class));

        EssentialsRuntimeException thrown =
                assertThrows(EssentialsRuntimeException.class,
                        () -> service.loadUserByUsername(users.getUsername()));

        assertEquals(thrown.getClass(), EssentialsRuntimeException.class);
        assertEquals(thrown.getMessage(), "An unidentified problem has occurred.");

        verify(repository, times(1)).findByUsername(any(String.class));
    }
}
