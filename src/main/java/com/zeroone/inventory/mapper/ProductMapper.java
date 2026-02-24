package com.zeroone.inventory.mapper;

import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.dto.ProductResponse;
import com.zeroone.inventory.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(ProductRequest request){
        if(request == null){
            return null;
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        return product;
    }

    public static ProductResponse toResponse(Product product){
        if (product == null){
            return null;
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setCreatedAt(product.getCreatedAt());

        return productResponse;
    }
    public static void updateEntity(Product product, ProductRequest request) {

        if (product == null || request == null) {
            return;
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {

        if (products == null) {
            return null;
        }
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}
