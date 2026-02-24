package com.zeroone.inventory.controller;

import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.dto.ProductResponse;
import com.zeroone.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){

        return new ResponseEntity<>( productService.saveProduct(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
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
