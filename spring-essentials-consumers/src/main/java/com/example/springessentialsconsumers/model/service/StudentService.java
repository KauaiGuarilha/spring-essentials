package com.example.springessentialsconsumers.model.service;

import com.example.springessentialsconsumers.model.domain.EValidation;
import com.example.springessentialsconsumers.model.entity.Student;
import com.example.springessentialsconsumers.model.exceptions.ResourceNotFoundException;
import com.example.springessentialsconsumers.model.exceptions.StudentNotFoundException;
import com.example.springessentialsconsumers.model.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    @Autowired private StudentRepository repository;

    public List<Student> studentByName(String name) {
        try {
            if (StringUtils.isBlank(name))
                throw new StudentNotFoundException(EValidation.STUDENT_NOT_FOUND_FOR_NAME);

            List<Student> students = repository.findByNameIgnoreCaseContaining(name);
            return students;
        } catch (StudentNotFoundException e){
            throw e;
        } catch (Exception e) {
            log.error("There was a generic problem when trying to return students by name.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }
}
