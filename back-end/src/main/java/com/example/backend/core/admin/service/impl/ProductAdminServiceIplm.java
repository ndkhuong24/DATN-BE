package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.ProductAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductAdminServiceIplm implements ProductAdminService {
    @Autowired
    private ProductAdminRepository productAdminRepository;

    @Autowired
    private ProductAdminMapper productAdminMapper;

    @Autowired
    private SoleAdminMapper soleAdminMapper;

    @Autowired
    private SoleAdminRepository soleAdminRepository;

    @Autowired
    private MaterialAdminRepository materialAdminRepository;

    @Autowired
    private MaterialAdminMapper materialAdminMapper;

    @Autowired
    private CategoryAdminRepository categoryAdminRepository;

    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    @Autowired
    private BrandAdminRepository brandAdminRepository;

    @Autowired
    private BrandAdminMapper brandAdminMapper;

    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;

    @Autowired
    private ProductDetailAdminServiceIplm productDetailAdminServiceIplm;

    @Override
    public List<ProductAdminDTO> getAll() {
        List<Product> productListt = productAdminRepository.findAll();

        List<Product> productList = productListt.stream()
                .sorted(Comparator.comparing(Product::getUpdateDate).reversed())
                .collect(Collectors.toList());

        List<ProductAdminDTO> productAdminDTOList = new ArrayList<>();

        for (Product product : productList) {
            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(product);

            String imageURL = "http://localhost:8081/view/anh/" + product.getId();
            productAdminDTO.setImageURL(imageURL);

            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(soleAdminRepository.findById(product.getIdSole()).orElse(null));
            productAdminDTO.setSoleAdminDTO(soleAdminDTO);

            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(materialAdminRepository.findById(product.getIdMaterial()).orElse(null));
            productAdminDTO.setMaterialAdminDTO(materialAdminDTO);

            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brandAdminRepository.findById(product.getIdBrand()).orElse(null));
            productAdminDTO.setBrandAdminDTO(brandAdminDTO);

            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(categoryAdminRepository.findById(product.getIdCategory()).orElse(null));
            productAdminDTO.setCategoryAdminDTO(categoryAdminDTO);

            ServiceResult<List<ProductDetailAdminDTO>> productDetails = productDetailAdminServiceIplm.getProductDetailsByProductId(product.getId());

            if (productDetails != null && !productDetails.getData().isEmpty()) {
                productAdminDTO.setProductDetailAdminDTOList(productDetails.getData());
            }

            productAdminDTOList.add(productAdminDTO);
        }
        return productAdminDTOList;
    }

    @Override
    public ServiceResult<ProductAdminDTO> getById(Long id) {
        ServiceResult<ProductAdminDTO> result = new ServiceResult<>();

        Optional<Product> productOptional = productAdminRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(product);

            ServiceResult<List<ProductDetailAdminDTO>> serviceResult = productDetailAdminServiceIplm.getProductDetailsByProductId(id);

            List<ProductDetailAdminDTO> productDetailAdminDTOList = serviceResult.getData();

            productAdminDTO.setProductDetailAdminDTOList(productDetailAdminDTOList);

            String imageURL = "http://localhost:8081/view/anh/" + id;
            productAdminDTO.setImageURL(imageURL);

            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(soleAdminRepository.findById(product.getIdSole()).orElse(null));
            productAdminDTO.setSoleAdminDTO(soleAdminDTO);

            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(materialAdminRepository.findById(product.getIdMaterial()).orElse(null));
            productAdminDTO.setMaterialAdminDTO(materialAdminDTO);

            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brandAdminRepository.findById(product.getIdBrand()).orElse(null));
            productAdminDTO.setBrandAdminDTO(brandAdminDTO);

            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(categoryAdminRepository.findById(product.getIdCategory()).orElse(null));
            productAdminDTO.setCategoryAdminDTO(categoryAdminDTO);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Lấy thông tin sản phẩm thành công");
            result.setData(productAdminDTO);
            result.setSuccess(true);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy sản phẩm");
        }

        return result;
    }

    @Override
    public ServiceResult add(ProductAdminDTO productAdminDTO) {
        ServiceResult<ProductAdminDTO> result = new ServiceResult<>();

        Product product = productAdminMapper.toEntity(productAdminDTO);

        product.setCode("PR" + Instant.now().getEpochSecond());

        Optional<Brand> brand = brandAdminRepository.findById(product.getIdBrand());
        Optional<Material> material = materialAdminRepository.findById(product.getIdMaterial());
        Optional<Sole> sole = soleAdminRepository.findById(product.getIdSole());
        Optional<Category> category = categoryAdminRepository.findById(product.getIdCategory());

        BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brand.orElse(null));
        MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(material.orElse(null));
        SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(sole.orElse(null));
        CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(category.orElse(null));

        product.setIdBrand(brandAdminDTO.getId());
        product.setIdMaterial(materialAdminDTO.getId());
        product.setIdSole(soleAdminDTO.getId());
        product.setIdCategory(categoryAdminDTO.getId());

        productAdminDTO.setSoleAdminDTO(soleAdminDTO);
        productAdminDTO.setBrandAdminDTO(brandAdminDTO);
        productAdminDTO.setCategoryAdminDTO(categoryAdminDTO);
        productAdminDTO.setMaterialAdminDTO(materialAdminDTO);

        product.setCreateDate(LocalDateTime.now());
        product.setUpdateDate(LocalDateTime.now());

        product = this.productAdminRepository.save(product);

        if (product != null) {
            for (int i = 0; i < productAdminDTO.getProductDetailAdminDTOList().size(); i++) {
                ProductDetail productDetail = new ProductDetail();

                productDetail.setIdProduct(product.getId());

                productDetail.setIdColor(productAdminDTO.getProductDetailAdminDTOList().get(i).getColorDTO().getId());

                productDetail.setIdSize(productAdminDTO.getProductDetailAdminDTOList().get(i).getSizeDTO().getId());

                productDetail.setPrice(productAdminDTO.getProductDetailAdminDTOList().get(i).getPrice());

                productDetail.setShoeCollar(productAdminDTO.getProductDetailAdminDTOList().get(i).getShoeCollar());

                productDetail.setQuantity(productAdminDTO.getProductDetailAdminDTOList().get(i).getQuantity());

                this.productDetailAdminRepository.save(productDetail);
            }
        }

        ProductAdminDTO productAdminDTO1 = new ProductAdminDTO();
        productAdminDTO1.setId(product.getId());
        productAdminDTO1.setCode(product.getCode());
        productAdminDTO1.setName(product.getName());
        productAdminDTO1.setCreateDate(product.getCreateDate());
        productAdminDTO1.setUpdateDate(product.getUpdateDate());

        productAdminDTO1.setIdBrand(product.getIdBrand());
        productAdminDTO1.setIdCategory(product.getIdCategory());
        productAdminDTO1.setIdMaterial(product.getIdMaterial());
        productAdminDTO1.setIdSole(product.getIdSole());
        productAdminDTO1.setDescription(product.getDescription());
        productAdminDTO1.setStatus(product.getStatus());

        String imageURL = "http://localhost:8081/view/anh/" + product.getId();
        productAdminDTO1.setImageURL(imageURL);

        List<ProductDetail> productDetailList = productDetailAdminRepository.findByIdProduct(product.getId());
        productAdminDTO1.setProductDetailList(productDetailList);

        productAdminDTO1.setBrandAdminDTO(brandAdminDTO);
        productAdminDTO1.setCategoryAdminDTO(categoryAdminDTO);
        productAdminDTO1.setSoleAdminDTO(soleAdminDTO);
        productAdminDTO1.setMaterialAdminDTO(materialAdminDTO);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(productAdminDTO1);
