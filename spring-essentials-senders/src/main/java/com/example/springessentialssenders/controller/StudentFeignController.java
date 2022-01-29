package com.example.springessentialssenders.controller;

import com.example.springessentialssenders.model.service.StudentFeignService;
import com.example.springessentialssenders.model.service.StudentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @Autowired private StudentService service;
    @Autowired private StudentFeignService feignService;

    @PreAuthorize("hasRole('USER')")
    @HystrixCommand(fallbackMethod = "findStudentAlternative")
    @GetMapping("/protected/student-consumers/findByName/{name}")
    public ResponseEntity findStudentConsumersByName(@PathVariable String name) {
        return new ResponseEntity(feignService.findStudentConsumersByName(name), HttpStatus.OK);
    }

    public ResponseEntity findStudentAlternative(@PathVariable String name) {
        return new ResponseEntity(service.studentByName(name), HttpStatus.OK);
    }
}
