package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.repository.ImagesAdminRepository;
import com.example.backend.core.admin.service.FileUpload;
import com.example.backend.core.admin.service.ImageAdminService;
import com.example.backend.core.admin.service.impl.CloudinaryService;
import com.example.backend.core.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class CloudinaryAdminController {
    @Autowired
    private ImageAdminService imsv;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagesAdminRepository imagesAdminRepository;

    @Autowired
    private FileUpload fileUpload;

    @PostMapping("/upload-img-file")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(required = false) Long idProduct) throws IOException {
            String imageURL = fileUpload.uploadFile(file);
            Images images = new Images();
            images.setImageName(imageURL);
            images.setCreateDate(LocalDate.now());
            images.setIdProduct(idProduct);
            imagesAdminRepository.save(images);
        return "Success";
    }
}
