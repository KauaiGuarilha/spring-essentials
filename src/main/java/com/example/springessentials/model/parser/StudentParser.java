package com.example.springessentials.model.parser;

import com.example.springessentials.model.dto.StudentDTO;
import com.example.springessentials.model.entity.Student;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

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
