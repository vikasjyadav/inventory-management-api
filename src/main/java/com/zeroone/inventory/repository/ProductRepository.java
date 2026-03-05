package com.zeroone.inventory.repository;

import com.zeroone.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long > {

     List<Product> findByQuantityLessThan(Integer quantity);
}
