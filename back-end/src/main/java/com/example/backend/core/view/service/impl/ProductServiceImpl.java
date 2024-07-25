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

import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<ProductDetail> listProductDetail = productDetailRepository.findByIdProduct(idProduct);
        MaterialDTO materialDTO = materialMapper.toDto(material.orElse(null));
        SoleDTO soleDTO = soleMapper.toDto(sole.orElse(null));
        CategoryDTO categoryDTO = categoryMapper.toDto(category.orElse(null));
        BrandDTO brandDTO = brandMapper.toDto(brand.orElse(null));

        for (ProductDetail pd : listProductDetail) {
            totalQuantity += pd.getQuantity();
        }

        productDTO.setProductDetailDTOList(productDetailMapper.toDto(listProductDetail));
        productDTO.setBrandDTO(brandDTO);
        productDTO.setMaterialDTO(materialDTO);
        productDTO.setSoleDTO(soleDTO);
        productDTO.setCategoryDTO(categoryDTO);
        productDTO.setTotalQuantity(totalQuantity);

        String imageURL = "http://localhost:8081/view/anh/" + idProduct;
        productDTO.setImageURL(imageURL);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        result.setData(productDTO);
        return result;
    }
    @Override
    public ServiceResult<List<ProductDTO>> GetAll() {
        List<Product> products = productRepository.findAll();
        ServiceResult<List<ProductDTO>> result = new ServiceResult<>();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            Integer totalQuantity = 0;
            ProductDTO productDTO = productMapper.toDto(product);

            Optional<Brand> brand = brandRepository.findById(product.getIdBrand());
            Optional<Material> material = materialRepository.findById(product.getIdMaterial());
            Optional<Sole> sole = soleRepository.findById(product.getIdSole());
            Optional<Category> category = categoryRepository.findById(product.getIdCategory());
            List<ProductDetail> listProductDetail = productDetailRepository.findByIdProduct(product.getId());

            MaterialDTO materialDTO = materialMapper.toDto(material.orElse(null));
            SoleDTO soleDTO = soleMapper.toDto(sole.orElse(null));
            CategoryDTO categoryDTO = categoryMapper.toDto(category.orElse(null));
            BrandDTO brandDTO = brandMapper.toDto(brand.orElse(null));

            for (ProductDetail pd : listProductDetail) {
                totalQuantity += pd.getQuantity();
            }
            // Khởi tạo giá min và giá max
            BigDecimal minPrice = new BigDecimal(Double.MAX_VALUE);
            BigDecimal maxPrice = new BigDecimal(Double.MIN_VALUE);

            // Duyệt qua từng đối tượng trong danh sách
            for (ProductDetail pd : listProductDetail) {
                BigDecimal price = pd.getPrice();
                // So sánh và cập nhật giá min và giá max
                if (price.compareTo(minPrice) < 0) {
                    minPrice = price;
                }
                if (price.compareTo(maxPrice) > 0) {
                    maxPrice = price;
                }
            }
            productDTO.setMinPrice(minPrice);
            productDTO.setMaxPrice(maxPrice);
            productDTO.setProductDetailDTOList(productDetailMapper.toDto(listProductDetail));
            productDTO.setBrandDTO(brandDTO);
            productDTO.setMaterialDTO(materialDTO);
            productDTO.setSoleDTO(soleDTO);
            productDTO.setCategoryDTO(categoryDTO);
            productDTO.setTotalQuantity(totalQuantity);

            String imageURL = "http://localhost:8081/view/anh/" + product.getId();
            productDTO.setImageURL(imageURL);

            productDTOs.add(productDTO);
        }

        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        result.setData(productDTOs);
        return result;
    }
    @Override
    public List<ProductDTO> getProductTuongTu(Long idProduct, Long idCategory) {
        List<ProductDTO> productDTOList = productCustomRepository.getProductTuongTu(idProduct, idCategory);
        return productDTOList;
    }


}
