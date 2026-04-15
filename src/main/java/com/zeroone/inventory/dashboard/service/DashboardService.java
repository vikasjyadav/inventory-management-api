package com.zeroone.inventory.dashboard.service;

import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.purchase.entity.Purchase;
import com.zeroone.inventory.purchase.repository.PurchaseRepository;
import com.zeroone.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;
@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public DashboardService(ProductRepository productRepository,
                            PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public long getTotalPurchases() {
        return purchaseRepository.count();
    }

    public double getTotalSales() {
        return purchaseRepository.findAll()
                .stream()
                .mapToDouble(Purchase::getTotalAmount)
                .sum();
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findByQuantityLessThan(10);
    }

    public Map<LocalDate, Double> getSalesSummary() {

        return purchaseRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        purchase -> purchase.getPurchaseDate().toLocalDate(),
                        Collectors.summingDouble(Purchase::getTotalAmount)
                ));
    }
}