package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.mapper.ColorAdminMapper;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.mapper.ProductDetailAdminMapper;
import com.example.backend.core.admin.mapper.SizeAdminMapper;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.ProductDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Color;
import com.example.backend.core.model.Product;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private SizeAdminRepository sizeAdminRepository;

    @Override
    public List<ProductDetailAdminDTO> getAll() {
        List<ProductDetail> productDetailList = productDetailAdminRepository.findAll();

        List<ProductDetailAdminDTO> productDetailAdminDTOList = new ArrayList<>();

        for (ProductDetail productDetail : productDetailList) {
            ProductDetailAdminDTO productDetailAdminDTO = productDetailAdminMapper.toDto(productDetail);

            productDetailAdminDTO.setPrice(productDetail.getPrice());

            ColorAdminDTO colorAdminDTO = colorAdminMapper.toDto(colorAdminRepository.findById(productDetail.getIdColor()).orElse(null));
            productDetailAdminDTO.setColorDTO(colorAdminDTO);

            SizeAdminDTO sizeAdminDTO = sizeAdminMapper.toDto(sizeAdminRepository.findById(productDetail.getIdSize()).orElse(null));
            productDetailAdminDTO.setSizeDTO(sizeAdminDTO);

            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(productDetail.getIdProduct()).orElse(null));
            productDetailAdminDTO.setProductDTO(productAdminDTO);

            productDetailAdminDTOList.add(productDetailAdminDTO);
        }

        return productDetailAdminDTOList;
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
        productDetail.setPrice(productDetailAdminDTO.getPrice());
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
        if (optional.isPresent()) {
            ProductDetail productDetail = optional.get();
            productDetail.setId(id);
            productDetail.setIdProduct(productDetailAdminDTO.getIdProduct());
            productDetail.setIdColor(productDetailAdminDTO.getIdColor());
            productDetail.setIdSize(productDetailAdminDTO.getIdSize());
            productDetail.setQuantity(productDetailAdminDTO.getQuantity());
            productDetail.setPrice(productDetailAdminDTO.getPrice());
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
    public ServiceResult<List<ProductDetailAdminDTO>> getProductDetailsByProductId(long idProduct) {
        ServiceResult<List<ProductDetailAdminDTO>> result = new ServiceResult<>();

        List<ProductDetail> productDetailList = productDetailAdminRepository.findByIdProduct(idProduct);

        // Kiểm tra danh sách sản phẩm có rỗng hay không
        if (!productDetailList.isEmpty()) {
            // Chuyển đổi danh sách ProductDetail thành danh sách ProductDetailAdminDTO
            List<ProductDetailAdminDTO> productDetailDTOList = productDetailList.stream()
                    .map(productDetail -> {
                        Product product = productAdminRepository.findById(productDetail.getIdProduct()).orElse(null);
                        Color color = colorAdminRepository.findById(productDetail.getIdColor()).orElse(null);
                        Size size = sizeAdminRepository.findById(productDetail.getIdSize()).orElse(null);

                        // Chuyển đổi đối tượng thành DTO
                        ProductAdminDTO productAdminDTO = product != null ? productAdminMapper.toDto(product) : null;
                        ColorAdminDTO colorAdminDTO = color != null ? colorAdminMapper.toDto(color) : null;
                        SizeAdminDTO sizeAdminDTO = size != null ? sizeAdminMapper.toDto(size) : null;

//                        BigDecimal listedPrice = productDetail.getPrice();
//                        BigDecimal totalBestSeller = productDetail.getPrice();

                        return new ProductDetailAdminDTO(
                                productDetail.getId(),
                                productDetail.getIdProduct(),
                                productDetail.getIdColor(),
                                productDetail.getIdSize(),
                                productDetail.getQuantity(),
                                productDetail.getPrice(),
                                productDetail.getShoeCollar(),
//                                listedPrice,
//                                totalBestSeller,
                                productAdminDTO,
                                colorAdminDTO,
                                sizeAdminDTO
                        );
                    }).collect(Collectors.toList());

            result.setStatus(HttpStatus.OK);
            result.setMessage("Lấy thông tin sản phẩm thành công");
            result.setData(productDetailDTOList);
        } else {
            // Nếu không tìm thấy sản phẩm, thiết lập kết quả thất bại
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy sản phẩm");
        }
        return result;
    }

    @Override
    public List<ProductDetailAdminDTO> findByNameLikeOrCodeLike(String param) {
        List<ProductDetail> productDetailList = productDetailAdminRepository.findByNameLikeOrCodeLike("%" + param + "%", "%" + param + "%");

        List<ProductDetailAdminDTO> productDetailAdminDTOList = productDetailAdminMapper.toDto(productDetailList);

        for (int i = 0; i < productDetailAdminDTOList.size(); i++) {
            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(productDetailAdminDTOList.get(i).getIdProduct()).orElse(null));
            String imageURL = "http://localhost:8081/view/anh/" + productDetailAdminDTOList.get(i).getIdProduct();
            productAdminDTO.setImageURL(imageURL);
            productDetailAdminDTOList.get(i).setProductDTO(productAdminDTO);

            ColorAdminDTO colorAdminDTO = colorAdminMapper.toDto(colorAdminRepository.findById(productDetailAdminDTOList.get(i).getIdColor()).orElse(null));
            productDetailAdminDTOList.get(i).setColorDTO(colorAdminDTO);

            SizeAdminDTO sizeAdminDTO = sizeAdminMapper.toDto(sizeAdminRepository.findById(productDetailAdminDTOList.get(i).getIdSize()).orElse(null));
            productDetailAdminDTOList.get(i).setSizeDTO(sizeAdminDTO);
        }

        return productDetailAdminDTOList;
    }

}
