package com.example.springessentials.controller;

import com.example.springessentials.model.dto.UploadDTOResponse;
import com.example.springessentials.model.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired FirebaseStorageService service;

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity upload(String fileName, String mimiType,
                                 @Param(value = "file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(new UploadDTOResponse(service.upload(fileName, mimiType, file)));
    }

    @PostMapping(value = "/save-path", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity savePath(String fileName, String mimiType,
                                   @Param(value = "file") MultipartFile file, String idStudent) throws IOException {
        return ResponseEntity.ok(service.uploadDatabase(fileName, mimiType, file, idStudent));
    }
}
