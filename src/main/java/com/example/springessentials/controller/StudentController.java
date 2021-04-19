package com.example.springessentials.controller;

import com.example.springessentials.model.dto.StudentDTO;
import com.example.springessentials.model.parser.StudentParser;
import com.example.springessentials.model.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired private StudentParser parser;
    @Autowired private StudentService service;

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody StudentDTO dto){
        return new ResponseEntity<>(parser.dtoResponse(service.saveStudent(parser.toStudent(dto))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody StudentDTO student){
        return new ResponseEntity<>(parser.dtoResponse(service.updateStudent(id, parser.toStudent(student))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentuById(@PathVariable("id") String id){
        return new ResponseEntity<>(parser.dtoResponse(service.studentuById(id)), HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity findStudentByName(@PathVariable String name){
        return new ResponseEntity(service.studentByName(name), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable){
        return new ResponseEntity<>(service.listAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
