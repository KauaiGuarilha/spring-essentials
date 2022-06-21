package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.FileLoad;
import com.example.springessentialssenders.model.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
public class FileLoadRepositoryTest {

    @Autowired private FileLoadRepository repository;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    @DisplayName("Should create persist fileLoad.")
    public void shouldCreatePersistFileLoad() {
        FileLoad fileLoad =
                FileLoad.builder()
                        .id(UUID.randomUUID())
                        .student(Student.builder().build())
                        .pathFile("pathfile")
                        .build();

        FileLoad fileLoadRepository = this.repository.save(fileLoad);

        Assertions.assertThat(fileLoad.getId()).isNotNull();
        Assertions.assertThat(fileLoad.getStudent()).isNotNull();
        Assertions.assertThat(fileLoad.getPathFile()).isEqualTo(fileLoadRepository.getPathFile());
    }

    @Test
    @DisplayName("Should delete fileLoad.")
    public void shouldDeleteFileLoad() {
        FileLoad fileLoad =
                FileLoad.builder()
                        .id(UUID.randomUUID())
                        .student(Student.builder().build())
                        .pathFile("pathfile")
                        .build();

        FileLoad fileLoadRepository = this.repository.save(fileLoad);

        Assertions.assertThat(fileLoad.getId()).isNotNull();
        Assertions.assertThat(fileLoad.getStudent()).isNotNull();
        Assertions.assertThat(fileLoad.getPathFile()).isEqualTo(fileLoadRepository.getPathFile());

        this.repository.delete(fileLoad);

        Assertions.assertThat(repository.findById(fileLoad.getId())).isNotNull();
    }

    @Test
    @DisplayName("Should update change and persist fileLoad.")
    public void shouldUpdateChangeAndPersistFileLoad() {
        FileLoad fileLoad =
                FileLoad.builder()
                        .id(UUID.randomUUID())
                        .student(Student.builder().build())
                        .pathFile("pathfile")
                        .build();

        FileLoad fileLoadRepository = this.repository.save(fileLoad);

        Assertions.assertThat(fileLoad.getId()).isNotNull();
        Assertions.assertThat(fileLoad.getStudent()).isNotNull();
        Assertions.assertThat(fileLoad.getPathFile()).isEqualTo(fileLoadRepository.getPathFile());

        fileLoad.setPathFile("pathfile2");

        fileLoadRepository = this.repository.save(fileLoad);

        Assertions.assertThat(fileLoad.getId()).isNotNull();
        Assertions.assertThat(fileLoad.getStudent()).isNotNull();
        Assertions.assertThat(fileLoad.getPathFile()).isEqualTo(fileLoadRepository.getPathFile());
    }
}
