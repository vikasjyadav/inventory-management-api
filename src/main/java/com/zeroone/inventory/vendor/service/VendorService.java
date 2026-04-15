package com.zeroone.inventory.vendor.service;

import com.zeroone.inventory.vendor.entity.Vendor;
import com.zeroone.inventory.vendor.repository.VendorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor saveVendor(Vendor vendor){
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendor(){
        return vendorRepository.findAll();
    }
}
