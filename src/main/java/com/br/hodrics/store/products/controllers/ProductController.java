package com.br.hodrics.store.products.controllers;

import com.br.hodrics.deps.dtos.products.ProductDto;
import com.br.hodrics.store.products.services.MsProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final MsProductService service;

    public ProductController(MsProductService service) {
        this.service = service;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ProductDto>> findAll(){
        return ResponseEntity.ok().body(service.listProducts());
    }

}
