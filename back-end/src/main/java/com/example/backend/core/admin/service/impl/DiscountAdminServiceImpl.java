package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.repository.DiscountAdminCustomRepository;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.ProductAdminRepository;
import com.example.backend.core.admin.repository.ProductDetailAdminRepository;
import com.example.backend.core.admin.service.DiscountAdminService;
import com.example.backend.core.admin.service.ProductDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountAdminServiceImpl implements DiscountAdminService {
    @Autowired
    private DiscountAdminRepository discountAdminRepository;

    @Autowired
    private DiscountAdminCustomRepository discountAdminCustomRepository;

    @Autowired
    private DiscountAdminMapper discountAdminMapper;

    @Override
    public List<String> getAllDiscountExport() {
        List<String> lstStr = discountAdminRepository.findAll()
                .stream()
                .map(b -> b.getId() + "-" + b.getName())
                .collect(Collectors.toList());
        return lstStr;
    }

    @Override
    public List<DiscountAdminDTO> getAll() {
        List<DiscountAdminDTO> discountAdminDTOList = discountAdminMapper.toDto(discountAdminRepository.findAll());
        return discountAdminDTOList;
    }


//    @Override
//    public ServiceResult<DiscountAdminDTO> KichHoat(Long idDiscount) {
//        ServiceResult<DiscountAdminDTO> serviceResult = new ServiceResult<>();
//        Optional<Discount> optionalDiscount = discountAdminRepository.findById(idDiscount);
//
//        if (optionalDiscount.isPresent()) {
//            boolean flag = false;
//            List<ProductAdminDTO> lstProduct = discountAdminCustomRepository.getAllProductKickHoat();
//            DiscountAdminDTO discountAdminDTO = getDetailDiscount(optionalDiscount.get().getId());
//            for (int i = 0; i < lstProduct.size(); i++) {
//                for (int j = 0; j < discountAdminDTO.getProductDTOList().size(); j++) {
//                    if(lstProduct.get(i).getId() == discountAdminDTO.getProductDTOList().get(j).getId()){
//                        flag = true;
//                        break;
//                    }
//                }
//            }
//            if(!flag){
//                Discount discount = optionalDiscount.get();
//                discount.setIdel(discount.getIdel() == 1 ? 0 : 1);
//                discount =  discountAdminRepository.save(discount);
//                DiscountAdminDTO voucherAdminDTO = discountAdminMapper.toDto(discount);
//                serviceResult.setData(voucherAdminDTO);
//                serviceResult.setStatus(HttpStatus.OK);
//                serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu
//            }else {
//                serviceResult.setMessage("Sản phẩm trong khuyến mãi này đang được áp dụng ở nơi khác");
//                serviceResult.setStatus(HttpStatus.BAD_REQUEST);
//                serviceResult.setData(null);
//            }
//
//        } else {
//            serviceResult.setMessage("Không tìm thấy khuyến mãi");
//            serviceResult.setStatus(HttpStatus.NOT_FOUND);
//            serviceResult.setData(null);
//        }
//
//        return serviceResult;
//    }
//
//    @Override
//    public ServiceResult<DiscountAdminDTO> setIdel(Long idDiscount) {
//        ServiceResult<DiscountAdminDTO> serviceResult = new ServiceResult<>();
//        Optional<Discount> optionalDiscount = discountAdminRepository.findById(idDiscount);
//
//        if (optionalDiscount.isPresent()) {
//                Discount discount = optionalDiscount.get();
//                discount.setIdel(0);
//                discount =  discountAdminRepository.save(discount);
//                DiscountAdminDTO voucherAdminDTO = discountAdminMapper.toDto(discount);
//                serviceResult.setData(voucherAdminDTO);
//                serviceResult.setStatus(HttpStatus.OK);
//                serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu
//
//        } else {
//            serviceResult.setMessage("Không tìm thấy khuyến mãi");
//            serviceResult.setStatus(HttpStatus.NOT_FOUND);
//            serviceResult.setData(null);
//        }
//
//        return serviceResult;
//    }
}
