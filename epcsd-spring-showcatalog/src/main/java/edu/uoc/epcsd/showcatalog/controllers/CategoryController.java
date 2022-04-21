package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        log.trace("getAllCategories");
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> getCategory(@PathVariable(value = "id") Long categoryId) {
        log.trace("getCategory");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found for this id: " + categoryId));
        return ResponseEntity.ok().body(category);
    }


    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @NonNull Category category) {
        log.trace("createCategory");
        categoryRepository.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        log.trace("deleteCategory");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found for this id: " + categoryId));
        categoryRepository.delete(category);
        return ResponseEntity.ok().body("Category deleted with success!");
    }

}
