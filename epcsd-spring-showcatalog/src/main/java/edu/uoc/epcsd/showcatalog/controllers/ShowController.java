package edu.uoc.epcsd.showcatalog.controllers;

import edu.uoc.epcsd.showcatalog.entities.Category;
import edu.uoc.epcsd.showcatalog.entities.Show;
import edu.uoc.epcsd.showcatalog.repositories.ShowRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.OK)
    public List<Show> getShows(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId
    ) {
        log.trace("getShows");
        System.out.println("getShows");
        System.out.println("name="+name);
        System.out.println("categoryId="+categoryId);
        return showRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Show getShow(@PathVariable(value = "id") Long showId) {
        log.trace("getShow" + showId);
        return new Show();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShow() {
        log.trace("createShow");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShow(@PathVariable(value = "id") Long showId) {
        log.trace("deleteShow" + showId);
    }

}
