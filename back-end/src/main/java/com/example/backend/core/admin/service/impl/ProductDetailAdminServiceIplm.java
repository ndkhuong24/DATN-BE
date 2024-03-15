package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.ColorAdminRepository;
import com.example.backend.core.admin.repository.ProductAdminRepository;
import com.example.backend.core.admin.repository.ProductDetailAdminRepository;
import com.example.backend.core.admin.repository.SizeAdminReposiotry;
import com.example.backend.core.admin.service.ProductDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Color;
import com.example.backend.core.model.Product;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailAdminServiceIplm implements ProductDetailAdminService {
    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;
    @Autowired
    private ProductDetailAdminMapper productDetailAdminMapper;
    @Autowired
    private ColorAdminRepository colorAdminRepository;
    @Autowired
    private SizeAdminReposiotry sizeAdminReposiotry;
    @Autowired
    private ColorAdminMapper colorAdminMapper;
    @Autowired
    private SizeAdminMapper sizeAdminMapper;
    @Autowired
    private ProductAdminRepository productAdminRepository;
    @Autowired
    private ProductAdminMapper productAdminMapper;

    private ServiceResult<ProductDetailAdminDTO> result = new ServiceResult<>();
    @Override
    public List<ProductDetailAdminDTO> getAll() {
        List<ProductDetailAdminDTO> list = productDetailAdminMapper.toDto(productDetailAdminRepository.findAll());
        for (int i = 0; i < list.size(); i++) {
            ColorAdminDTO colorAdminDTO = colorAdminMapper.toDto(colorAdminRepository.findById(list.get(i).getIdColor()).orElse(null));
            list.get(i).setColorDTO(colorAdminDTO);
            SizeAdminDTO sizeAdminDTO = sizeAdminMapper.toDto(sizeAdminReposiotry.findById(list.get(i).getIdSize()).orElse(null));
            list.get(i).setSizeDTO(sizeAdminDTO);
            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(list.get(i).getIdProduct()).orElse(null));
            list.get(i).setProductDTO(productAdminDTO);
        }
        return list;
    }

    @Override
    public ServiceResult<ProductDetailAdminDTO> add(ProductDetailAdminDTO productDetailAdminDTO) {
        ProductDetail productDetail = productDetailAdminMapper.toEntity(productDetailAdminDTO);
        Optional<Color> color = colorAdminRepository.findById(productDetail.getIdColor());
        Optional<Size> size = sizeAdminReposiotry.findById(productDetail.getIdSize());
        Optional<Product> product = productAdminRepository.findById(productDetail.getIdProduct());
        ColorAdminDTO colorAdminDTO = colorAdminMapper.toDto(color.orElse(null));
        SizeAdminDTO sizeAdminDTO = sizeAdminMapper.toDto(size.orElse(null));
        ProductAdminDTO productAdminDTO = productAdminMapper.toDto(product.orElse(null));
        productDetail.setIdProduct(productAdminDTO.getId());
        productDetail.setIdColor(colorAdminDTO.getId());
        productDetail.setIdSize(sizeAdminDTO.getId());
        productDetail.setQuantity(productDetailAdminDTO.getQuantity());
        productDetail.setShoeCollar(productDetailAdminDTO.getShoeCollar());
        productDetailAdminDTO.setProductDTO(productAdminDTO);
        productDetailAdminDTO.setColorDTO(colorAdminDTO);
        productDetailAdminDTO.setSizeDTO(sizeAdminDTO);
        this.productDetailAdminRepository.save(productDetail);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(productDetailAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<ProductDetailAdminDTO> update(ProductDetailAdminDTO productDetailAdminDTO, Long id) {
        Optional<ProductDetail> optional = this.productDetailAdminRepository.findById(id);
        if (optional.isPresent()){
            ProductDetail productDetail = optional.get();
            productDetail.setId(id);
            productDetail.setIdProduct(productDetailAdminDTO.getIdProduct());
            productDetail.setIdColor(productDetailAdminDTO.getIdColor());
            productDetail.setIdSize(productDetailAdminDTO.getIdSize());
            productDetail.setQuantity(productDetailAdminDTO.getQuantity());
            productDetail.setShoeCollar(productDetailAdminDTO.getShoeCollar());
            productDetail = this.productDetailAdminRepository.save(productDetail);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(productDetailAdminMapper.toDto(productDetail));

        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ProductDetailAdminDTO> delete(Long id) {
        Optional<ProductDetail> optional = this.productDetailAdminRepository.findById(id);
        if (optional.isPresent()) {
            this.productDetailAdminRepository.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ProductDetailAdminDTO> getById(Long id) {
        ServiceResult<ProductDetailAdminDTO> result = new ServiceResult<>();
        Optional<ProductDetail> optional = this.productDetailAdminRepository.findById(id);

        if (optional.isPresent()) {
            ProductDetail productDetail = optional.get();
            ProductDetailAdminDTO productDetailAdminDTO = productDetailAdminMapper.toDto(productDetail);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Lấy thông tin thành công");
            result.setData(productDetailAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy chi tiet sản phẩm");
        }

        return result;
    }

    @Override
    public List<ProductDetailAdminDTO> getProductDetails(int idColor, int idSize) {
        List<ProductDetailAdminDTO> productDetailAdminDTOS = new ArrayList<>();
        return productDetailAdminDTOS.stream()
                .filter(detail -> detail.getIdColor() == idColor && detail.getIdSize() == idSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetailAdminDTO> getProductDetailsByProductId(int idProduct) {
        List<ProductDetailAdminDTO> productDetailAdminDTOSs = new ArrayList<>();
        return productDetailAdminDTOSs.stream()
                .filter(detail -> detail.getIdProduct() == idProduct)
                .collect(Collectors.toList());
    }
}
