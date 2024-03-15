package com.example.backend.core.admin.service;

import com.example.backend.core.model.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageAdminService {
//    List<Images> list();
//    Optional<Images> getOne(Long id);
    void save(Images images);
//    void Delete(Long id);
//    boolean exists(Long id);
//    void saveImage(byte[] imageData,Long idproduct);
    String UploadFile(MultipartFile multipartFile) throws IOException;




}
