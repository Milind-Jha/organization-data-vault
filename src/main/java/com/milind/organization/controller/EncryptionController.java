package com.milind.organization.controller;

import com.milind.organization.service.FileServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/organization")
public class EncryptionController {

    @Autowired
    private FileServiceClient fileServiceClient; // Inject Feign client

    @PostMapping("/sendFile")
    public Mono<ResponseEntity<String>> sendFile(@RequestBody MultipartFile file) {
        // Using the Feign client to send the file non-blocking
        return fileServiceClient.uploadFile(file)
                .map(response -> ResponseEntity.ok("File uploaded successfully: " + response))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(500).body("Error: " + error.getMessage())));
    }

}
