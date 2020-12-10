package com.footprint.omsproductmgmtservice.assembler;

import com.footprint.omsproductmgmtservice.controller.CategoryController;
import com.footprint.omsproductmgmtservice.entity.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product product) {

        return EntityModel.of(product,
                linkTo(methodOn(CategoryController.class).category(product.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).listAll()).withRel("products"));
    }
}