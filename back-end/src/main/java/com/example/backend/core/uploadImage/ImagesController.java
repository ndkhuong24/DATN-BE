package com.example.backend.core.uploadImage;

import com.example.backend.core.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin
public class ImagesController {
    @Autowired
    private ImagesRepository imagesRepository;

    @PostMapping("/upload/image")
    public ResponseEntity<ImageUploadResponse> uploadImages(@RequestParam("image") MultipartFile file, @RequestParam("idProduct") Long idProduct) throws IOException {
        imagesRepository.save(Images.builder()
                .imageName(file.getOriginalFilename())
                .idProduct(idProduct)
                .createDate(LocalDate.now())
                .image(ImageUtility.compressImage(file.getBytes()))
                .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @GetMapping(path = {"/get/image/{idProduct}and{imageName}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName,
                                           @PathVariable("idProduct") Long idProduct) throws IOException {
        final Optional<Images> dbImage = imagesRepository.findByImageNameAndIdProduct(imageName, idProduct);

        if (dbImage.isPresent()) {
            Images image = dbImage.get();
            MediaType mediaType;
            String imageNameLower = image.getImageName().toLowerCase();
            if (imageNameLower.endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (imageNameLower.endsWith(".jpg") || imageNameLower.endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else {
                mediaType = MediaType.APPLICATION_OCTET_STREAM; // Default to binary stream
            }

            return ResponseEntity
                    .ok()
                    .contentType(mediaType)
                    .body(ImageUtility.decompressImage(image.getImage()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
