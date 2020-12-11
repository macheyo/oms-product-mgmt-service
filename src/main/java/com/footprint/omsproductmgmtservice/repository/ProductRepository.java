package com.footprint.omsproductmgmtservice.repository;

import com.footprint.omsproductmgmtservice.entity.Product;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "products", collectionResourceRel = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrganisationId(@Param("organisationId") Long organisationId);
}
