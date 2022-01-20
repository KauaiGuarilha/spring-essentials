package com.example.springessentialssenders.controller;

import com.example.springessentialssenders.model.service.StudentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class StudentFeignController {

    @Autowired private StudentFeignService service;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/protected/student-consumers/findByName/{name}")
    public ResponseEntity findStudentConsumersByName(@PathVariable String name) {
        return new ResponseEntity(service.findStudentConsumersByName(name), HttpStatus.OK);
    }
}
