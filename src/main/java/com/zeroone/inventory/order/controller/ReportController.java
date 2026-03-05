package com.zeroone.inventory.order.controller;

import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.order.dto.SalesSummaryResponse;
import com.zeroone.inventory.order.service.OrderService;
import com.zeroone.inventory.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final OrderService orderService;

    private final ProductRepository productRepository;

    public ReportController(OrderService orderService, ProductRepository productRepository) {
        this.orderService = orderService;
        this.productRepository = productRepository;
    }

    @GetMapping("/sales")
    public ResponseEntity<SalesSummaryResponse> totalSaleSummary(){

        return new ResponseEntity<>(orderService.getTotalSalesSummary() , HttpStatus.OK);
    }

    @GetMapping("/low-stock/{quantity}")
    public ResponseEntity<List<Product>> getLowStockProducts(@PathVariable int quantity){
        List<Product> byQuantityLessThan = productRepository.findByQuantityLessThan(quantity);
        return new ResponseEntity<>(byQuantityLessThan , HttpStatus.OK);
    }
}
