package com.footprint.omsproductmgmtservice.repository;


import com.footprint.omsproductmgmtservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