//        result.setData(productAdminMapper.toDto(product));

        return result;
    }

    @Override
    public void activateProduct(Long productId) {
        Product product = productAdminRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
        product.setStatus(0);
        productAdminRepository.save(product);
    }

    @Override
    public void deactivateProduct(Long productId) {
        Product product = productAdminRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
        product.setStatus(1);
        productAdminRepository.save(product);
    }

    @Override
    public ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO, Long id) {
        ServiceResult<ProductAdminDTO> result = new ServiceResult<>();

        Optional<Product> productOptional = this.productAdminRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            product.setId(id);
            product.setName(productAdminDTO.getName());
            product.setUpdateDate(LocalDateTime.now());
            product.setIdBrand(productAdminDTO.getIdBrand());
            product.setIdSole(productAdminDTO.getIdSole());
            product.setIdMaterial(productAdminDTO.getIdMaterial());
            product.setIdCategory(productAdminDTO.getIdCategory());
            product.setDescription(productAdminDTO.getDescription());
            product.setStatus(productAdminDTO.getStatus());

            product = this.productAdminRepository.save(product);

            List<ProductDetail> existingProductDetails = this.productDetailAdminRepository.findByIdProduct(id);
            Map<ProductDetailKey, ProductDetail> existingProductDetailMap = existingProductDetails.stream()
                    .collect(Collectors.toMap(
                            pd -> new ProductDetailKey(pd.getIdProduct(), pd.getIdColor(), pd.getIdSize()),
                            Function.identity()
                    ));

            Set<ProductDetailKey> newDetailKeys = productAdminDTO.getProductDetailAdminDTOList().stream()
                    .map(detailDTO -> new ProductDetailKey(detailDTO.getIdProduct(), detailDTO.getColorDTO().getId(), detailDTO.getSizeDTO().getId()))
                    .collect(Collectors.toSet());

            // Cập nhật hoặc thêm các chi tiết sản phẩm mới
            for (ProductDetailAdminDTO detailDTO : productAdminDTO.getProductDetailAdminDTOList()) {
                ProductDetailKey key = new ProductDetailKey(product.getId(), detailDTO.getColorDTO().getId(), detailDTO.getSizeDTO().getId());
                ProductDetail productDetail;

                if (existingProductDetailMap.containsKey(key)) {
                    productDetail = existingProductDetailMap.get(key);
                    if (productDetail.getQuantity() != detailDTO.getQuantity()) {
                        productDetail.setQuantity(detailDTO.getQuantity());
                    }
                    if (productDetail.getShoeCollar() != detailDTO.getShoeCollar()) {
                        productDetail.setShoeCollar(detailDTO.getShoeCollar());
                    }
                    if (productDetail.getPrice() != detailDTO.getPrice()) {
                        productDetail.setPrice(detailDTO.getPrice());
                    }
                } else {
                    productDetail = new ProductDetail();
                    productDetail.setId(detailDTO.getId());
                    productDetail.setIdProduct(product.getId());
                    productDetail.setIdColor(detailDTO.getColorDTO().getId());
                    productDetail.setIdSize(detailDTO.getSizeDTO().getId());
                    productDetail.setPrice(detailDTO.getPrice());
                    productDetail.setShoeCollar(detailDTO.getShoeCollar());
                    productDetail.setQuantity(detailDTO.getQuantity());
                }

                this.productDetailAdminRepository.save(productDetail);
            }

            // Xóa các chi tiết sản phẩm bị loại bỏ
            for (ProductDetail existingDetail : existingProductDetails) {
                ProductDetailKey key = new ProductDetailKey(existingDetail.getIdProduct(), existingDetail.getIdColor(), existingDetail.getIdSize());
                if (!newDetailKeys.contains(key)) {
                    this.productDetailAdminRepository.delete(existingDetail);
                }
            }

            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(productAdminMapper.toDto(product));

        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai");
            result.setData(null);
        }
        return result;
    }

