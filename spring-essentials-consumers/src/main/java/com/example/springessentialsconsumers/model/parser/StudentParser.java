package com.example.springessentialsconsumers.model.parser;

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
}
