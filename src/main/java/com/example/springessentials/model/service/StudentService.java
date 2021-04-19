package com.example.springessentials.model.service;

import com.example.springessentials.model.entity.Student;
import com.example.springessentials.model.exceptions.ResourceNotFoundException;
import com.example.springessentials.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired private StudentRepository repository;

    public Student saveStudent(Student student){
        try {
            Student db = repository.save(student);
            return db;
        } catch (Exception e){
            throw new RuntimeException("Could not save student.");
        }
    }

    public Student updateStudent(String id, Student student){
        try {
            Optional<Student> optional = repository.findById(UUID.fromString(id));
            if (optional.isPresent()){
                Student db = optional.get();

                db.setName(student.getName());
                db.setEmail(student.getEmail());

                return repository.save(db);
            }
            return null;
        } catch (Exception e){
            throw new ResourceNotFoundException("Student not found for UUID: " + id);
        }
    }

    public Page<Student> listAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Student studentuById(String id){
        verifyStudentExists(id);
        Student student = repository.findByStudentId(UUID.fromString(id));
        return student;
    }

    public List<Student> studentByName(String name){
        try {
            List<Student> students = repository.findByNameIgnoreCaseContaining(name);
            return students;
        } catch (Exception e){
            throw new ResourceNotFoundException("Student not found for name: " + name);
        }
    }

    public void delete(String id){
        repository.deleteById(UUID.fromString(id));
    }

    private void verifyStudentExists(String id){
        if (repository.findByStudentId(UUID.fromString(id)) == null) throw new ResourceNotFoundException("Student not found for UUID: " + id);
    }
}
