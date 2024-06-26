package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.mapper.DiscountAdminMapper;
import com.example.backend.core.admin.mapper.DiscountDetailAdminMapper;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.mapper.ProductDetailAdminMapper;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.DiscountAdminService;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.admin.service.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiscountDetailAdminServiceImpl implements DiscountDetailAdminService {
    @Autowired
    private DiscountDetailAdminRepository discountDetailRepository;

    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;

    @Autowired
    private ProductDetailAdminMapper productDetailAdminMapper;

    @Autowired
    private DiscountAdminService discountAdminService;

    @Autowired
    private DiscountDetailAdminMapper discountDetailAdminMapper;

    @Autowired
    private DiscountAdminRepository discountAdminRepository;

    @Autowired
    private DiscountAdminMapper discountAdminMapper;

    @Autowired
    private ProductAdminRepository productAdminRepository;

    @Autowired
    private ProductAdminMapper productAdminMapper;

    @Autowired
    private DiscountAdminCustomRepository discountAdminCustomRepository;

    @Autowired
    private OrderDetailAdminRepository orderDetailAdminRepository;

    @Autowired
    private ProductAdminService productAdminService;

//    @Override
//    public List<DiscountAdminDTO> getAll() {
//        List<DiscountAdminDTO> discountAdminDTOList = discountAdminCustomRepository.getAll();
//        for (int i = 0; i < discountAdminDTOList.size(); i++) {
//            if (discountAdminDTOList.get(i).getUsed_count() > 0) {
//                DiscountAdminDTO discountAdminDTO = discountAdminDTOList.get(i);
//                discountAdminDTO.setIsUpdate(1);
//            }
//        }
//        return discountAdminDTOList;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllKichHoat() {
//        List<DiscountAdminDTO> discountAdminDTOList = discountAdminCustomRepository.getAllKichHoat();
//        return discountAdminDTOList;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllKhongKichHoat() {
//        List<DiscountAdminDTO> discountAdminDTOList = discountAdminCustomRepository.getAllKhongKichHoat();
//        return discountAdminDTOList;
//    }
//
//    @Override
//    public ServiceResult<Void> deleteDiscountById(Long idDiscount) {
//        ServiceResult<Void> serviceResult = new ServiceResult<>();
//
//        Optional<Discount> discount = discountAdminRepository.findById(idDiscount);
//
//        if (discount.isPresent()) {
//            Discount discount1 = discount.get();
//            discount1.setDelete(1);
//            discountAdminRepository.save(discount1);
//
//            serviceResult.setMessage("Xoa thanh cong");
//            serviceResult.setStatus(HttpStatus.OK);
//            serviceResult.setSuccess(true);
//        } else {
//            serviceResult.setMessage("Khong tim thay isDiscount");
//            serviceResult.setStatus(HttpStatus.NOT_FOUND);
//            serviceResult.setSuccess(false);
//        }
//        return serviceResult;
//    }
//
//    @Override
//    public DiscountAdminDTO getDetailDiscountById(Long idDiscount) {
//        Discount discount = discountAdminRepository.findById(idDiscount).get();
//        if (discount == null) {
//            return null;
//        }
//        DiscountAdminDTO discountAdminDTO = discountAdminMapper.toDto(discount);
//        List<DiscountDetail> discountDetailsList = discountDetailRepository.findDiscountDetailByIdDiscount(discount.getId());
//
////        List<ProductAdminDTO> productAdminDTOList = new ArrayList<>();
//        List<ProductDetailAdminDTO> productDetailAdminDTOList = new ArrayList<>();
//        if (discountDetailsList.size() > 0) {
//            for (int i = 0; i < discountDetailsList.size(); i++) {
//                ProductDetailAdminDTO productDetailAdminDTO = productDetailAdminMapper.toDto(productDetailAdminRepository.findById(discountDetailsList.get(i).getIdProductDetail()).orElse(null));
//                productDetailAdminDTOList.add(productDetailAdminDTO);
////                ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(discountDetailsList.get(i).getIdProduct()).orElse(null));
////                productAdminDTOList.add(productAdminDTO);
//            }
//            discountAdminDTO.setReducedValue(discountDetailsList.get(0).getReducedValue());
//            discountAdminDTO.setDiscountType(discountDetailsList.get(0).getDiscountType());
//            discountAdminDTO.setMaxReduced(discountDetailsList.get(0).getMaxReduced());
//            discountAdminDTO.setProductDetailAdminDTOList(productDetailAdminDTOList);
//        }
//        return discountAdminDTO;
//    }
//
//    @Override
//    public ServiceResult<DiscountAdminDTO> KichHoat(Long id) {
//        ServiceResult<DiscountAdminDTO> serviceResult = new ServiceResult<>();
//
//        Optional<Discount> discountOptional = discountAdminRepository.findById(id);
//
//        if (discountOptional.isPresent()) {
//            boolean flag = false;
////            List<ProductAdminDTO> productAdminDTOList = discountAdminCustomRepository.getAllProductKickHoat();
//
//            List<ProductAdminDTO> allProducts = productAdminService.getAll();
//            List<ProductAdminDTO> productAdminDTOList = allProducts.stream()
//                    .filter(product -> product.getStatus() == 0)
//                    .collect(Collectors.toList());
//
//            DiscountAdminDTO discountAdminDTO = getDetailDiscountById(discountOptional.get().getId());
//
//
//
//        }
//
//        if (optionalDiscount.isPresent()) {
//            boolean flag = false;
//
//            List<ProductAdminDTO> lstProduct = discountAdminCustomRepository.getAllProductKickHoat();
//            DiscountAdminDTO discountAdminDTO = getDetailDiscount(optionalDiscount.get().getId());
//
//            for (int i = 0; i < lstProduct.size(); i++) {
//                for (int j = 0; j < discountAdminDTO.getProductDTOList().size(); j++) {
//                    if (lstProduct.get(i).getId() == discountAdminDTO.getProductDTOList().get(j).getId()) {
//                        flag = true;
//                        break;
//                    }
//                }
//            }
//            if (!flag) {
//                Discount discount = optionalDiscount.get();
//                discount.setIdel(discount.getIdel() == 1 ? 0 : 1);
//                discount = discountAdminRepository.save(discount);
//                DiscountAdminDTO voucherAdminDTO = discountAdminMapper.toDto(discount);
//                serviceResult.setData(voucherAdminDTO);
//                serviceResult.setStatus(HttpStatus.OK);
//                serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu
//            } else {
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
//        return serviceResult;
//        return null;
//    }

//    @Override
//    public ServiceResult<DiscountAdminDTO> setIdel(Long id) {
//        //        ServiceResult<DiscountAdminDTO> serviceResult = new ServiceResult<>();
////        Optional<Discount> optionalDiscount = discountAdminRepository.findById(idDiscount);
////
////        if (optionalDiscount.isPresent()) {
////            Discount discount = optionalDiscount.get();
////            discount.setIdel(0);
////            discount = discountAdminRepository.save(discount);
////            DiscountAdminDTO voucherAdminDTO = discountAdminMapper.toDto(discount);
////            serviceResult.setData(voucherAdminDTO);
////            serviceResult.setStatus(HttpStatus.OK);
////            serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu
////
////        } else {
////            serviceResult.setMessage("Không tìm thấy khuyến mãi");
////            serviceResult.setStatus(HttpStatus.NOT_FOUND);
////            serviceResult.setData(null);
////        }
////        return serviceResult;
//        return null;
//    }

//    @Override
//    public List<DiscountAdminDTO> getAll() {
//        List<DiscountAdminDTO> discountAdminDTOList = discountAdminCustomRepository.getAll();
//        for (int i = 0; i < discountAdminDTOList.size(); i++) {
//            if (discountAdminDTOList.get(i).getUsed_count() > 0) {
//                DiscountAdminDTO discountAdminDTO = discountAdminDTOList.get(i);
//                discountAdminDTO.setIsUpdate(1);
//            }
//        }
//        return discountAdminDTOList;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllKichHoat() {
//        List<DiscountAdminDTO> discountAdminDTOList = discountAdminCustomRepository.getAllKichHoat();
//        return discountAdminDTOList;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllKhongKichHoat() {
//        List<DiscountAdminDTO> discountAdminDTOList = discountAdminCustomRepository.getAllKhongKichHoat();
//        return discountAdminDTOList;
//    }

//    @Override
//    public ServiceResult<DiscountAdminDTO> KichHoat(Long idDiscount) {
//        ServiceResult<DiscountAdminDTO> serviceResult = new ServiceResult<>();
//        Optional<Discount> optionalDiscount = discountAdminRepository.findById(idDiscount);
//
//        if (optionalDiscount.isPresent()) {
//            boolean flag = false;
//
//            List<ProductAdminDTO> lstProduct = discountAdminCustomRepository.getAllProductKickHoat();
//            DiscountAdminDTO discountAdminDTO = getDetailDiscount(optionalDiscount.get().getId());
//
//            for (int i = 0; i < lstProduct.size(); i++) {
//                for (int j = 0; j < discountAdminDTO.getProductDTOList().size(); j++) {
//                    if (lstProduct.get(i).getId() == discountAdminDTO.getProductDTOList().get(j).getId()) {
//                        flag = true;
//                        break;
//                    }
//                }
//            }
//            if (!flag) {
//                Discount discount = optionalDiscount.get();
//                discount.setIdel(discount.getIdel() == 1 ? 0 : 1);
//                discount = discountAdminRepository.save(discount);
//                DiscountAdminDTO voucherAdminDTO = discountAdminMapper.toDto(discount);
//                serviceResult.setData(voucherAdminDTO);
//                serviceResult.setStatus(HttpStatus.OK);
//                serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu
//            } else {
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
//        return serviceResult;
//    }

//    @Override
//    public ServiceResult<DiscountAdminDTO> setIdel(Long idDiscount) {
//        ServiceResult<DiscountAdminDTO> serviceResult = new ServiceResult<>();
//        Optional<Discount> optionalDiscount = discountAdminRepository.findById(idDiscount);
//
//        if (optionalDiscount.isPresent()) {
//            Discount discount = optionalDiscount.get();
//            discount.setIdel(0);
//            discount = discountAdminRepository.save(discount);
//            DiscountAdminDTO voucherAdminDTO = discountAdminMapper.toDto(discount);
//            serviceResult.setData(voucherAdminDTO);
//            serviceResult.setStatus(HttpStatus.OK);
//            serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu
//
//        } else {
//            serviceResult.setMessage("Không tìm thấy khuyến mãi");
//            serviceResult.setStatus(HttpStatus.NOT_FOUND);
//            serviceResult.setData(null);
//        }
//        return serviceResult;
//    }

//
//    @Override
//    public List<DiscountAdminDTO> getAll() {
//        List<DiscountAdminDTO> list = discountAdminCustomRepository.getAll();
//        for (int i =0; i< list.size(); i++ ) {
//            if (list.get(i).getUsed_count() > 0) {
//                DiscountAdminDTO discountAdminDTO = list.get(i);
//                discountAdminDTO.setIsUpdate(1);
//            }
//        }
//        return list;
//    }
//
//
//    @Override
//    public List<DiscountAdminDTO> getAllKichHoat() {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllKichHoat();
//        return list;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllKhongKichHoat() {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllKhongKichHoat();
//        return list;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllByCodeOrName(String search) {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByCodeOrName(search);
//        return list;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllByCategory(String category) {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByCategory(category);
//        return list;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode) {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByProductNameOrCode(productNameOrCode);
//        return list;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllByBrand(String brand) {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByBrand(brand);
//        return list;
//    }
//
//    @Override
//    public ServiceResult<Void> deleteDiscount(Long discountId) {
//        ServiceResult<Void> serviceResult = new ServiceResult<>();
//        Optional<Discount> discount = discountAdminRepository.findById(discountId);
//
//        if (discount.isPresent()) {
//            Discount discount1 = discount.get();
//            discount1.setDelete(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
//            discountAdminRepository.save(discount1); // Lưu lại thay đổi vào cơ sở dữ liệu
//
//            serviceResult.setMessage("Xóa thành công!");
//            serviceResult.setStatus(HttpStatus.OK);
//        } else {
//            serviceResult.setMessage("Không tìm thấy khuyến mãi");
//            serviceResult.setStatus(HttpStatus.NOT_FOUND);
//        }
//        return serviceResult;
//    }
//
//    @Override
//    public List<DiscountAdminDTO> getAllByDateRange(String fromDate, String toDate) {
//        List<DiscountAdminDTO> list= discountAdminCustomRepository.getAllByDateRange(fromDate,toDate);
//        return list;
//    }
//
//
//    @Override
//    public ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO discountDetailAdminDTO) {
//        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();
//        // Chuyển DTO sang Entity cho DiscountAdmin
//        Discount discountAdminEntity = discountAdminMapper.toEntity(discountDetailAdminDTO.getDiscountAdminDTO());
//        discountAdminEntity.setCode("GG" + Instant.now().getEpochSecond());
//        Date nowDate = new Date();
//        discountAdminEntity.setCreateDate(LocalDate.now());
//        discountAdminEntity.setStatus(0);
//        discountAdminEntity.setIdel(0);
//        discountAdminEntity.setDelete(0);
//
//        discountAdminEntity.setStartDate(discountDetailAdminDTO.getDiscountAdminDTO().getStartDate());
//        discountAdminEntity.setEndDate(discountDetailAdminDTO.getDiscountAdminDTO().getEndDate());
//        discountAdminEntity = discountAdminRepository.save(discountAdminEntity);
//        for (int i = 0; i < discountDetailAdminDTO.getProductDTOList().size(); i++) {
//            DiscountDetail discountDetail = new DiscountDetail();
//            discountDetail.setIdDiscount(discountAdminEntity.getId());
//            discountDetail.setIdProduct(discountDetailAdminDTO.getProductDTOList().get(i).getId());
//            discountDetail.setDiscountType(discountDetailAdminDTO.getDiscountType());
//            discountDetail.setReducedValue(discountDetailAdminDTO.getReducedValue());
//            discountDetail.setStatus(0);
//            discountDetail.setMaxReduced(discountDetailAdminDTO.getMaxReduced() != null ? discountDetailAdminDTO.getMaxReduced() : null);
//            discountDetailRepository.save(discountDetail);
//        }
//
//        serviceResult.setData(discountDetailAdminDTO);
//        serviceResult.setMessage("Thêm thành công!");
//        serviceResult.setStatus(HttpStatus.OK);
//
//        return serviceResult;
//    }
//
//    @Override
//    public ServiceResult<DiscountDetailAdminDTO> updateDiscount(DiscountDetailAdminDTO discountDetailAdminDTO) {
//        ServiceResult<DiscountDetailAdminDTO> serviceResult = new ServiceResult<>();
//        Optional<Discount> discountAdminOptional = discountAdminRepository.findById(discountDetailAdminDTO.getDiscountAdminDTO().getId());
//
//        if (discountAdminOptional.isPresent()) {
//            Discount discountAdminEntity = discountAdminOptional.get();
//            discountAdminEntity.setStartDate(discountDetailAdminDTO.getDiscountAdminDTO().getStartDate());
//            discountAdminEntity.setEndDate(discountDetailAdminDTO.getDiscountAdminDTO().getEndDate());
//            discountAdminEntity.setDescription(discountDetailAdminDTO.getDiscountAdminDTO().getDescription());
//
//            discountAdminEntity = discountAdminRepository.save(discountAdminEntity);
//
//
//            discountAdminCustomRepository.deleteAllDiscountDetailByDiscount(discountDetailAdminDTO.getIdDiscount());
//
//            for (int i = 0; i < discountDetailAdminDTO.getProductDTOList().size(); i++) {
//                DiscountDetail discountDetail = new DiscountDetail();
//                discountDetail.setIdDiscount(discountAdminEntity.getId());
//                discountDetail.setIdProduct(discountDetailAdminDTO.getProductDTOList().get(i).getId());
//                discountDetail.setDiscountType(discountDetailAdminDTO.getDiscountType());
//                discountDetail.setReducedValue(discountDetailAdminDTO.getReducedValue());
//                discountDetail.setMaxReduced(discountDetailAdminDTO.getMaxReduced() != null ? discountDetailAdminDTO.getMaxReduced() : null);
//                discountDetailRepository.save(discountDetail);
//            }
//        serviceResult.setData(discountDetailAdminDTO);
//        serviceResult.setMessage("Cập nhật thành công!");
//        serviceResult.setStatus(HttpStatus.OK);
//        } else {
//            serviceResult.setMessage("Không tìm thấy khuyến mãi");
//            serviceResult.setStatus(HttpStatus.NOT_FOUND);
//        }
//
//        return serviceResult;
//    }
//
//
//

//
//    private DiscountAdminDTO getDetailKickHoat(Long id){
//        Discount discount = discountAdminRepository.findById(id).get();
//        if (discount == null) {
//            return null;
//        }
//        DiscountAdminDTO discountAdminDTO = discountAdminMapper.toDto(discount);
//        List<DiscountDetail> discountDetailList = discountDetailRepository.findAllByDiscount(discount.getId());
//
//        List<ProductAdminDTO> lstPruct = new ArrayList<>();
//        if (discountDetailList.size() > 0) {
//            for (int i = 0; i < discountDetailList.size(); i++) {
//                ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(discountDetailList.get(i).getIdProduct()).orElse(null));
//                lstPruct.add(productAdminDTO);
//
//            }
//            discountAdminDTO.setReducedValue(discountDetailList.get(0).getReducedValue());
//            discountAdminDTO.setDiscountType(discountDetailList.get(0).getDiscountType());
//            discountAdminDTO.setMaxReduced(discountDetailList.get(0).getMaxReduced());
//            discountAdminDTO.setProductDTOList(lstPruct);
//        }
//        return discountAdminDTO;
//    }

//
//    @Override
//    public DiscountAdminDTO getDetailDiscount(Long id) {
//        Discount discount = discountAdminRepository.findById(id).get();
//        if (discount == null) {
//            return null;
//        }
//        DiscountAdminDTO discountAdminDTO = discountAdminMapper.toDto(discount);
//        List<DiscountDetail> discountDetailList = discountDetailRepository.findAllByDiscount(discount.getId());
//
//        List<ProductAdminDTO> lstPruct = new ArrayList<>();
//        if (discountDetailList.size() > 0) {
//            for (int i = 0; i < discountDetailList.size(); i++) {
//                ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(discountDetailList.get(i).getIdProduct()).orElse(null));
//                lstPruct.add(productAdminDTO);
//
//            }
//            discountAdminDTO.setReducedValue(discountDetailList.get(0).getReducedValue());
//            discountAdminDTO.setDiscountType(discountDetailList.get(0).getDiscountType());
//            discountAdminDTO.setMaxReduced(discountDetailList.get(0).getMaxReduced());
//            discountAdminDTO.setProductDTOList(lstPruct);
//        }
//        return discountAdminDTO;
//    }
//
//
//    public List<ProductAdminDTO> getAllProduct() {
//        List<ProductAdminDTO> list = discountAdminCustomRepository.getAllProduct();
//        return list;
//    }
//
//    @Override
//    public byte[] exportExcelDiscount() throws IOException {
//        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
//        List<DiscountDetailAdminDTO> discountAdminDTOS = discountAdminCustomRepository.discountExport();
//        sheetConfigList = getDataForExcel("Danh Sách Giảm Giá", discountAdminDTOS, sheetConfigList, AppConstant.EXPORT_DATA);
//        try {
//            String title = "DANH SÁCH GIẢM GIÁ";
//            return fileExportUtil.exportXLSX(false, sheetConfigList, title);
//        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ioE) {
//            throw new IOException("Lỗi Export" + ioE.getMessage(), ioE);
//        }
//    }
//
//    private List<SheetConfigDTO> getDataForExcel(String sheetName,
//                                                 List<DiscountDetailAdminDTO> listDataSheet,
//                                                 List<SheetConfigDTO> sheetConfigList,
//                                                 Long exportType) {
//        SheetConfigDTO sheetConfig = new SheetConfigDTO();
//        String[] headerArr = null;
//        if (AppConstant.EXPORT_DATA.equals(exportType)) {
//            headerArr =
//                    new String[]{
//                            "STT",
//                            "Mã Giảm Giá",
//                            "Tên Giảm Giá",
//                            "Ngày Tạo",
//                            "Người Tạo",
//                            "Ngày Bắt Đầu",
//                            "Ngày Kết Thúc",
//                            "Nội Dung",
//                            "Giá Trị Giảm",
//                            "Loại Giảm Giá",
//                            "Giá trị Giảm Tối Đa",
//                            "Tên Sản Phẩm",
//                    };
//        }
//        sheetConfig.setSheetName(sheetName);
//        sheetConfig.setHeaders(headerArr);
//        int recordNo = 1;
//        List<CellConfigDTO> cellConfigCustomList = new ArrayList<>();
//        for (DiscountDetailAdminDTO item : listDataSheet) {
//            item.setRecordNo(recordNo++);
//        }
//        List<CellConfigDTO> cellConfigList = new ArrayList<>();
//        sheetConfig.setList(listDataSheet);
//        cellConfigList.add(new CellConfigDTO("recordNo", AppConstant.ALIGN_LEFT, AppConstant.NO));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.code", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.name", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.createDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.createName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.startDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.endDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountAdminDTO.description", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("reducedValue", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("discountTypeStr", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("maxReduced", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("productDTO.name", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        if (AppConstant.EXPORT_DATA.equals(exportType) || AppConstant.EXPORT_ERRORS.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("messageStr", AppConstant.ALIGN_LEFT, AppConstant.ERRORS));
//        }
//        sheetConfig.setHasIndex(false);
//        sheetConfig.setHasBorder(true);
//        sheetConfig.setExportType(exportType.intValue());
//        sheetConfig.setCellConfigList(cellConfigList);
//        sheetConfig.setCellCustomList(cellConfigCustomList);
//        sheetConfigList.add(sheetConfig);
//        return sheetConfigList;
//    }


}
