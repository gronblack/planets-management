package com.github.gronblack.pm.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.repository.LordRepository;
import com.github.gronblack.pm.util.json.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.gronblack.pm.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = LordController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class LordController {
    static final String REST_URL = "/api/lords";
    private final LordRepository repository;

    public LordController(LordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @JsonView(Views.Public.class)
    public List<Lord> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/{id}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<Lord> getWithPlanets(@PathVariable int id) {
        log.info("getWithPlanets {}", id);
        return ResponseEntity.of(repository.getWithPlanets(id));
    }

    @GetMapping("/idle")
    @JsonView(Views.Public.class)
    public List<Lord> getIdle() {
        log.info("getIdle");
        return repository.getIdle();
    }

    @GetMapping("/young")
    @JsonView(Views.Public.class)
    public List<Lord> getYoung() {
        log.info("getIdle");
        return repository.getYoung();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lord> createWithLocation(@Valid @RequestBody Lord lord) {
        log.info("create {}", lord);
        checkNew(lord);
        Lord created = repository.save(lord);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
