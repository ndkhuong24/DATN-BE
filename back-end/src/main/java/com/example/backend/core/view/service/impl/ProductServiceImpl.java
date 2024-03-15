package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.*;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.*;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCustomRepository productCustomRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SoleRepository soleRepository;
    @Autowired
    private SoleMapper soleMapper;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesMapper imagesMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountDetailRepository discountDetailRepository;

    @Override
    public List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu) {
        List<ProductDTO> lst = productCustomRepository.getProductNoiBatByBrand(thuongHieu);
        return lst;
    }

    @Override
    public ServiceResult<ProductDTO> getDetailProduct(Long idProduct) {
        Optional<Product> product = productRepository.findById(idProduct);
        ServiceResult<ProductDTO> result = new ServiceResult<>();
        Integer totalQuantity = 0;
        if (!product.isPresent()) {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Product không tồn tại !");
            result.setData(null);
            return result;
        }
        ProductDTO productDTO = productMapper.toDto(product.orElse(null));
        Optional<Brand> brand = brandRepository.findById(product.get().getIdBrand());
        Optional<Material> material = materialRepository.findById(product.get().getIdMaterial());
        Optional<Sole> sole = soleRepository.findById(product.get().getIdSole());
        Optional<Category> category = categoryRepository.findById(product.get().getIdCategory());
        List<Images> imageList = imagesRepository.findByIdProduct(product.get().getId());
        List<ProductDetail> listProductDetail = productDetailRepository.findByIdProduct(idProduct);
        MaterialDTO materialDTO = materialMapper.toDto(material.orElse(null));
        SoleDTO soleDTO = soleMapper.toDto(sole.orElse(null));
        CategoryDTO categoryDTO = categoryMapper.toDto(category.orElse(null));
        BrandDTO brandDTO = brandMapper.toDto(brand.orElse(null));
        for (ProductDetail pd : listProductDetail) {
            totalQuantity += pd.getQuantity();
        }
        productDTO.setProductDetailDTOList(productDetailMapper.toDto(listProductDetail));
        productDTO.setImagesDTOList(imagesMapper.toDto(imageList));
        productDTO.setBrandDTO(brandDTO);
        productDTO.setMaterialDTO(materialDTO);
        productDTO.setSoleDTO(soleDTO);
        productDTO.setCategoryDTO(categoryDTO);
        productDTO.setTotalQuantity(totalQuantity);
        List<Discount> discountList = discountRepository.getDiscountConApDung();
        for (int i = 0; i < discountList.size(); i++) {
            DiscountDetail discountDetail = discountDetailRepository.findByIdDiscountAndIdProduct(discountList.get(i).getId(), productDTO.getId());
            if (null != discountDetail) {
                if (discountDetail.getDiscountType() == 0) {
                    productDTO.setReducePrice(discountDetail.getReducedValue());
                    productDTO.setPercentageReduce(Math.round(discountDetail.getReducedValue().divide(productDTO.getPrice(),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).floatValue()));
                }
                if (discountDetail.getDiscountType() == 1) {
                    BigDecimal price = discountDetail.getReducedValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(productDTO.getPrice());
                    if(price.compareTo(discountDetail.getMaxReduced()) >= 0){
                        productDTO.setReducePrice(discountDetail.getMaxReduced());
                    }else {
                        productDTO.setReducePrice(discountDetail.getReducedValue());
                    }
                    productDTO.setPercentageReduce(discountDetail.getReducedValue().intValue());
                }
            }

        }
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        result.setData(productDTO);
        return result;
    }

    @Override
    public List<ProductDTO> getProductTuongTu(Long idProduct, Long idCategory) {
        List<ProductDTO> lst = productCustomRepository.getProductTuongTu(idProduct, idCategory);
        return lst;
    }
}
