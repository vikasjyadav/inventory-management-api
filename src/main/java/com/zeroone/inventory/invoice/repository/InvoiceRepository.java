package com.zeroone.inventory.invoice.repository;

import com.zeroone.inventory.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice ,Long> {
    Invoice findTopByOrderByIdDesc();

    List<Invoice> findByPurchaseId(Long purchaseId);
}
