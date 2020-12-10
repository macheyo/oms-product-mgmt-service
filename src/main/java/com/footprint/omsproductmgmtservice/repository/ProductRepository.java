package com.footprint.omsproductmgmtservice.repository;

import com.footprint.omsproductmgmtservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
