package com.example.springessentials.controller;

import com.example.springessentials.model.dto.StudentDTO;
import com.example.springessentials.model.parser.StudentParser;
import com.example.springessentials.model.service.StudentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class StudentController {

    @Autowired private StudentParser parser;
    @Autowired private StudentService service;

    @PostMapping("/admin/students")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody StudentDTO dto) {
        return new ResponseEntity<>(
                parser.dtoResponse(service.saveUpdate(parser.toStudent(dto))), HttpStatus.CREATED);
    }

    @PutMapping("/admin/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody StudentDTO dto) {
        return new ResponseEntity<>(
                parser.dtoResponse(service.saveUpdate(parser.toStudent(dto))), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/students/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/protected/students/{id}")
    public ResponseEntity<?> getStudentuById(@PathVariable("id") String id) {
        return new ResponseEntity<>(parser.dtoResponse(service.studentuById(id)), HttpStatus.OK);
    }

    @GetMapping("/protected/students/findByName/{name}")
    public ResponseEntity findStudentByName(@PathVariable String name) {
        return new ResponseEntity(service.studentByName(name), HttpStatus.OK);
    }

    @GetMapping("/protected/students")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(service.listAll(pageable), HttpStatus.OK);
    }
}