//    @Override
//    public ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO, Long id) {
//        ServiceResult<ProductAdminDTO> result = new ServiceResult<>();
//
//        Optional<Product> productOptional = this.productAdminRepository.findById(id);
//
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//
//            product.setId(id);
//            product.setName(productAdminDTO.getName());
//            product.setUpdateDate(LocalDateTime.now());
//            product.setIdBrand(productAdminDTO.getIdBrand());
//            product.setIdSole(productAdminDTO.getIdSole());
//            product.setIdMaterial(productAdminDTO.getIdMaterial());
//            product.setIdCategory(productAdminDTO.getIdCategory());
//            product.setDescription(productAdminDTO.getDescription());
//            product.setStatus(productAdminDTO.getStatus());
//
//            product = this.productAdminRepository.save(product);
//
//            List<ProductDetail> existingProductDetails = this.productDetailAdminRepository.findByIdProduct(id);
//
//            Map<ProductDetailKey, ProductDetail> existingProductDetailMap = existingProductDetails.stream()
//                    .collect(Collectors.toMap(
//                            pd -> new ProductDetailKey(pd.getIdProduct(), pd.getIdColor(), pd.getIdSize()),
//                            Function.identity()
//                    ));
//
////            Map<Long, ProductDetail> existingProductDetailMap = existingProductDetails.stream()
////                    .collect(Collectors.toMap(ProductDetail::getIdProduct,ProductDetail::getIdColor,ProductDetail::getIdSize ,Function.identity()));
//
////            Set<Long> newDetailIds = productAdminDTO.getProductDetailAdminDTOList().stream()
////                    .map(ProductDetailAdminDTO::getId)
////                    .collect(Collectors.toSet());
//
//            Set<ProductDetailKey> newDetailIds = productAdminDTO.getProductDetailAdminDTOList().stream()
//                    .map(detailDTO -> new ProductDetailKey(detailDTO.getIdProduct(), detailDTO.getColorDTO().getId(), detailDTO.getSizeDTO().getId()))
//                    .collect(Collectors.toSet());
//
//            // Cập nhật hoặc thêm các chi tiết sản phẩm mới
//            for (ProductDetailAdminDTO detailDTO : productAdminDTO.getProductDetailAdminDTOList()) {
//                ProductDetail productDetail;
//                ProductDetailKey key = new ProductDetailKey(detailDTO.getIdProduct(), detailDTO.getIdColor(), detailDTO.getIdSize());
//
//                if (existingProductDetailMap.containsKey(key)) {
//                    productDetail = existingProductDetailMap.get(key);
//                    if (productDetail.getQuantity() != detailDTO.getQuantity()) {
//                        productDetail.setQuantity(detailDTO.getQuantity());
//                    }
//                    if (productDetail.getShoeCollar() != detailDTO.getShoeCollar()) {
//                        productDetail.setShoeCollar(detailDTO.getShoeCollar());
//                    }
//                    if (productDetail.getPrice() != detailDTO.getPrice()) {
//                        productDetail.setPrice(detailDTO.getPrice());
//                    }
//                }
////                if (existingProductDetailMap.containsKey(detailDTO.getIdProduct(), detailDTO.getIdColor(), detailDTO.getIdSize())) {
////                    productDetail = existingProductDetailMap.get(detailDTO.getId());
////                    if (productDetail.getQuantity() != detailDTO.getQuantity()) {
////                        productDetail.setQuantity(detailDTO.getQuantity());
////                    }
////                    if (productDetail.getShoeCollar() != detailDTO.getShoeCollar()) {
////                        productDetail.setShoeCollar(detailDTO.getShoeCollar());
////                    }
////                    if (productDetail.getPrice() != detailDTO.getPrice()) {
////                        productDetail.setPrice(detailDTO.getPrice());
////                    }
////                }
//                else {
//                    productDetail = new ProductDetail();
//                    productDetail.setId(detailDTO.getId());
//                    productDetail.setIdProduct(product.getId());
//                    productDetail.setIdColor(detailDTO.getColorDTO().getId());
//                    productDetail.setIdSize(detailDTO.getSizeDTO().getId());
//                    productDetail.setPrice(detailDTO.getPrice());
//                    productDetail.setShoeCollar(detailDTO.getShoeCollar());
//                    productDetail.setQuantity(detailDTO.getQuantity());
//                }
//
//                this.productDetailAdminRepository.save(productDetail);
//            }
//
//            // Xóa các chi tiết sản phẩm bị loại bỏ
//            for (ProductDetail existingDetail : existingProductDetails) {
//                if (!newDetailIds.contains(existingDetail.getId())) {
//                    this.productDetailAdminRepository.delete(existingDetail);
//                }
//            }
//
//            result.setStatus(HttpStatus.OK);
//            result.setMessage("Sua thanh cong");
//            result.setData(productAdminMapper.toDto(product));
//
//        } else {
//            result.setStatus(HttpStatus.BAD_REQUEST);
//            result.setMessage("Id khong ton tai");
//            result.setData(null);
//        }
//        return result;
//    }

//    @Override
//    public ServiceResult<ProductAdminDTO> add(ProductAdminDTO productAdminDTO) {
//        Product product = productAdminMapper.toEntity(productAdminDTO);
//        product.setCode("SP" + Instant.now().getEpochSecond());
//        Optional<Brand> brand = brrp.findById(product.getIdBrand());
//        Optional<Material> material = mtrp.findById(product.getIdMaterial());
//        Optional<Sole> sole = slrp.findById(product.getIdSole());
//        Optional<Category> category = ctrp.findById(product.getIdCategory());

