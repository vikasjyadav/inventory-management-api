package com.zeroone.inventory.service;

import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.exception.ResourceNotFoundException;
import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.dto.ProductResponse;
import com.zeroone.inventory.mapper.ProductMapper;
import com.zeroone.inventory.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${inventory.low-stock-threshold}")
    private int lowStockThreshold;

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper,
                              ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    private String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return "SYSTEM";
        }

        Object principal = authentication.getPrincipal();

        // If using Spring's UserDetails
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            return userDetails.getUsername();
        }

        // If using your custom User entity
        if (principal instanceof com.zeroone.inventory.security.User user) {
            return user.getUsername();
        }

        return principal.toString();
    }

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {

        logger.info("Creating product with name: {}", productRequest.getName());

        Product product = productMapper.toEntity(productRequest);

        String currentUser = getCurrentUsername();

        product.setCreatedBy(currentUser);
        product.setUpdatedBy(currentUser);

        Product savedProduct = productRepository.save(product);

        logger.info("Product created successfully with id: {}", savedProduct.getId());

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {

        logger.info("Fetching products - page: {}, size: {}, sort: {}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        return productRepository.findAll(pageable);
    }

    @Override
    public ProductResponse getProductById(Long id) {

        logger.info("Fetching product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with id: {}", id);
                    return new ResourceNotFoundException(
                            "Product not found with id: " + id);
                });

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {

        logger.info("Updating product with id: {}", id);

        Product existedProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found for update with id: {}", id);
                    return new ResourceNotFoundException(
                            "Product Not Found With Id : " + id);
                });

        productMapper.updateEntity(existedProduct, productRequest);

        existedProduct.setUpdatedBy(getCurrentUsername());

        Product updatedProduct = productRepository.save(existedProduct);

        logger.info("Product updated successfully with id: {}", id);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        logger.info("Deleting product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found for deletion with id: {}", id);
                    return new ResourceNotFoundException(
                            "Product No Exist With Id : " + id);
                });

        productRepository.delete(product);

        logger.info("Product deleted successfully with id: {}", id);
    }

    @Override
    public List<ProductResponse> getLowStockProducts() {

        logger.info("Fetching low stock products with threshold: {}", lowStockThreshold);

        List<Product> products =
                productRepository.findByQuantityLessThan(lowStockThreshold);

        logger.info("Found {} low stock products", products.size());

        return productMapper.toResponseList(products);
    }

    @Override
    public ProductResponse updateQuantity(Long id, Integer quantity) {

        logger.info("Updating quantity for product id: {} to {}", id, quantity);

        if (quantity == null || quantity < 0) {
            logger.error("Invalid quantity provided for product id: {}", id);
            throw new IllegalArgumentException("Quantity must be zero or positive");
        }

        Product existedProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found while updating quantity with id: {}", id);
                    return new ResourceNotFoundException(
                            "Product not found with id: " + id);
                });

        existedProduct.setQuantity(quantity);
        existedProduct.setUpdatedBy(getCurrentUsername());

        Product updatedProduct = productRepository.save(existedProduct);

        logger.info("Quantity updated successfully for product id: {}", id);

        return productMapper.toResponse(updatedProduct);
    }
}