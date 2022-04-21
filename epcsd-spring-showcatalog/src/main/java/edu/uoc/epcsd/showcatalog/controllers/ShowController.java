package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.entities.Show;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
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
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private KafkaTemplate<String, Show> kafkaTemplate;

    @GetMapping
    public List<Show> getShows(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId
    ) {
        log.trace("getShows");
        if (name.isEmpty() && categoryId <= 0) {
            return showRepository.findAll();
        } else if (name.isEmpty()) {
            //return showRepository.findBy();
            return showRepository.findAll();
        } else if (categoryId <= 0) {
            //return showRepository.findBy();
            return showRepository.findAll();
        } else {
            //return showRepository.findBy();
            return showRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Show> getShow(@PathVariable(value = "id") Long showId) {
        log.trace("getShow");
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not found for this id: " + showId));
        return ResponseEntity.ok().body(show);

    }

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody @NonNull Show show) {
        log.trace("createShow");
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

}
