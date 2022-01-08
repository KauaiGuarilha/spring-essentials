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
    @DisplayName("Create should persist data")
    public void createShouldPersistData() {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        this.repository.save(user);

        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo("Kauai");
        Assertions.assertThat(user.getUsername()).isEqualTo("kauaiTest");
        Assertions.assertThat(user.getPassword()).isEqualTo("123456");
        Assertions.assertThat(user.getAdmin()).isEqualTo(2);
    }

    @Test
    @DisplayName("Delete should remove data")
    public void deleteShouldRemoveData() {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        this.repository.save(user);
        this.repository.delete(user);

        Assertions.assertThat(repository.findByUserId(user.getId())).isNull();
    }

    @Test
    @DisplayName("Update should change and persist data")
    public void updateShouldChangeAndPersistData() {
        Users user = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai")
                .username("kauaiTest")
                .password("123456")
                .admin(2)
                .build();

        this.repository.save(user);
        user.setName("Kauai Guarilha");
        user.setPassword("123");
        user.setAdmin(1);

        this.repository.save(user);

        Assertions.assertThat(user.getName()).isEqualTo("Kauai Guarilha");
        Assertions.assertThat(user.getPassword()).isEqualTo("123");
        Assertions.assertThat(user.getAdmin()).isEqualTo(1);
    }

    @Test
    @DisplayName("Find name IgnoreCase containing should IgnoreCase")
    public void findNameIgnoreCaseContainingShouldIgnoreCase() {
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

        this.repository.save(userAdmin);
        this.repository.save(userUser);

        List<Users> users = repository.findByNameIgnoreCaseContaining("Kauai Guarilha");

        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Find by username should")
    public void findByUsernameShould() {
        Users userAdmin = Users.builder()
                .id(UUID.randomUUID())
                .name("Kauai Guarilha")
                .username("kauaiAdmin")
                .password("123456")
                .admin(2)
                .build();

        this.repository.save(userAdmin);

        Users user = repository.findByUsername(userAdmin.getUsername());

        Assertions.assertThat(user.getUsername()).isEqualTo("kauaiAdmin");
    }
}
