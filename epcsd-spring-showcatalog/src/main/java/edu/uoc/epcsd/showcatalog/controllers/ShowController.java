package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.dtos.IdentifierDTO;
import edu.uoc.epcsd.showcatalog.dtos.PerformanceDTO;
import edu.uoc.epcsd.showcatalog.dtos.ShowDTO;
import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.entities.Show;
import edu.uoc.epcsd.showcatalog.kafka.KafkaTopicConfig;
import edu.uoc.epcsd.showcatalog.mappers.PerformanceMapper;
import edu.uoc.epcsd.showcatalog.mappers.ShowMapper;
import edu.uoc.epcsd.showcatalog.repositories.CategoryRepository;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
import edu.uoc.epcsd.showcatalog.requests.PerformanceRequest;
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

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    private final ShowMapper showMapper = new ShowMapper();

    private final PerformanceMapper performanceMapper = new PerformanceMapper();

    @GetMapping
    public List<ShowDTO> getShows(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId
    ) {
        log.trace("getShows");
        if (name == null && categoryId == null) {
            return showMapper.mapListToDTO(showRepository.findAll());
        } else if (name == null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null) {
                return showMapper.mapListToDTO(showRepository.findShowsByCategory(category));
            } else {
                return new ArrayList<>();
            }
        } else if (categoryId == null) {
            return showMapper.mapListToDTO(showRepository.findShowsByName(name));
        } else {
            return showMapper.mapListToDTO(showRepository.findShowsByName(name));
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShowDTO> getShow(@PathVariable(value = "id") Long showId) {
        log.trace("getShow");
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        return ResponseEntity.ok().body(showMapper.mapToDTO(show));

    }

    @PostMapping
    public ResponseEntity<IdentifierDTO> createShow(@RequestBody @NonNull ShowRequest request) {
        log.trace("createShow");
        Show show = mapToShow(request);
        showRepository.save(show);
        kafkaTemplate.send(kafkaTopicConfig.showAddTopic().name(), show);
        IdentifierDTO idDTO = new IdentifierDTO();
        idDTO.setId(show.getId());
        return ResponseEntity.ok().body(idDTO);
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<Boolean> openShow(@PathVariable(value = "id") Long showId) {
        log.trace("open show for id: " + showId);
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        show.setStatus(true);
        showRepository.save(show);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Boolean> cancelShow(@PathVariable(value = "id") Long showId) {
        log.trace("open show for id: " + showId);
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        show.setStatus(false);
        showRepository.save(show);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable(value = "id") Long showId) {
        log.trace("deleteShow" + showId);
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        showRepository.delete(show);
        return ResponseEntity.ok().body("Show deleted with success!");
    }

    @GetMapping("/{id}/performance")
    private List<PerformanceDTO> getPerformances(@PathVariable(value = "id") Long showId) {
        log.trace("getShowPerformances of show id: " + showId);
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        return performanceMapper.mapToDTO(show.getPerformances());
    }

    @PostMapping("/{id}/performance")
    public ResponseEntity<?> createPerformance(@PathVariable(value = "id") Long showId, @RequestBody @NonNull PerformanceRequest request) {
        log.trace("createPerormance of show id: " + showId);
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));

        show.addPerformance(performanceMapper.map(request));
        showRepository.save(show);

        return ResponseEntity.created(getLocation("/{id}/performance", show)).build();
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
            categoryRepository.findById(
                    request.getCategoryId()).ifPresent(show::setCategory);
        }
        return show;
    }

    private URI getLocation(String path, Show show) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(show.getId())
                .toUri();
    }

}
