package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.model.Images;
import com.example.backend.core.salesCounter.dto.ProductSCDTO;
import com.example.backend.core.salesCounter.mapper.ProductSCMapper;
import com.example.backend.core.salesCounter.repository.DiscountSCRepository;
import com.example.backend.core.salesCounter.repository.ProductSalesCouterRepository;
import com.example.backend.core.salesCounter.service.ProductSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
public class ProductSCServiceImpl implements ProductSCService {
    @Autowired
    private ProductSalesCouterRepository productSCRepo;
    @Autowired
    private DiscountSCRepository discountSCRepository;
    @Autowired
    private ProductSCMapper productSCMapper;
    @Autowired
    private DiscountDetailAdminRepository discountDetailAdminRepository;
    @Autowired
    private ImageAdminRepository imageAdminRepository;
    @Autowired
    private ImagesAdminMapper imagesAdminMapper;
    @Autowired
    private SoleAdminRepository soleAdminRepository;
    @Autowired
    private SoleAdminMapper soleAdminMapper;
    @Autowired
    private MaterialAdminRepository materialAdminRepository;
    @Autowired
    private MaterialAdminMapper materialAdminMapper;
    @Autowired
    private BrandAdminRepository brandAdminRepository;
    @Autowired
    private BrandAdminMapper brandAdminMapper;
    @Autowired
    private CategoryAdminRepository categoryAdminRepository;
    @Autowired
    private CategoryAdminMapper categoryAdminMapper;
    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;
    @Autowired
    private ProductDetailAdminMapper productDetailAdminMapper;

    @Override
    public List<ProductSCDTO> getAllProductDetail() {
        List<ProductSCDTO> list = productSCMapper.toDto(productSCRepo.findAll());
        for (int i = 0; i < list.size(); i++) {
            List<Images> imagesList = imageAdminRepository.findByIdProduct(list.get(i).getId());
            if(!imagesList.isEmpty()){
                list.get(i).setImagesDTOList(imagesAdminMapper.toDto(imagesList));
            }
            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(soleAdminRepository.findById(list.get(i).getIdSole()).orElse(null));
            list.get(i).setSoleAdminDTO(soleAdminDTO);
            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(materialAdminRepository.findById(list.get(i).getIdMaterial()).orElse(null));
            list.get(i).setMaterialAdminDTO(materialAdminDTO);
            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brandAdminRepository.findById(list.get(i).getIdBrand()).orElse(null));
            list.get(i).setBrandAdminDTO(brandAdminDTO);
            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(categoryAdminRepository.findById(list.get(i).getIdCategory()).orElse(null));
            list.get(i).setCategoryAdminDTO(categoryAdminDTO);
            System.out.println(list.get(i).getId());
            List<ProductDetailAdminDTO> productDetailAdminDTO = productDetailAdminMapper.toDto(productDetailAdminRepository.findByIdProduct(list.get(i).getId()));
            list.get(i).setProductDetailDTOList(productDetailAdminDTO);
            List<Discount> discountList = discountSCRepository.getDiscountConApDung();
            for (int j = 0; j < discountList.size(); j++) {
                DiscountDetail discountDetail = discountDetailAdminRepository.findByIdDiscountAndIdProduct(discountList.get(j).getId(), list.get(i).getId());
                if (null != discountDetail) {
                    if (discountDetail.getDiscountType() == 0) {
                        list.get(i).setReducePrice(discountDetail.getReducedValue());
                        list.get(i).setPercentageReduce(Math.round(discountDetail.getReducedValue().divide(list.get(i).getPrice(),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).floatValue()));
                    }
                    if (discountDetail.getDiscountType() == 1) {
                        BigDecimal price = discountDetail.getReducedValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(list.get(i).getPrice());
                        if(price.compareTo(discountDetail.getMaxReduced()) >= 0){
                            list.get(i).setReducePrice(discountDetail.getMaxReduced());
                        }else {
                            list.get(i).setReducePrice(discountDetail.getReducedValue());
                        }
                        list.get(i).setPercentageReduce(discountDetail.getReducedValue().intValue());
                    }
                }

            }
        }
        return list;
    }
}
