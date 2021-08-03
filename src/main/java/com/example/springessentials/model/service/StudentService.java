package com.example.springessentials.model.service;

import com.example.springessentials.model.domain.EValidation;
import com.example.springessentials.model.entity.Student;
import com.example.springessentials.model.exceptions.ResourceNotFoundException;
import com.example.springessentials.model.repository.StudentRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentService {

    @Autowired private StudentRepository repository;

    public Student saveUpdate(Student student) {
        try {
            if (Objects.isNull(student.getId())) return repository.save(student);

            Optional<Student> optional = repository.findById(student.getId());

            Student db = new Student();
            if (optional.isPresent()) db = optional.get();

            db.setName(student.getName());
            db.setEmail(student.getEmail());

            return repository.save(db);
        } catch (Exception e) {
            log.error("There was a generic problem when trying to save or update the student.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public Page<Student> listAll(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e){
            log.error("There was a generic problem when trying to list all student.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public Student studentuById(String id) {
        try {
            verifyStudentExists(id);
            Student student = repository.findByStudentId(UUID.fromString(id));
            if (Objects.isNull(student))
                throw new ResourceNotFoundException("Student not found for ID");

            return student;
        } catch (ResourceNotFoundException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying to return students by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public List<Student> studentByName(String name) {
        try {
            if (StringUtils.isBlank(name))
                throw new ResourceNotFoundException("Student not found for name");

            List<Student> students = repository.findByNameIgnoreCaseContaining(name);
            return students;
        } catch (Exception e) {
            log.error("There was a generic problem when trying to return students by name.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public void delete(String id) {
        try {
            verifyStudentExists(id);
            repository.deleteById(UUID.fromString(id));
        } catch (Exception e){
            log.error("There was a generic problem when trying to delete students by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private void verifyStudentExists(String id) {
        if (Objects.isNull(UUID.fromString(id)))
            throw new ResourceNotFoundException("Student not found for UUID");
    }
}
