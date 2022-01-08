package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.Student;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class StudentRepositoryTest {

    @Autowired private StudentRepository repository;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    @DisplayName("Create should persist data")
    public void createShouldPersistData() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        this.repository.save(student);

        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo("Kauai Guarilha");
        Assertions.assertThat(student.getEmail()).isEqualTo("kauai@kauai.com");
    }

    @Test
    @DisplayName("Delete should remove data")
    public void deleteShouldRemoveData() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        this.repository.save(student);
        this.repository.delete(student);

        Assertions.assertThat(repository.findByStudentId(student.getId())).isNull();
    }

    @Test
    @DisplayName("Update should change and persist data")
    public void updateShouldChangeAndPersistData() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        this.repository.save(student);
        student.setName("Kauai");
        student.setEmail("kauai@k.com");

        this.repository.save(student);

        Assertions.assertThat(student.getName()).isEqualTo("Kauai");
        Assertions.assertThat(student.getEmail()).isEqualTo("kauai@k.com");
    }

    @Test
    @DisplayName("Find name IgnoreCase containing should IgnoreCase")
    public void findNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        Student student2 =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha2")
                        .email("kauai2@kauai.com")
                        .build();

        this.repository.save(student);
        this.repository.save(student2);
        List<Student> students = repository.findByNameIgnoreCaseContaining("Kauai Guarilha");

        Assertions.assertThat(students.size()).isEqualTo(2);
    }
}
