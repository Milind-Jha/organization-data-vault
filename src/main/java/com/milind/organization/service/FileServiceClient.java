package com.milind.organization.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@FeignClient(name = "file-service", url = "http://another-service-url")
public interface FileServiceClient {

    @PostMapping("/uploadFile")
    Mono<String> uploadFile(@RequestPart("file") MultipartFile file); // non-blocking return type Mono
}
