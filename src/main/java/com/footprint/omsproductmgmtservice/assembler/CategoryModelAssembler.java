package com.footprint.omsproductmgmtservice.assembler;

import com.footprint.omsproductmgmtservice.controller.CategoryController;
import com.footprint.omsproductmgmtservice.entity.Category;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {

    @Override
    public EntityModel<Category> toModel(Category category) {

        return EntityModel.of(category,
                linkTo(methodOn(CategoryController.class).category(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).listAll()).withRel("categories"));
    }
}