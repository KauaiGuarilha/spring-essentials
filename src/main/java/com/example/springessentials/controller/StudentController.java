package com.example.springessentials.controller;

import com.example.springessentials.model.entity.Student;
import com.example.springessentials.model.exceptions.ResourceNotFoundException;
import com.example.springessentials.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentRepository repository;

    @Autowired
    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable){
        return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentuById(@PathVariable("id") String id){
        verifyStudentExists(id);
        Student student = repository.findByStudentId(UUID.fromString(id));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity findStudentByName(@PathVariable String name){
        return new ResponseEntity(repository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Student student){
        return new ResponseEntity<>(repository.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        verifyStudentExists(id);
        repository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){
        verifyStudentExists(String.valueOf(student.getId()));
        repository.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyStudentExists(String id){
        if (repository.findByStudentId(UUID.fromString(id)) == null) throw new ResourceNotFoundException("Student not found for UUID: " + id);
    }
}
