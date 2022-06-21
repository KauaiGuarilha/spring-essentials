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
    @DisplayName("Should create persist student.")
    public void shouldCreatePersistStudent() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        Student studentRepository = this.repository.save(student);

        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo(studentRepository.getName());
        Assertions.assertThat(student.getEmail()).isEqualTo(studentRepository.getEmail());
    }

    @Test
    @DisplayName("Should delete student.")
    public void shouldDeleteStudent() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        Student studentRepository = this.repository.save(student);

        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo(studentRepository.getName());
        Assertions.assertThat(student.getEmail()).isEqualTo(studentRepository.getEmail());

        this.repository.delete(student);

        Assertions.assertThat(repository.findByStudentId(student.getId())).isNull();
    }

    @Test
    @DisplayName("Should update change and persist student.")
    public void shouldUpdateChangeAndPersistStudent() {
        Student student =
                Student.builder()
                        .id(UUID.randomUUID())
                        .name("Kauai Guarilha")
                        .email("kauai@kauai.com")
                        .build();

        Student studentRepository = this.repository.save(student);

        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo(studentRepository.getName());
        Assertions.assertThat(student.getEmail()).isEqualTo(studentRepository.getEmail());

        student.setName("Kauai");
        student.setEmail("kauai@k.com");

        studentRepository = this.repository.save(student);

        Assertions.assertThat(student.getName()).isEqualTo(studentRepository.getName());
        Assertions.assertThat(student.getEmail()).isEqualTo(studentRepository.getEmail());
    }

    @Test
    @DisplayName("Should find student name ignoreCase containing ignoreCase.")
    public void shouldFindStudentNameIgnoreCaseContainingIgnoreCase() {
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

        Student studentRepository = this.repository.save(student);

        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo(studentRepository.getName());
        Assertions.assertThat(student.getEmail()).isEqualTo(studentRepository.getEmail());

        Student student2Repository = this.repository.save(student2);

        Assertions.assertThat(student2.getId()).isNotNull();
        Assertions.assertThat(student2.getName()).isEqualTo(student2Repository.getName());
        Assertions.assertThat(student2.getEmail()).isEqualTo(student2Repository.getEmail());

        List<Student> students = repository.findByNameIgnoreCaseContaining("Kauai Guarilha");

        Assertions.assertThat(students.size()).isEqualTo(2);
    }
}
