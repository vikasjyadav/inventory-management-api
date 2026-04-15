package com.zeroone.inventory.purchase.repository;

import com.zeroone.inventory.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase , Long> {
}
