package com.zeroone.inventory;

import com.zeroone.inventory.dto.ProductRequest;
import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.exception.ResourceNotFoundException;
import com.zeroone.inventory.mapper.ProductMapper;
import com.zeroone.inventory.repository.ProductRepository;

import com.zeroone.inventory.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;



    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest request;

    @BeforeEach
    void setUp() {

        ProductMapper productMapper = new ProductMapper();
        productService = new ProductServiceImpl(productMapper, productRepository);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setQuantity(10);

        request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(100.0);
        request.setQuantity(10);
    }

    @Test
    void getProductById_success() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertNotNull(productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_notFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(1L));
    }

    @Test
    void updateQuantity_invalidQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateQuantity(1L, -5));
    }

    @Test
    void updateQuantity_success() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        assertNotNull(productService.updateQuantity(1L, 20));
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getAllProducts_success() {

        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(product)));

        assertNotNull(productService.getAllProducts(pageable));
    }
}