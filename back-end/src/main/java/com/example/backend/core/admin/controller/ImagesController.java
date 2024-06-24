package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.repository.ImagesAdminRepository;
import com.example.backend.core.model.Images;
import com.example.backend.core.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
public class ImagesController {

    private static final Logger logger = Logger.getLogger(ImagesController.class.getName());

    @Autowired
    private ImagesAdminRepository imagesAdminRepository;

    @PostMapping("/api/admin/images/upload")
    public ResponseEntity<ImageUploadResponse> uploadImages(@RequestParam("image") MultipartFile file, @RequestParam("idProduct") Long idProduct) {
        try {
            logger.info("Uploading image for product ID: " + idProduct);

            // Check if the file is empty
            if (file.isEmpty()) {
                logger.warning("Failed to upload because the file is empty.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ImageUploadResponse("File is empty"));
            }

            // Save the image to the repository
            Images imageEntity = Images.builder()
                    .imageName(file.getOriginalFilename())
                    .idProduct(idProduct)
                    .createDate(LocalDate.now())
                    .image(ImageUtility.compressImage(file.getBytes()))
                    .build();

            imagesAdminRepository.save(imageEntity);

            logger.info("Image uploaded successfully: " + file.getOriginalFilename());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ImageUploadResponse("Image uploaded successfully: " + file.getOriginalFilename()));
        } catch (IOException e) {
            logger.severe("Error uploading image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageUploadResponse("Image upload failed: " + e.getMessage()));
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageUploadResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }

    @PostMapping("/api/admin/images/update")
    public ResponseEntity<ImageUploadResponse> updateImage(@RequestParam("image") MultipartFile file, @RequestParam("idProduct") Long idProduct) {
        try {
            logger.info("Updating image for product ID: " + idProduct);

            // Check if the file is empty
            if (file.isEmpty()) {
                logger.warning("Failed to update because the file is empty.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ImageUploadResponse("File is empty"));
            }

            // Find existing images for the product
            List<Images> existingImages = imagesAdminRepository.findByIdProduct(idProduct);

            if (!existingImages.isEmpty()) {
                // Update the first image (or you can loop through and update all if needed)
                Images existingImage = existingImages.get(0);
                existingImage.setImageName(file.getOriginalFilename());
                existingImage.setCreateDate(LocalDate.now());
                existingImage.setImage(ImageUtility.compressImage(file.getBytes()));

                imagesAdminRepository.save(existingImage);

                logger.info("Image updated successfully: " + file.getOriginalFilename());

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ImageUploadResponse("Image updated successfully: " + file.getOriginalFilename()));
            } else {
                logger.warning("No existing image found for product ID: " + idProduct);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ImageUploadResponse("No existing image found for product ID: " + idProduct));
            }
        } catch (IOException e) {
            logger.severe("Error updating image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageUploadResponse("Image update failed: " + e.getMessage()));
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageUploadResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping(path = {"/view/anh/{idProduct}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("idProduct") Long idProduct) {
        logger.info("Fetching images for product ID: " + idProduct);
        final List<Images> dbImageList = imagesAdminRepository.findByIdProduct(idProduct);

        if (!dbImageList.isEmpty()) {
            Images image = dbImageList.get(0); // Get the first element in the list
            logger.info("Image found: " + image.getImageName());
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
            logger.warning("No images found for product ID: " + idProduct);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
