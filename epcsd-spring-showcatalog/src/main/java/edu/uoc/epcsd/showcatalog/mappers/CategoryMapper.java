package edu.uoc.epcsd.showcatalog.mappers;

import edu.uoc.epcsd.showcatalog.dtos.CategoryDTO;
import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.requests.CategoryRequest;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public Category mapToCategory(CategoryRequest request) {
        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setName(request.getName());
        return category;
    }

    public CategoryDTO mapToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public List<CategoryDTO> mapListToDTO(List<Category> categories) {
        List<CategoryDTO> listDto = new ArrayList<>();
        for (Category category : categories) {
            listDto.add(mapToDTO(category));
        }
        return listDto;
    }

}
