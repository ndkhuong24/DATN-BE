package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.repository.ImageAdminRepository;
import com.example.backend.core.admin.service.ImageAdminService;
import com.example.backend.core.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageAdminServiceIplm implements ImageAdminService {
    @Autowired
    private ImageAdminRepository imrp;
//    @Override
//    public List<Images> list() {
//        return imrp.findByOrderById();
//    }
//
//    @Override
//    public Optional<Images> getOne(Long id) {
//        return imrp.findById(id);
//    }
//
//    @Override
//    public void save(Images images) {
//        imrp.save(images);
//    }
//
//    @Override
//    public void Delete(Long id) {
//        imrp.deleteById(id);
//    }
//
//    @Override
//    public boolean exists(Long id) {
//        return imrp.existsById(id);
//    }
//
//    @Override
//    public void saveImage(byte[] imageData,Long idproduct) {
//        Images images = new Images();
////        images.setId(imageData);
//        images.setIdProduct(idproduct);
//        imrp.save(images);
//    }

    @Override
    public void save(Images images) {
        imrp.save(images);
    }

    @Override
    public String UploadFile(MultipartFile multipartFile) throws IOException {
        return null;
    }
}
