package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.dtos.CategoryDTO;
import edu.uoc.epcsd.showcatalog.dtos.ShowDTO;
import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.entities.Show;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
import edu.uoc.epcsd.showcatalog.requests.ShowRequest;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private KafkaTemplate<String, Show> kafkaTemplate;

    @GetMapping
    public List<ShowDTO> getShows(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId
    ) {
        log.trace("getShows");
        if (name == null && categoryId == null) {
            return mapListShowToDTO(showRepository.findAll());
        } else if (name.isEmpty()) {
            //return showRepository.findBy();
            return mapListShowToDTO(showRepository.findAll());
        } else if (categoryId <= 0) {
            //return showRepository.findBy();
            return mapListShowToDTO(showRepository.findAll());
        } else {
            //return showRepository.findBy();
            return mapListShowToDTO(showRepository.findAll());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShowDTO> getShow(@PathVariable(value = "id") Long showId) {
        log.trace("getShow");
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        return ResponseEntity.ok().body(mapShowToDTO(show));

    }

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody @NonNull ShowRequest request) {
        log.trace("createShow");
        Show show = mapToShow(request);
        showRepository.save(show);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(show.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable(value = "id") Long showId) {
        log.trace("deleteShow" + showId);
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        showRepository.delete(show);
        return ResponseEntity.ok().body("Show deleted with success!");
    }

    private Show mapToShow(ShowRequest request) {
        Show show = new Show();
        show.setName(request.getName());
        show.setDescription(request.getDescription());
        show.setImage(request.getImage());
        show.setCapacity(request.getCapacity());
        show.setPrice(request.getPrice());
        show.setStatus(true);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(
                    request.getCategoryId()).orElse(null);
            if (category != null) {
                show.setCategory(category);
            }
        }

        return show;
    }

    private ShowDTO mapShowToDTO(Show show) {
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

    private CategoryDTO getCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    private List<ShowDTO> mapListShowToDTO(List<Show> shows) {
        List<ShowDTO> listDto = new ArrayList<ShowDTO>();
        for (Show show : shows) {
            listDto.add(mapShowToDTO(show));
        }
        return listDto;
    }

}
