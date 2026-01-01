package com.br.hodrics.store.products.controllers;

import com.br.hodrics.deps.dtos.products.ProductDTO;
import com.br.hodrics.store.products.services.MsProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/store/products")
public class ProductController {
    private final MsProductService service;

    public ProductController(MsProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        return ResponseEntity.ok().body(service.listProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(
                                      @RequestPart("title") String title,
                                      @RequestPart("price") String price,
                                      @RequestPart("description") String description,
                                      @RequestPart("category") String category,
                                      @RequestPart("image") MultipartFile image) {
        service.create(title, price, description, category, image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> update(@RequestBody ProductDTO productDto,@PathVariable String id) {
        service.update(id, productDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
