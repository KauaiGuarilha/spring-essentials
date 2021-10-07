package com.example.springessentialsconsumers.model.builder;

import com.example.springessentialsconsumers.model.dto.StudentDTO;
import com.example.springessentialsconsumers.model.entity.Student;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class StudentParser {

    public Student toStudent(StudentDTO dto) {
        return Student.builder()
                .id(Objects.isNull(dto.getId()) ? null : UUID.fromString(dto.getId()))
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public StudentDTO dtoResponse(Student student) {
        return StudentDTO.builder()
                .id(student.getId().toString())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}
