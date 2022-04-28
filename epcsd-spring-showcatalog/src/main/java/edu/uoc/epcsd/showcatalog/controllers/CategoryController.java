package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.dtos.CategoryDTO;
import edu.uoc.epcsd.showcatalog.dtos.IdentifierDTO;
import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.mappers.CategoryMapper;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import edu.uoc.epcsd.showcatalog.requests.CategoryRequest;
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

    private final CategoryMapper mapper = new CategoryMapper();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAllCategories() {
        log.trace("getAllCategories");
        return mapper.mapListToDTO(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable(value = "id") Long categoryId) {
        log.trace("getCategory");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found for this id: " + categoryId));
        return ResponseEntity.ok().body(mapper.mapToDTO(category));
    }


    @PostMapping
    public ResponseEntity<IdentifierDTO> createCategory(@RequestBody @NonNull CategoryRequest request) {
        log.trace("createCategory");
        Category category = mapper.mapToCategory(request);
        categoryRepository.save(category);
        IdentifierDTO idDTO = new IdentifierDTO();
        idDTO.setId(category.getId());
        return ResponseEntity.ok().body(idDTO);
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
