package com.br.hodrics.store.products.controllers;

import com.br.hodrics.deps.dtos.products.ProductDTO;
import com.br.hodrics.store.products.services.MsProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/store/products")
public class ProductController {
    private final MsProductService service;

    public ProductController(MsProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok().body(service.listProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> save(
            @RequestPart("title") String title,
            @RequestPart("price") String price,
            @RequestPart("description") String description,
            @RequestPart("category") String category,
            @RequestPart("image") MultipartFile image) {
        final ProductDTO productDTO = service.create(title, price, description, category, image);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.getId()).toUri()).build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDto, @PathVariable String id) {
        final ProductDTO updateProduct = service.update(id, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