//        MaterialAdminDTO materialDTO = materialAdminMapper.toDto(material.orElse(null));
//        SoleAdminDTO soleDTO = soleAdminMapper.toDto(sole.orElse(null));
//        CategoryAdminDTO categoryDTO = categoryAdminMapper.toDto(category.orElse(null));
//        BrandAdminDTO brandDTO = brandAdminMapper.toDto(brand.orElse(null));

//        product.setIdBrand(brandDTO.getId());
//        product.setIdCategory(categoryDTO.getId());
//        product.setIdMaterial(materialDTO.getId());
//        product.setIdSole(soleDTO.getId());

//        productAdminDTO.setSoleAdminDTO(soleDTO);
//        productAdminDTO.setBrandAdminDTO(brandDTO);
//        productAdminDTO.setCategoryAdminDTO(categoryDTO);
//        productAdminDTO.setMaterialAdminDTO(materialDTO);

//        product.setCreateDate(LocalDate.now());
//        product.setUpdateDate(LocalDate.now());

//        product.setPrice(productAdminDTO.getPrice());
//        product = this.prdrp.save(product);

//        if (product != null) {
//            for (int i = 0; i < productAdminDTO.getProductDetailAdminDTOList().size(); i++) {
//                ProductDetail productDetail = new ProductDetail();
//                productDetail.setIdProduct(product.getId());
//                productDetail.setIdColor(productAdminDTO.getProductDetailAdminDTOList().get(i).getColorDTO().getId());
//                productDetail.setIdSize(productAdminDTO.getProductDetailAdminDTOList().get(i).getSizeDTO().getId());
//                productDetail.setShoeCollar(productAdminDTO.getProductDetailAdminDTOList().get(i).getShoeCollar());
//                productDetail.setQuantity(productAdminDTO.getProductDetailAdminDTOList().get(i).getQuantity());
//                this.productDetailAdminRepository.save(productDetail);
//            }
//        }
//        result.setStatus(HttpStatus.OK);
//        result.setMessage("Them thanh cong");
//        result.setData(productAdminMapper.toDto(product));
//        return result;
//    }

//    @Override
//    public ServiceResult<ProductAdminDTO> delete(Long id) {
//        Optional<Product> optional = this.prdrp.findById(id);
//        if (optional.isPresent()) {
//            this.prdrp.deleteById(id);
//            result.setStatus(HttpStatus.OK);
//            result.setMessage("Xoa thanh cong");
//            result.setData(null);
//        }
//        return result;
//    }

    @Override
    public List<ProductAdminDTO> findByNameLikeOrCodeLike(String param) {
        List<ProductAdminDTO> productAdminDTOList = productAdminMapper.toDto(productAdminRepository.findByNameLikeOrCodeLike("%" + param + "%", "%" + param + "%"));

        for (int i = 0; i < productAdminDTOList.size(); i++) {
            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(soleAdminRepository.findById(productAdminDTOList.get(i).getIdSole()).orElse(null));
            productAdminDTOList.get(i).setSoleAdminDTO(soleAdminDTO);

            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(materialAdminRepository.findById(productAdminDTOList.get(i).getIdMaterial()).orElse(null));
            productAdminDTOList.get(i).setMaterialAdminDTO(materialAdminDTO);

            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brandAdminRepository.findById(productAdminDTOList.get(i).getIdBrand()).orElse(null));
            productAdminDTOList.get(i).setBrandAdminDTO(brandAdminDTO);

            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(categoryAdminRepository.findById(productAdminDTOList.get(i).getIdCategory()).orElse(null));
            productAdminDTOList.get(i).setCategoryAdminDTO(categoryAdminDTO);

            String imageURL = "http://localhost:8081/view/anh/" + productAdminDTOList.get(i).getId();
            productAdminDTOList.get(i).setImageURL(imageURL);

//            List<ProductDetailAdminDTO> productDetailAdminDTOList = productDetailAdminMapper.toDto(productDetailAdminRepository.findByIdProduct(productAdminDTOList.get(i).getId()));

            ServiceResult<List<ProductDetailAdminDTO>> productDetails = productDetailAdminServiceIplm.getProductDetailsByProductId(productAdminDTOList.get(i).getId());

            if (productDetails != null && !productDetails.getData().isEmpty()) {
//                productAdminDTO.setProductDetailAdminDTOList(productDetails.getData());
                productAdminDTOList.get(i).setProductDetailAdminDTOList(productDetails.getData());
            }

//            productAdminDTOList.get(i).setProductDetailAdminDTOList(productDetailAdminDTOList);
        }
        return productAdminDTOList;
    }


//    public List<ProductDetailAdminDTO> getProductDetailAdminDTOs(Long productId) {
//        List<ProductDetail> productDetails = productDetailAdminRepository.findByIdProduct(productId);
//        List<ProductDetailAdminDTO> productDetailAdminDTOs = new ArrayList<>();
//
//        for (ProductDetail productDetail : productDetails) {
//            ProductDetailAdminDTO productDetailAdminDTO = new ProductDetailAdminDTO();
//            productDetailAdminDTO.setId(productDetail.getId());
//            productDetailAdminDTO.setQuantity(productDetail.getQuantity());
//            productDetailAdminDTO.setShoeCollar(productDetail.getShoeCollar());
//
//            ColorAdminDTO colorAdminDTO = colorAdminMapper.toDto(colorAdminRepository.findById(productDetail.getIdColor()).orElse(null));
//            productDetailAdminDTO.setColorDTO(colorAdminDTO);
//
//            SizeAdminDTO sizeAdminDTO = sizeAdminMapper.toDto(sizeAdminReposiotry.findById(productDetail.getIdSize()).orElse(null));
//            productDetailAdminDTO.setSizeDTO(sizeAdminDTO);
//
//
//            productDetailAdminDTOs.add(productDetailAdminDTO);
//        }
//
//        return productDetailAdminDTOs;
//    }

