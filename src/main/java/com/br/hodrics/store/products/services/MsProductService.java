package com.br.hodrics.store.products.services;

import com.br.hodrics.deps.dtos.products.ProductDTO;
import com.br.hodrics.deps.exceptions.IntegrationException;
import com.br.hodrics.deps.services.aws.S3UploadService;
import com.br.hodrics.deps.services.aws.dynamo.ProductDynamoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MsProductService {

    private static final Logger log = LoggerFactory.getLogger(MsProductService.class);
    private final S3UploadService s3UploadService;
    private final ProductDynamoService productService;

    public MsProductService(S3UploadService s3UploadService, ProductDynamoService productService) {
        this.s3UploadService = s3UploadService;
        this.productService = productService;
    }

    public List<ProductDTO> listProducts(){
        return productService.getAll();
    }

    public ProductDTO findById(String id){
        return productService.getById(id);
    }

    public void delete(String id){
        productService.delete(id);
    }

    public ProductDTO update(String id, ProductDTO productDto){
        try {
            return productService.update(id, productDto);
        } catch (Exception e){
            log.error("MsProductService error when create new product {}" , productDto, e);
            throw new IntegrationException("MsProductService error when update new product");
        }
    }

    public ProductDTO create(String title, String price, String description, String category, MultipartFile image) {
        log.info("MsProductService create product start");
        try {
           final ProductDTO productDto = new ProductDTO(title,new BigDecimal(price),description,category);

           final ProductDTO newProduct = productService.create(productDto);
           final String keyImageInBucket = this.uploadImage(image, newProduct.getId());
           productDto.setImage(keyImageInBucket);
           return this.productService.update(newProduct.getId(), productDto);

        }catch (Exception e){
            log.error("MsProductService error when create new product {}" , title, e);
            throw new IntegrationException("MsProductService error when create new product");
        }
    }

    private String uploadImage(MultipartFile file, String key){
        return  s3UploadService.uploadImage(file, key);
    }
}
