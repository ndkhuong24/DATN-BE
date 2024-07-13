package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Product;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.ColorMapper;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import com.example.backend.core.view.mapper.ProductMapper;
import com.example.backend.core.view.mapper.SizeMapper;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeMapper sizeMapper;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountDetailRepository discountDetailRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServiceResult<CartDTO> getCart(CartDTO cartDTO) {
        ServiceResult<CartDTO> cartDTOServiceResult = new ServiceResult<>();

        CartDTO cartDTO1 = new CartDTO();

        Optional<Product> product = productRepository.findById(cartDTO.getProductId());

        if (!product.isPresent()) {
            cartDTOServiceResult.setData(null);
            cartDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            cartDTOServiceResult.setMessage("Không tìm thấy product!");
            return cartDTOServiceResult;
        }

        ProductDTO productDTO = productMapper.toDto(product.get());
        String imageURL ="http://localhost:8081/view/anh/"+cartDTO.getProductId();
        productDTO.setImageURL(imageURL);

        ProductDetail productDetail = productDetailRepository.findByIdSizeAndIdColorAndIdProduct(cartDTO.getProductDetailDTO().getColorDTO().getId(), cartDTO.getProductDetailDTO().getSizeDTO().getId(), cartDTO.getProductId());

        if (productDetail == null) {
            cartDTOServiceResult.setData(null);
            cartDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            cartDTOServiceResult.setMessage("product detail is null");
            return cartDTOServiceResult;
        }

        ColorDTO colorDTO = colorMapper.toDto(colorRepository.findById(productDetail.getIdColor()).get());
        SizeDTO sizeDTO = sizeMapper.toDto(sizeRepository.findById(productDetail.getIdSize()).get());

        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        productDetailDTO.setSizeDTO(sizeDTO);
        productDetailDTO.setColorDTO(colorDTO);

        productDTO.setProductDetailDTO(productDetailDTO);

        cartDTO1.setProductId(product.get().getId());
        cartDTO1.setProductName(product.get().getName());
        cartDTO1.setQuantity(cartDTO.getQuantity());

//        List<Discount> discountList = discountRepository.getDiscountConApDung();
//        for (int i = 0; i < discountList.size(); i++) {
//            DiscountDetail discountDetail = discountDetailRepository.findByIdDiscountAndIdProduct(discountList.get(i).getId(), productDTO.getId());
//            if (null != discountDetail) {
//                if (discountDetail.getDiscountType() == 0) {
//                    productDTO.setReducePrice(discountDetail.getReducedValue());
//                    productDTO.setPercentageReduce(Math.round(discountDetail.getReducedValue().divide(productDTO.getPrice(),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).floatValue()));
//                }
//                if (discountDetail.getDiscountType() == 1) {
//                    BigDecimal price = discountDetail.getReducedValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(productDTO.getPrice());
//                    if(price.compareTo(discountDetail.getMaxReduced()) >= 0){
//                        productDTO.setReducePrice(discountDetail.getMaxReduced());
//                    }else {
//                    productDTO.setPercentageReduce(Math.round(discountDetail.getReducedValue().divide(productDTO.getPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).floatValue()));
//                }
//                if (discountDetail.getDiscountType() == 1) {
//                    BigDecimal price = discountDetail.getReducedValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(productDTO.getPrice());
//                    if (price.compareTo(discountDetail.getMaxReduced()) >= 0) {
//                        productDTO.setReducePrice(discountDetail.getMaxReduced());
//                    } else {
//                        productDTO.setReducePrice(discountDetail.getReducedValue());
//                    }
//                    productDTO.setPercentageReduce(discountDetail.getReducedValue().intValue());
//                }
//            }
//        }

        cartDTO1.setProductDTO(productDTO);
        cartDTO1.setProductDetailDTO(productDetailDTO);

        cartDTOServiceResult.setStatus(HttpStatus.OK);
        cartDTOServiceResult.setMessage("Success");
        cartDTOServiceResult.setData(cartDTO1);
        cartDTOServiceResult.setSuccess(true);
        return cartDTOServiceResult;
    }
}
