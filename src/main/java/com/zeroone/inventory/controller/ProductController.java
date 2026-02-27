package com.zeroone.inventory.controller;

import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.dto.ProductResponse;
import com.zeroone.inventory.mapper.ProductMapper;
import com.zeroone.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductMapper productMapper;

    private final ProductService productService;


    public ProductController(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){

        return new ResponseEntity<>( productService.saveProduct(request), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productService.getAllProducts(pageable)
                .map(productMapper::toResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse productById = productService.getProductById(id);

        return new ResponseEntity<>(productById,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateById(@PathVariable Long id ,
                                                      @Valid @RequestBody ProductRequest request){
        ProductResponse productById = productService.updateProduct(id, request);
        return new ResponseEntity<>(productById,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponse>> getLowStockProducts(){
        List<ProductResponse> lowStockProducts = productService.getLowStockProducts();
        return new ResponseEntity<>(lowStockProducts,HttpStatus.OK);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductResponse> updateProductQuantityById(@RequestParam Integer quantity,@PathVariable Long id){
        return new ResponseEntity<>(productService.updateQuantity(id,quantity),HttpStatus.OK);
    }
}
