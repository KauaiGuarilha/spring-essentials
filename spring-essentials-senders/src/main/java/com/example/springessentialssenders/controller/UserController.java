package com.example.springessentialssenders.controller;

import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
public class UserController {

    @Autowired private UserService service;

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO dto){
        return new ResponseEntity<>(service.saveUpdate(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/protected/user/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO dto){
        return new ResponseEntity<>(service.saveUpdate(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/protected/user/{id}")
    public ResponseEntity<?> returnUser(@PathVariable String id){
        return new ResponseEntity<>(service.userById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/user")
    public ResponseEntity<?> listAll(Pageable pageable){
        return new ResponseEntity<>(service.listAll(pageable), HttpStatus.OK);
    }
}
