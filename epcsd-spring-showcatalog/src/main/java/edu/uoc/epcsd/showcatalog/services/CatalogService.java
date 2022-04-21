package edu.uoc.epcsd.showcatalog.services;

import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.entities.Show;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CatalogService")
public class CatalogService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShowRepository showRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found for this id: " + categoryId));
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(getCategory(categoryId));
    }

}