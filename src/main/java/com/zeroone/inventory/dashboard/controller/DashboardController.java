package com.zeroone.inventory.dashboard.controller;

import com.zeroone.inventory.dashboard.service.DashboardService;
import com.zeroone.inventory.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/total-products")
    public ResponseEntity<Long> getTotalProducts(){
        return ResponseEntity.ok(dashboardService.getTotalProducts());
    }

    @GetMapping("/total-orders")
    public ResponseEntity<Long> getTotalOrders(){
        return ResponseEntity.ok(dashboardService.getTotalPurchases());
    }

    @GetMapping("/total-sales")
    public ResponseEntity<Double> getTotalSales(){
        return ResponseEntity.ok(dashboardService.getTotalSales());
    }

    @GetMapping("/low-stock-products")
    public ResponseEntity<List<Product>> getLowStockProducts(){
        return ResponseEntity.ok(dashboardService.getLowStockProducts());
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<Map<LocalDate, Double>> getSalesSummary() {
        return ResponseEntity.ok(dashboardService.getSalesSummary());
    }
}