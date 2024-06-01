package com.example.backend.core.uploadImage;

import com.example.backend.core.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("uploadImagesRepository")
public interface ImagesRepository extends JpaRepository<Images, Long> {
    Optional<Images> findByImageNameAndIdProduct(String imageName, Long idProduct);
}
