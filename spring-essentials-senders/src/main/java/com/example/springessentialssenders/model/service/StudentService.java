package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.StudentDTO;
import com.example.springessentialssenders.model.entity.Student;
import com.example.springessentialssenders.model.exceptions.ResourceNotFoundException;
import com.example.springessentialssenders.model.exceptions.StudentNotFoundException;
import com.example.springessentialssenders.model.exceptions.UUIDNotFoundException;
import com.example.springessentialssenders.model.parser.StudentParser;
import com.example.springessentialssenders.model.queue.senders.QueueStudentSender;
import com.example.springessentialssenders.model.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class StudentService {

    @Autowired private StudentParser parser;
    @Autowired private StudentRepository repository;
    @Autowired private QueueStudentSender queueStudentSender;

    public StudentDTO saveUpdate(StudentDTO studentDTO) {
        try {
            if (Objects.isNull(studentDTO.getId())) return sendForQueueAndSaveStudent(studentDTO);

            Student student = repository.findByStudentIdOptional(parser.toStudent(studentDTO).getId())
                    .orElseThrow(
                            () -> new StudentNotFoundException(EValidation.STUDENT_NOT_FOUND_FOR_ID, studentDTO.getId()));

            Student db = student;
            db.setName(studentDTO.getName());
            db.setEmail(studentDTO.getEmail());

            return sendForQueueAndSaveStudent(parser.dtoResponse(db));
        } catch (StudentNotFoundException e){
            throw e;
        } catch (Exception e) {
            log.error("There was a generic problem when trying to save or update the student.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private StudentDTO sendForQueueAndSaveStudent(StudentDTO studentDTO){
        queueStudentSender.sendMessage(studentDTO);
        return parser.dtoResponse(repository.save(parser.toStudent(studentDTO)));
    }
 

    public Page<Student> listAll(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e){
            log.error("There was a generic problem when trying to list all student.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public StudentDTO studentuById(String id) {
        try {
            verifyUUID(id);
            Student student = repository.findByStudentId(UUID.fromString(id));
            if (Objects.isNull(student))
                throw new StudentNotFoundException(EValidation.STUDENT_NOT_FOUND_FOR_ID, id);

            return parser.dtoResponse(student);
        } catch (ResourceNotFoundException | StudentNotFoundException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying to return students by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

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

    public void delete(String id) {
        try {
            verifyUUID(id);
            repository.deleteById(UUID.fromString(id));
        } catch (UUIDNotFoundException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying to delete students by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private void verifyUUID(String id) {
        if (Objects.isNull(id))
            throw new UUIDNotFoundException(EValidation.UUID_NOT_FOUND);
    }
}
