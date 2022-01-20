package com.example.springessentialsconsumers.controller;

import com.example.springessentialsconsumers.model.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class StudentController {

    @Autowired private StudentService service;

    @GetMapping("/students/findByName/{name}")
    public ResponseEntity findStudentByName(@PathVariable String name) {
        return new ResponseEntity(service.studentByName(name), HttpStatus.OK);
    }
}
