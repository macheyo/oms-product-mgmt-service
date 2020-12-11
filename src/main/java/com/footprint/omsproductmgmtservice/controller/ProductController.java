package com.footprint.omsproductmgmtservice.controller;

import com.footprint.omsproductmgmtservice.assembler.ProductModelAssembler;
import com.footprint.omsproductmgmtservice.entity.Product;
import com.footprint.omsproductmgmtservice.exception.ProductNotFoundException;
import com.footprint.omsproductmgmtservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductModelAssembler productModelAssembler;

    @GetMapping("/list")
    public CollectionModel<EntityModel<Product>> listAll(){
        List<EntityModel<Product>> products = productRepository.findAll().stream().map(productModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(products,linkTo(methodOn(ProductController.class).listAll()).withSelfRel());
    }
    @GetMapping("/{id}")
    public EntityModel<Product> product(@PathVariable Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
      return productModelAssembler.toModel(product);
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product){
        EntityModel<Product> productEntityModel = productModelAssembler.toModel(productRepository.save(product));
        return ResponseEntity.created(productEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(productEntityModel);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Long id){
        Product updatedProduct = productRepository.findById(id).map(prod -> {
            prod.setName(product.getName());
            prod.setDescription(product.getDescription());
            prod.setPrice(product.getPrice());
            prod.setOrganisationId(product.getOrganisationId());
            prod.setImageURL(product.getImageURL());
            return productRepository.save(prod);
        }).orElseGet(()->{
            product.setId(id);
            return  productRepository.save(product);
        });
        EntityModel<Product> productEntityModel = productModelAssembler.toModel(updatedProduct);
        return ResponseEntity.created(productEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(productEntityModel);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
