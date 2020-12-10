package com.footprint.omsproductmgmtservice.controller;

import com.footprint.omsproductmgmtservice.assembler.CategoryModelAssembler;
import com.footprint.omsproductmgmtservice.entity.Category;
import com.footprint.omsproductmgmtservice.exception.CategoryNotFoundException;
import com.footprint.omsproductmgmtservice.repository.CategoryRepository;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryModelAssembler categoryModelAssembler;

    @GetMapping("/list")
    public CollectionModel<EntityModel<Category>> listAll(){
        List<EntityModel<Category>> categories = categoryRepository.findAll().stream().map(categoryModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(categories,linkTo(methodOn(CategoryController.class).listAll()).withSelfRel());
    }
    @GetMapping("/{id}")
    public EntityModel<Category> category(@PathVariable Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
      return categoryModelAssembler.toModel(category);
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Category category){
        EntityModel<Category> categoryEntityModel = categoryModelAssembler.toModel(categoryRepository.save(category));
        return ResponseEntity.created(categoryEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(categoryEntityModel);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Category category, @PathVariable Long id){
        Category updatedCategory = categoryRepository.findById(id).map(cat -> {
            cat.setName(category.getName());
            cat.setDescription(category.getDescription());
            return categoryRepository.save(cat);
        }).orElseGet(()->{
            category.setId(id);
            return  categoryRepository.save(category);
        });
        EntityModel<Category> categoryEntityModel = categoryModelAssembler.toModel(updatedCategory);
        return ResponseEntity.created(categoryEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(categoryEntityModel);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
