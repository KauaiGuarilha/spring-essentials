package com.example.springessentials.model.parser;

import com.example.springessentials.model.dto.StudentDTO;
import com.example.springessentials.model.dto.StudentDTOResponse;
import com.example.springessentials.model.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentParser {

    public Student toStudent(StudentDTO dto){
        return Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public StudentDTOResponse dtoResponse(Student student){
        return StudentDTOResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}
