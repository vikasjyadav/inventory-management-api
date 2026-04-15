package com.zeroone.inventory.vendor.controller;

import com.zeroone.inventory.vendor.entity.Vendor;
import com.zeroone.inventory.vendor.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor){
        Vendor saved = vendorService.saveVendor(vendor);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors(){
        List<Vendor> allVendor = vendorService.getAllVendor();
        return ResponseEntity.ok(allVendor);
    }

}
