package com.zeroone.inventory.service;
import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.mapper.*;
import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.dto.ProductResponse;
import com.zeroone.inventory.mapper.ProductMapper;
import com.zeroone.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {
       return ProductMapper.toResponse(productRepository.save(ProductMapper.toEntity(productRequest)));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> all = productRepository.findAll();
        List<ProductResponse> responseList = new ArrayList<>();
        for (Product product : all) {
            responseList.add(ProductMapper.toResponse(product));
        }

        return responseList ;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            Product product = byId.get();

            return ProductMapper.toResponse(product);
        }else {
            throw new RuntimeException("Product not found with id:"+id);
        }
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            Product existedProduct = byId.get();
            ProductMapper.updateEntity(existedProduct,productRequest);
            productRepository.save(existedProduct);

            return ProductMapper.toResponse(existedProduct);
        }
        throw new RuntimeException("Product Not Found With Id : "+id);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            Product product = byId.get();
            productRepository.delete(product);
        }else {
            throw new RuntimeException("Product No Exist With Id :"+id);
        }

    }

    @Override
    public List<ProductResponse> getLowStockProducts() {
         final int LOW_STOCK_THRESHOLD = 10;
        List<Product> byQuantityLessThan = productRepository.findByQuantityLessThan(LOW_STOCK_THRESHOLD);

        return  ProductMapper.toResponseList(byQuantityLessThan);
    }

    @Override
    public ProductResponse updateQuantity(Long id, Integer quantity) {
        if (quantity == null || quantity < 0){
            throw new RuntimeException("Quantity must be zero or positive");
        }
        Product existedProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id)
                );

        existedProduct.setQuantity(quantity);

        Product updatedProduct = productRepository.save(existedProduct);

        return ProductMapper.toResponse(updatedProduct);

    }
}
