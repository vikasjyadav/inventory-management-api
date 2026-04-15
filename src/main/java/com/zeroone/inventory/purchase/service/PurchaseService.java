package com.zeroone.inventory.purchase.service;

import com.zeroone.inventory.entity.Product;
import com.zeroone.inventory.purchase.entity.Purchase;
import com.zeroone.inventory.purchase.entity.PurchaseItem;
import com.zeroone.inventory.purchase.repository.PurchaseRepository;
import com.zeroone.inventory.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Purchase createPurchase(Purchase purchase) {

        double total = 0.0;


        if (purchase.getPurchaseItems() != null) {
            for (PurchaseItem item : purchase.getPurchaseItems()) {

                item.setPurchase(purchase);

                total += item.getQuantity() * item.getPurchasePrice();

                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found with id: " + item.getProductId()));

                product.setQuantity(product.getQuantity() + item.getQuantity());

                productRepository.save(product);
            }
        }

        purchase.setTotalAmount(total);

        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getAllPurchases(){
        return purchaseRepository.findAll();
    }
}