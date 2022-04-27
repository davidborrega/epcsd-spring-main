package edu.uoc.epcsd.showcatalog.mappers;

import edu.uoc.epcsd.showcatalog.dtos.CategoryDTO;
import edu.uoc.epcsd.showcatalog.dtos.ShowDTO;
import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.entities.Show;

import java.util.ArrayList;
import java.util.List;

public class ShowMapper {

    public ShowDTO mapToDTO(Show show) {
        ShowDTO dto = new ShowDTO();
        dto.setId(show.getId());
        dto.setName(show.getName());
        dto.setDescription(show.getDescription());
        dto.setDuration(show.getDuration());
        dto.setCapacity(show.getCapacity());
        dto.setImage(show.getImage());
        dto.setPrice(show.getPrice());
        dto.setCategory(getCategoryDTO(show.getCategory()));
        return dto;
    }

    public List<ShowDTO> mapListToDTO(List<Show> shows) {
        List<ShowDTO> listDto = new ArrayList<>();
        for (Show show : shows) {
            listDto.add(mapToDTO(show));
        }
        return listDto;
    }

    private CategoryDTO getCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

}
