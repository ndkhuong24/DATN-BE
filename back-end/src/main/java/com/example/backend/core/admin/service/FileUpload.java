package com.example.backend.core.admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUpload {
    String uploadFile(MultipartFile multipartFile) throws IOException;
}
