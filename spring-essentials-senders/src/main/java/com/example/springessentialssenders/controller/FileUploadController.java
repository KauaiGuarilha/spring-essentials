package com.example.springessentialssenders.controller;

import com.example.springessentialssenders.model.dto.UploadDTOResponse;
import com.example.springessentialssenders.model.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("v1")
public class FileUploadController {

    @Autowired FirebaseStorageService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/upload-file", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity upload(@NotBlank String fileName, @NotBlank String mimiType,
                                 @Param(value = "file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(new UploadDTOResponse(service.upload(fileName, mimiType, file)));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/protected/save-path", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity savePath(@NotBlank String fileName, @NotBlank String mimiType,
                                   @Param(value = "file") MultipartFile file, String idStudent) throws IOException {
        return ResponseEntity.ok(service.uploadDatabase(fileName, mimiType, file, idStudent));
    }
    //TODO Create annotation to validation the field MultipartFile
}
