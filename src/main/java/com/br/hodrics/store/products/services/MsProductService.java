package com.br.hodrics.store.products.services;

import com.br.hodrics.deps.dtos.products.ProductDto;
import com.br.hodrics.deps.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsProductService {

    private final ProductService productService;

    public MsProductService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductDto> listProducts(){
        return productService.listProducts();
    }
}