//    public List<ImagesAdminDTO> getImagesAdminDTOs(Long productId) {
//        List<Images> images = imageAdminRepository.findByIdProduct(productId);
//        List<ImagesAdminDTO> imagesAdminDTOs = new ArrayList<>();
//
//        for (Images image : images) {
//            ImagesAdminDTO imagesAdminDTO = new ImagesAdminDTO();
//            imagesAdminDTO.setIdProduct(image.getIdProduct());
//            imagesAdminDTO.setId(image.getId());
//            imagesAdminDTO.setCreateDate(image.getCreateDate());
//            imagesAdminDTO.setImageName(image.getImageName());
//            imagesAdminDTOs.add(imagesAdminDTO);
//        }
//
//        return imagesAdminDTOs;
//    }

//    @Override
//    public byte[] exportExcelProduct() throws IOException {
//        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
//        List<ProductAdminDTO> productAdminDTOS = productAdminCustomRepository.getAllProductExport();
//        sheetConfigList = getDataForExcel("Danh Sách Sản Phẩm", productAdminDTOS, sheetConfigList, AppConstant.EXPORT_DATA);
//        try {
//            String title = "DANH SÁCH SẢN PHẨM";
//            return fileExportUtil.exportXLSX(false, sheetConfigList, title);
//        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ioE) {
//            throw new IOException("Lỗi Export" + ioE.getMessage(), ioE);
//        }
//    }

