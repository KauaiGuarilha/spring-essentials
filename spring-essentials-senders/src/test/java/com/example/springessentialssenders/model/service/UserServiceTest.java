package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.dto.UserDTOResponse;
import com.example.springessentialssenders.model.entity.Users;
import com.example.springessentialssenders.model.exceptions.*;
import com.example.springessentialssenders.model.parser.UsersParser;
import com.example.springessentialssenders.model.repository.UsersReporitory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock private UsersParser parser;
    @Mock private UsersReporitory repository;

    @InjectMocks UserService service;

    @Test
    @DisplayName("Should save User.")
    public void shouldSaveUser() {
        Users user = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

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

        doReturn(null).when(repository).findByUsername(userDTO.getUsername());
        doReturn(user).when(parser).toUsers(userDTO);
        doReturn(user).when(repository).save(user);
        doReturn(userDTOResponse).when(parser).toResponse(user);

        UserDTOResponse response = service.saveUpdate(userDTO);

        assertEquals(response.getId(), userDTOResponse.getId());
        assertEquals(response.getName(), userDTOResponse.getName());
        assertEquals(response.getUsername(), userDTOResponse.getUsername());
        assertEquals(response.getAdmin(), userDTOResponse.getAdmin());

        verify(repository, times(1)).findByUsername(user.getUsername());
        verify(parser, times(1)).toUsers(userDTO);
        verify(repository, times(1)).save(user);
        verify(parser, times(1)).toResponse(user);
    }

    @Test
    @DisplayName("Should exception when find an exist username.")
    public void shouldExceptionWhenFindAnExistUsername() {
        Users user = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(null)
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        doReturn(user).when(repository).findByUsername(userDTO.getUsername());

        UsernameAlreadyInUseException thrown =
                assertThrows(UsernameAlreadyInUseException.class,
                        () -> service.saveUpdate(userDTO));

        assertEquals(thrown.getClass(), UsernameAlreadyInUseException.class);
        assertEquals(thrown.getMessage(), "Username " + user.getUsername() + " is already using, try another one.");

        verify(repository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Should exception when save a null User.")
    public void shouldExceptionWhenSaveNullUser() {
        ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class,
                        () -> service.saveUpdate(null));

        assertEquals(thrown.getClass(), ResourceNotFoundException.class);
        assertEquals(thrown.getMessage(), "An unidentified problem has occurred.");
    }

    @Test
    @DisplayName("Should update User.")
    public void shouldUpdateUser() {
        Users user = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
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

        Optional<Users> optional = Optional.of(user);

        doReturn(user).when(parser).toUsers(userDTO);
        doReturn(optional).when(repository).findByUserIdOptional(user.getId());
        doReturn(user).when(repository).save(optional.get());
        doReturn(userDTOResponse).when(parser).toResponse(user);

        UserDTOResponse response = service.saveUpdate(userDTO);

        assertEquals(response.getId(), userDTOResponse.getId());
        assertEquals(response.getName(), userDTOResponse.getName());
        assertEquals(response.getUsername(), userDTOResponse.getUsername());
        assertEquals(response.getAdmin(), userDTOResponse.getAdmin());

        verify(parser, times(1)).toUsers(userDTO);
        verify(repository, times(1)).findByUserIdOptional(user.getId());
        verify(repository, times(1)).save(user);
        verify(parser, times(1)).toResponse(user);
    }

    @Test
    @DisplayName("Should exception when try find by id on update user")
    public void shouldExceptionWhenTryFindUserByIdOnUpdateUser() {
        Users user = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id("8e31518e-1558-48b0-9a06-5edfb772153f")
                .build();

        Optional<Users> optional = Optional.of(user);

        doReturn(user).when(parser).toUsers(userDTO);
        doReturn(optional).when(repository).findByUserIdOptional(null);

        UUIDNotFoundException thrown =
                assertThrows(UUIDNotFoundException.class,
                        () -> service.saveUpdate(userDTO));

        assertEquals(thrown.getClass(), UUIDNotFoundException.class);
        assertEquals(thrown.getMessage(), "UUID does not exist or null, check id.");

        verify(parser, times(1)).toUsers(userDTO);
        verify(repository, times(1)).findByUserIdOptional(user.getId());
    }

    @Test
    @DisplayName("Should return User by id.")
    public void shouldReturnUserById() {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";

        Users user = Users.builder()
                .id(UUID.fromString("8e31518e-1558-48b0-9a06-5edfb772153f"))
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

        doReturn(user).when(repository).findByUserId(UUID.fromString(id));
        doReturn(userDTOResponse).when(parser).toResponse(user);

        UserDTOResponse response = service.userById(id);

        assertEquals(response.getId(), userDTOResponse.getId());
        assertEquals(response.getName(), userDTOResponse.getName());
        assertEquals(response.getUsername(), userDTOResponse.getUsername());
        assertEquals(response.getAdmin(), userDTOResponse.getAdmin());

        verify(repository, times(1)).findByUserId(UUID.fromString(id));
        verify(parser, times(1)).toResponse(user);
    }

    @Test
    @DisplayName("Should exception when return User by id null.")
    public void shouldExceptionWhenReturnUserByIdNull() {
        UUIDNotFoundException thrown =
                assertThrows(UUIDNotFoundException.class,
                        () -> service.userById(null));

        assertEquals(thrown.getClass(), UUIDNotFoundException.class);
        assertEquals(thrown.getMessage(), "UUID does not exist or null, check id.");
    }

    @Test
    @DisplayName("Should exception when return null User by id.")
    public void shouldExceptionWhenReturnNullUserById() {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";

        doReturn(null).when(repository).findByUserId(UUID.fromString(id));

        UserNotFoundException thrown =
                assertThrows(UserNotFoundException.class,
                        () -> service.userById(id));

        assertEquals(thrown.getClass(), UserNotFoundException.class);
        assertEquals(thrown.getMessage(), "User not found for id: " + id + ".");

        verify(repository, times(1)).findByUserId(UUID.fromString(id));
    }

    @Test
    @DisplayName("Should delete User by id.")
    public void shouldDeleteUserById() {
        String id = "8e31518e-1558-48b0-9a06-5edfb772153f";

        doNothing().when(repository).deleteById(UUID.fromString(id));

        service.delete(id);

        verify(repository).deleteById(UUID.fromString(id));
    }

    @Test
    @DisplayName("Should exception when delete User by id.")
    public void shouldExceptionWhenDeleteUserById() {
        UUIDNotFoundException thrown =
                assertThrows(UUIDNotFoundException.class,
                        () -> service.delete(null));

        assertEquals(thrown.getClass(), UUIDNotFoundException.class);
        assertEquals(thrown.getMessage(), "UUID does not exist or null, check id.");
    }

    @Test
    @DisplayName("Should list users by pageable.")
    public void shouldListUserByPageable() {
        List<Users> usersList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 2);
        Page<Users> users = new PageImpl<Users>(usersList, pageable, usersList.size());

        doReturn(users).when(repository).findAll(pageable);

        service.listAll(pageable);

        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should exception when list user by pageable null.")
    public void shouldExceptionWhenListUserByNullPageableNull() {
        PageableNotFoundException thrown =
                assertThrows(PageableNotFoundException.class,
                        () -> service.listAll(null));

        assertEquals(thrown.getClass(), PageableNotFoundException.class);
        assertEquals(thrown.getMessage(), "Pageable does not found or null, check pageable.");
    }
}
