package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.Users;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UsersRepositoryTest {

    @Autowired private UsersReporitory repository;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    @DisplayName("Should create persist user.")
    public void shouldCreatePersistUser() {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        Users userRepository = this.repository.save(user);

        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo(userRepository.getName());
        Assertions.assertThat(user.getUsername()).isEqualTo(userRepository.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo(userRepository.getPassword());
        Assertions.assertThat(user.getAdmin()).isEqualTo(userRepository.getAdmin());
    }

    @Test
    @DisplayName("Should delete user.")
    public void shouldDeleteUser() {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        Users userRepository = this.repository.save(user);

        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo(userRepository.getName());
        Assertions.assertThat(user.getUsername()).isEqualTo(userRepository.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo(userRepository.getPassword());
        Assertions.assertThat(user.getAdmin()).isEqualTo(userRepository.getAdmin());

        this.repository.delete(user);

        Assertions.assertThat(repository.findByUserId(user.getId())).isNull();
    }

    @Test
    @DisplayName("Should update change and persist user.")
    public void shouldUpdateChangeAndPersistUser() {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        Users userRepository = this.repository.save(user);

        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo(userRepository.getName());
        Assertions.assertThat(user.getUsername()).isEqualTo(userRepository.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo(userRepository.getPassword());
        Assertions.assertThat(user.getAdmin()).isEqualTo(userRepository.getAdmin());

        user.setName("Kauai Guarilha");
        user.setPassword("123");
        user.setAdmin(1);

        userRepository = this.repository.save(user);

        Assertions.assertThat(user.getName()).isEqualTo(userRepository.getName());
        Assertions.assertThat(user.getPassword()).isEqualTo(userRepository.getPassword());
        Assertions.assertThat(user.getAdmin()).isEqualTo(userRepository.getAdmin());
    }

    @Test
    @DisplayName("Should find user name ignoreCase containing ignoreCase.")
    public void shouldFindUserNameIgnoreCaseContainingIgnoreCase() {
        Users userAdmin = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai Guarilha")
                .username("kauaiAdmin")
                .password("123456")
                .admin(2)
                .build();

        Users userUser = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai Guarilha2")
                .username("kauaiUser")
                .password("123456")
                .admin(1)
                .build();

        Users userRepositoryAdmin = this.repository.save(userAdmin);

        Assertions.assertThat(userAdmin.getId()).isNotNull();
        Assertions.assertThat(userAdmin.getName()).isEqualTo(userRepositoryAdmin.getName());
        Assertions.assertThat(userAdmin.getUsername()).isEqualTo(userRepositoryAdmin.getUsername());
        Assertions.assertThat(userAdmin.getPassword()).isEqualTo(userRepositoryAdmin.getPassword());
        Assertions.assertThat(userAdmin.getAdmin()).isEqualTo(userRepositoryAdmin.getAdmin());

        Users userRepositoryUser = this.repository.save(userUser);

        Assertions.assertThat(userUser.getId()).isNotNull();
        Assertions.assertThat(userUser.getName()).isEqualTo(userRepositoryUser.getName());
        Assertions.assertThat(userUser.getUsername()).isEqualTo(userRepositoryUser.getUsername());
        Assertions.assertThat(userUser.getPassword()).isEqualTo(userRepositoryUser.getPassword());
        Assertions.assertThat(userUser.getAdmin()).isEqualTo(userRepositoryUser.getAdmin());

        List<Users> users = repository.findByNameIgnoreCaseContaining("Kauai Guarilha");

        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should find by username.")
    public void shouldFindByUsername() {
        Users userAdmin = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai Guarilha")
                .username("kauaiAdmin")
                .password("123456")
                .admin(2)
                .build();

        Users userRepository = this.repository.save(userAdmin);

        Assertions.assertThat(userAdmin.getId()).isNotNull();
        Assertions.assertThat(userAdmin.getName()).isEqualTo(userRepository.getName());
        Assertions.assertThat(userAdmin.getUsername()).isEqualTo(userRepository.getUsername());
        Assertions.assertThat(userAdmin.getPassword()).isEqualTo(userRepository.getPassword());
        Assertions.assertThat(userAdmin.getAdmin()).isEqualTo(userRepository.getAdmin());

        Users user = repository.findByUsername(userAdmin.getUsername());

        Assertions.assertThat(user.getName()).isEqualTo(userAdmin.getName());
        Assertions.assertThat(user.getUsername()).isEqualTo(userAdmin.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo(userAdmin.getPassword());
        Assertions.assertThat(user.getAdmin()).isEqualTo(userAdmin.getAdmin());
    }
}