//    @Override
//    public byte[] exportExcelTemplateProduct() throws IOException {
//        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
//        List<ProductAdminDTO> productAdminDTOS = new ArrayList<>();
//        sheetConfigList = getDataForExcel("Danh Sách Sản Phẩm", productAdminDTOS, sheetConfigList, AppConstant.EXPORT_TEMPLATE);
//        try {
//            return fileExportUtil.exportXLSXTemplate(true, sheetConfigList, null);
//        } catch (IOException | ReflectiveOperationException ioE) {
//            throw new IOException("Lỗi Export Template" + ioE.getMessage(), ioE);
//        }
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public ServiceResult<ResponseImportDTO> importFileProduct(MultipartFile fileUploads, Long typeImport) throws IOException, ParseException {
//        boolean check = FileImportUtil.checkValidFileFormat(fileUploads.getOriginalFilename());
//        if (!check) {
//            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không đúng định dạng", null);
//        }
//        if (!FileImportUtil.isNotNullOrEmpty(fileUploads))
//            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không tồn tại", null);
//        //check extension file
//        String extention = FilenameUtils.getExtension(fileUploads.getOriginalFilename());
//        if (!AppConstant.EXTENSION_XLSX.equalsIgnoreCase(extention) && !AppConstant.EXTENSION_XLS.equalsIgnoreCase(extention)
//        ) return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không đúng định dạng", null);
//        List<List<String>> records;
//        try {
//            records = FileImportUtil.excelReader(fileUploads);
//        } catch (IllegalStateException | IOException e) {
//            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "Đọc file bị lỗi", null);
//        }
//        if (records.size() <= 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không có dữ liệu", null);
//        records.remove(0);
//        List<Product> dataSuccess = new ArrayList<>();
//        List<ProductAdminDTO> dataErrors = new ArrayList<>();
//        List<Brand> brandList = brrp.findAll();
//        List<Category> categoryList = categoryAdminRepository.findAll();
//        List<Material> materialList = mtrp.findAll();
//        List<Sole> soleList = slrp.findAll();
//        int countSuccess = 0;
//        int countError = 0;
//        int total = 0;
//        for (List<String> myRecord : records) {
//            if (myRecord.size() != 14)
//                return new ServiceResult<>(HttpStatus.BAD_REQUEST, "File không đúng định dạng file mẫu", null);
//            ProductAdminDTO dto = processRecord(myRecord, brandList, categoryList, materialList, soleList, typeImport);
//            if (!dto.getMessageErr().isEmpty()) {
//                countError++;
//                dataErrors.add(dto);
//            } else {
//                Product product = new Product(dto);
//                dataSuccess.add(product);
//                product = prdrp.save(product);
//                if (AppConstant.IMPORT_UPDATE.equals(typeImport)) {
//                    imageAdminRepository.deleteByIdProduct(product.getId());
//                }
//                for (String str : dto.getImageNameImport().split(",")) {
//                    Images images = new Images();
//                    images.setIdProduct(product.getId());
//                    images.setCreateDate(LocalDate.now());
//                    images.setImageName(str.trim());
//                    images = imageAdminRepository.save(images);
//                }
//                countSuccess++;
//            }
//            total++;
//        }
//        ResponseImportDTO responseImportDTO = new ResponseImportDTO(countError, countSuccess, total);
//        responseImportDTO.setListErrors(dataErrors);
//        responseImportDTO.setListSuccess(dataSuccess);
//        return new ServiceResult<>(HttpStatus.OK, " Import thành công", responseImportDTO);
//    }

//    @Override
//    public byte[] exportExcelProductErrors(List<ProductAdminDTO> listDataErrors) throws IOException {
//        List<SheetConfigDTO> sheetConfigList = getDataForExcel("DANH SÁCH SẢN PHẨM", listDataErrors, new ArrayList<>(), AppConstant.EXPORT_ERRORS);
//        try {
//            return fileExportUtil.exportXLSX(true, sheetConfigList, null);
//        } catch (IOException | ReflectiveOperationException ioE) {
//            throw new IOException("Lỗi Export Error" + ioE.getMessage(), ioE);
//        }
//    }

//    private List<SheetConfigDTO> getDataForExcel(String sheetName,
//                                                 List<ProductAdminDTO> listDataSheet,
//                                                 List<SheetConfigDTO> sheetConfigList,
//                                                 Long exportType) {
//        SheetConfigDTO sheetConfig = new SheetConfigDTO();
//        String[] headerArr = null;
//        if (AppConstant.EXPORT_DATA.equals(exportType)) {
//            headerArr =
//                    new String[]{
//                            "STT",
//                            "Mã Sản Phẩm",
//                            "Tên Sản Phẩm",
//                            "Ngày Tạo",
//                            "Hãng",
//                            "Danh Mục",
//                            "Chất Liệu",
//                            "Gía sản phẩm",
//                            "Mô Tả",
//                            "Trạng Thái",
//                            "Tổng số lượng",
//                            "Danh sách kích cỡ",
//                            "Danh sách màu sắc"
//                    };
//        } else if (AppConstant.EXPORT_ERRORS.equals(exportType)) {
//            headerArr =
//                    new String[]{
//                            "STT",
//                            "Mã Sản Phẩm (*) \n (maxlength = 250 kí tự)",
//                            "Tên Sản Phẩm (*) \n (maxlength = 250 kí tự)",
//                            "Hãng (*) \n",
//                            "Danh Mục (*) \n",
//                            "Chất Liệu (*) \n",
//                            "Đế giày (*) \n",
//                            "Gía sản phẩm (*) \n (Gía phải là số)",
//                            "Mô Tả \n",
//                            "Ảnh sản phẩm (*) \n (Link ảnh và cách nhau bởi dấu phẩy)",
//                            "Danh sách kích cỡ (*) \n (Nếu có nhiều kích cỡ thì cách nhau bởi dấu phẩy \n và phải nhập là số)",
//                            "Danh sách màu sắc (*) \n (Nếu có nhiều màu sắc thì cách nhau bởi dấu phẩy \n và phải nhập các mã màu)",
//                            "Số lượng (*) \n (Số lượng là số)",
//                            "Cổ giày (*)",
//                            "Mô tả lỗi"
//                    };
//        } else {
//            headerArr =
//                    new String[]{
//                            "STT",
//                            "Mã Sản Phẩm (*) \n (maxlength = 250 kí tự)",
//                            "Tên Sản Phẩm (*) \n (maxlength = 250 kí tự)",
//                            "Hãng (*) \n",
//                            "Danh Mục (*) \n",
//                            "Chất Liệu (*) \n",
//                            "Đế giày (*) \n",
//                            "Gía sản phẩm (*) \n (Gía phải là số)",
//                            "Mô Tả \n",
//                            "Ảnh sản phẩm (*) \n (Link ảnh và cách nhau bởi dấu phẩy)",
//                            "Danh sách kích cỡ (*) \n (Nếu có nhiều kích cỡ thì cách nhau bởi dấu phẩy \n và phải nhập là số)",
//                            "Danh sách màu sắc (*) \n (Nếu có nhiều màu sắc thì cách nhau bởi dấu phẩy \n và phải nhập các mã màu)",
//                            "Số lượng (*) \n (Số lượng là số)",
//                            "Cổ giày (*)"
//                    };
//        }
//        sheetConfig.setSheetName(sheetName);
//        sheetConfig.setHeaders(headerArr);
//        int recordNo = 1;
//        List<CellConfigDTO> cellConfigCustomList = new ArrayList<>();
//        if (!AppConstant.EXPORT_DATA.equals(exportType)) {
//            List<String> listBrand = brandAdminService.getAllBrandExport();
//            List<String> lstCategory = categoryAdminService.getAllListExport();
//            List<String> lstMaterial = materialAdminService.getAllListExport();
//            List<String> lstSole = soleAdminService.getAllListExport();
//            List<String> lstShoeCollar = new ArrayList<>();
//            lstShoeCollar.add("LowCollar");
//            lstShoeCollar.add("HighCollar");
//            cellConfigCustomList.add(
//                    new CellConfigDTO("brandName", AppConstant.ALIGN_LEFT, listBrand.toArray(new String[0]), 1, 99, 3, 3)
//            );
//            cellConfigCustomList.add(
//                    new CellConfigDTO("categoryName", AppConstant.ALIGN_LEFT, lstCategory.toArray(new String[0]), 1, 99, 4, 4)
//            );
//            cellConfigCustomList.add(
//                    new CellConfigDTO("materialName", AppConstant.ALIGN_LEFT, lstMaterial.toArray(new String[0]), 1, 99, 5, 5)
//            );
//            cellConfigCustomList.add(
//                    new CellConfigDTO("soleImport", AppConstant.ALIGN_LEFT, lstSole.toArray(new String[0]), 1, 99, 6, 6)
//            );
//            cellConfigCustomList.add(
//                    new CellConfigDTO("shoeCollarExport", AppConstant.ALIGN_LEFT, lstShoeCollar.toArray(new String[0]), 1, 99, 13, 13)
//            );
//            if (AppConstant.EXPORT_TEMPLATE.equals(exportType)) {
//                for (int i = 1; i < 4; i++) {
//                    ProductAdminDTO data = new ProductAdminDTO();
//                    data.setRecordNo(i);
//                    listDataSheet.add(data);
//                }
//            }
//            if (AppConstant.EXPORT_ERRORS.equals(exportType)) {
//                for (ProductAdminDTO item : listDataSheet) {
//                    item.setRecordNo(recordNo++);
//                    item.setMessageStr(String.join(AppConstant.NEXT_LINE, item.getMessageErr()));
//                }
//            }
//        } else {
//            for (ProductAdminDTO item : listDataSheet) {
//                item.setRecordNo(recordNo++);
//            }
//        }
//        List<CellConfigDTO> cellConfigList = new ArrayList<>();
//        sheetConfig.setList(listDataSheet);
//        cellConfigList.add(new CellConfigDTO("recordNo", AppConstant.ALIGN_LEFT, AppConstant.NO));
//        cellConfigList.add(new CellConfigDTO("code", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("name", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        if (AppConstant.EXPORT_DATA.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("createDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        }
//        cellConfigList.add(new CellConfigDTO("brandName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("categoryName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("materialName", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//
//        if (AppConstant.EXPORT_ERRORS.equals(exportType) || AppConstant.EXPORT_TEMPLATE.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("soleImport", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        }
//        cellConfigList.add(new CellConfigDTO("priceExport", AppConstant.ALIGN_LEFT, AppConstant.DOUBLE));
//        cellConfigList.add(new CellConfigDTO("description", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        if (AppConstant.EXPORT_DATA.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("status", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
//        }
//        if (AppConstant.EXPORT_ERRORS.equals(exportType) || AppConstant.EXPORT_TEMPLATE.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("imagesExportErrors", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        }
//        if (AppConstant.EXPORT_DATA.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("totalQuantity", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
//        }
//        cellConfigList.add(new CellConfigDTO("sizeExport", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("colorExport", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        if (!AppConstant.EXPORT_DATA.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("quantityExport", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
//            cellConfigList.add(new CellConfigDTO("shoeCollarExport", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        }
//
//        if (AppConstant.EXPORT_ERRORS.equals(exportType)) {
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

//    private ProductAdminDTO processRecord(
//            List<String> myRecord,
//            List<Brand> brandList,
//            List<Category> categoryList,
//            List<Material> materialList,
//            List<Sole> soleList,
//            Long typeImport
//    ) throws ParseException {
//        ProductAdminDTO productAdminDTO = new ProductAdminDTO();
//        List<String> messErr = new ArrayList<>();
//        List<String> fieldErr = new ArrayList<>();
//        String regex = "^[0-9]+$";
//        String regexLink = "^(http://|https://).+";
//        String regexSize = "\\d{2}";
//        String regexQuantity = "^[0-9]+$";
//        int col = 1;
//
//
//        String code = myRecord.get(col++);
//        productAdminDTO.setCode(code.trim());
//        Product product = prdrp.findByCode(code);
//        if (StringUtils.isBlank(code.trim())) {
//            messErr.add("Mã không được để trống");
//            fieldErr.add("code");
//        } else if (code.length() > 250 || code.length() < 0) {
//            messErr.add("Mã phải <= 250 kí tự");
//            fieldErr.add("code");
//        } else if (AppConstant.IMPORT_UPDATE.equals(typeImport)) {
//            if (null == product) {
//                messErr.add("Mã sản phẩm không tồn tại");
//                fieldErr.add("code");
//            } else {
//                productAdminDTO.setId(product.getId());
//                productAdminDTO.setCreateDate(product.getCreateDate());
//            }
//        } else {
//            if (null != product) {
//                messErr.add("Mã đã tồn tại");
//                fieldErr.add("code");
//            } else {
//                productAdminDTO.setCreateDate(LocalDate.now());
//            }
//        }
//
//        String name = myRecord.get(col++);
//        productAdminDTO.setName(name);
//        if (StringUtils.isBlank(code.trim())) {
//            messErr.add("Tên không được để trống");
//            fieldErr.add("name");
//        } else if (code.length() > 250 || code.length() < 0) {
//            messErr.add("Tên phải <= 250 kí tự");
//            fieldErr.add("name");
//        }
//        String b = myRecord.get(col++);
//        productAdminDTO.setBrandName(b);
//        if (StringUtils.isBlank(b)) {
//            messErr.add("Hãng không được để trống");
//            fieldErr.add("brandName");
//        } else {
//            productAdminDTO.setIdBrand(Long.parseLong(b.split("-")[0]));
//            Brand brand = brrp.findById(Long.parseLong(b.split("-")[0])).orElse(null);
//            if (null == brand) {
//                messErr.add("Hãng không tồn tại");
//                fieldErr.add("brandName");
//            }
//        }
//
//        String c = myRecord.get(col++);
//        productAdminDTO.setCategoryName(c);
//        if (StringUtils.isBlank(c)) {
//            messErr.add("Danh Mục không được để trống");
//            fieldErr.add("categoryName");
//        } else {
//            productAdminDTO.setIdCategory(Long.parseLong(c.split("-")[0]));
//            Category category = ctrp.findById(Long.parseLong(c.split("-")[0])).orElse(null);
//            if (null == category) {
//                messErr.add("Danh mục không tồn tại");
//                fieldErr.add("categoryName");
//            }
//        }
//
//        String m = myRecord.get(col++);
//        productAdminDTO.setMaterialName(m);
//        if (StringUtils.isBlank(m)) {
//            messErr.add("Chất Liệu không được để trống");
//            fieldErr.add("materialName");
//        } else {
//            productAdminDTO.setIdMaterial(Long.parseLong(m.split("-")[0]));
//            Material material = mtrp.findById(Long.parseLong(m.split("-")[0])).orElse(null);
//            if (null == material) {
//                messErr.add("Chất Liệu không tồn tại");
//                fieldErr.add("materialName");
//            }
//        }
//
//        String s = myRecord.get(col++);
//        productAdminDTO.setSoleImport(s);
//        if (StringUtils.isBlank(s)) {
//            messErr.add("Đế giày không được để trống");
//            fieldErr.add("soleImport");
//        } else {
//            productAdminDTO.setIdSole(Long.parseLong(s.split("-")[0]));
//            Sole sole = slrp.findById(Long.parseLong(s.split("-")[0])).orElse(null);
//            if (null == sole) {
//                messErr.add("Đế giày không tồn tại");
//                fieldErr.add("soleImport");
//            }
//        }
//
//
//        String price = myRecord.get(col++);
//        productAdminDTO.setPriceExport(price);
//        if (StringUtils.isBlank(price)) {
//            messErr.add("Giá không được để trống");
//            fieldErr.add("priceExport");
//        } else if (!price.matches(regex)) {
//            messErr.add("Vui lòng nhập giá là số");
//            fieldErr.add("priceExport");
//        } else if (Double.parseDouble(price) < 0) {
//            messErr.add("Vui lòng nhập giá lớn hơn 0");
//            fieldErr.add("priceExport");
//        } else {
//            productAdminDTO.setPrice(new BigDecimal(price));
//        }
//        String mota = myRecord.get(col++);
//        productAdminDTO.setDescription(mota);
//        if (mota.length() > 500) {
//            messErr.add("Vui lòng nhập mô tả không vượt quá 500 kí tự");
//            fieldErr.add("description");
//        }
//        String status = myRecord.get(col++);
//        if (!StringUtils.isBlank(status)) {
//            productAdminDTO.setStatus(Integer.valueOf(status));
//        }
//        String anhSanPham = myRecord.get(col++);
//        productAdminDTO.setImageNameImport(anhSanPham.trim());
//        productAdminDTO.setImagesExportErrors(anhSanPham);
//        if (StringUtils.isBlank(anhSanPham)) {
//            messErr.add("Vui lòng điền ảnh của sản phẩm");
//            fieldErr.add("imagesExportErrors");
//        } else if (!anhSanPham.matches(regexLink)) {
//            messErr.add("Vui lòng điền ảnh của sản phẩm là link");
//            fieldErr.add("imagesExportErrors");
//        }
//        String listSize = myRecord.get(col++);
//        productAdminDTO.setSizeExport(listSize);
//        if (StringUtils.isBlank(listSize.trim())) {
//            messErr.add("Vui lòng nhập kích cỡ của sản phẩm");
//            fieldErr.add("sizeExport");
//        } else {
//            String[] arr = listSize.split(",");
//            Set<String> lstSizeNew = new HashSet<>();
//            for (int i = 0; i < arr.length; i++) {
//                if (!arr[i].trim().matches(regexSize)) {
//                    messErr.add("Danh sách kích cỡ không hợp lệ, chỉ được nhập\n chỉ được nhập kích cỡ là số và có 2 chữ số\n");
//                    fieldErr.add("sizeExport");
//                    break;
//                } else {
//                    Size size = sizeAdminReposiotry.findBySizeNumber(arr[i].trim());
//                    if (size == null) {
//                        messErr.add("kích cỡ không tồn tại trong hệ thống");
//                        fieldErr.add("sizeExport");
//                        break;
//                    } else {
//                        lstSizeNew.add(arr[i].trim());
//                    }
//                }
//            }
//            productAdminDTO.setSizeImport(lstSizeNew);
//        }
//        String listColor = myRecord.get(col++);
//        productAdminDTO.setColorExport(listColor);
//        if (StringUtils.isBlank(listColor.trim())) {
//            messErr.add("Vui lòng nhập màu sắc của sản phẩm");
//            fieldErr.add("colorExport");
//        } else {
//            String[] arr = listColor.split(",");
//            Set<String> lstColorNew = new HashSet<>();
//            for (int i = 0; i < arr.length; i++) {
//                Color color = colorAdminRepository.findByCode(arr[i].trim());
//                if (color == null) {
//                    messErr.add("Mã màu sắc không tồn tại trong hệ thống");
//                    fieldErr.add("colorExport");
//                    break;
//                } else {
//                    lstColorNew.add(arr[i].trim());
//                }
//            }
//            productAdminDTO.setColorImport(lstColorNew);
//        }
//        String quantity = myRecord.get(col++).trim();
//        productAdminDTO.setQuantityExport(quantity);
//        if (StringUtils.isBlank(quantity)) {
//            messErr.add("Vui lòng nhập số lượng của sản phẩm");
//            fieldErr.add("quantityExport");
//        } else if (!quantity.matches(regexQuantity)) {
//            messErr.add("Vui lòng nhập số lượng phải là số và lớn hơn 0");
//            fieldErr.add("quantityExport");
//        } else {
//            productAdminDTO.setQuantity(Integer.valueOf(quantity));
//        }
//        String shoeCollar = myRecord.get(col++).trim();
//        productAdminDTO.setShoeCollarExport(shoeCollar);
//        if (StringUtils.isBlank(shoeCollar)) {
//            messErr.add("Vui lòng nhập cổ giày của sản phẩm");
//            fieldErr.add("shoeCollarExport");
//        } else {
//            if (shoeCollar.equals("LowCollar")) {
//                productAdminDTO.setShoeCollarImport(0);
//            } else {
//                productAdminDTO.setShoeCollarImport(1);
//            }
//        }
//        productAdminDTO.setMessageErr(messErr);
//        productAdminDTO.setFieldErr(fieldErr);
//        return productAdminDTO;
//    }


}
