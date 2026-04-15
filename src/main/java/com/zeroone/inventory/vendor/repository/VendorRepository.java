package com.zeroone.inventory.vendor.repository;

import com.zeroone.inventory.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
