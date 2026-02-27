package com.zeroone.inventory.service;

import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.dto.ProductResponse;
import com.zeroone.inventory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
     ProductResponse saveProduct(ProductRequest productRequest);

    Page<Product> getAllProducts(Pageable pageable);

     ProductResponse getProductById(Long id);

     ProductResponse updateProduct(Long id ,ProductRequest productRequest);

     void deleteProduct(Long id);

     List<ProductResponse> getLowStockProducts();

    ProductResponse updateQuantity(Long id, Integer quantity);
}
