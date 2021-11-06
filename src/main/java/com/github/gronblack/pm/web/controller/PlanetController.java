package com.github.gronblack.pm.web.controller;

import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.repository.PlanetRepository;
import com.github.gronblack.pm.to.PlanetFullTo;
import com.github.gronblack.pm.to.PlanetTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.github.gronblack.pm.util.PlanetsUtil.createFullToOptional;
import static com.github.gronblack.pm.util.PlanetsUtil.getTos;
import static com.github.gronblack.pm.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.gronblack.pm.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = PlanetController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PlanetController {
    static final String REST_URL = "/api/planets";
    private final PlanetRepository repository;

    public PlanetController(PlanetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PlanetTo> getAll(@RequestParam Map<String,String> params) {
        return getTos(repository.findAll(repository.createPageRequest(params)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetFullTo> getWithLord(@PathVariable int id) {
        log.info("getWithLord {}", id);
        return ResponseEntity.of(createFullToOptional(repository.getWithLord(id), id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> createWithLocation(@Valid @RequestBody Planet planet) {
        log.info("create {}", planet);
        checkNew(planet);
        Planet created = repository.save(planet);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody Planet planet) {
        log.info("update {} with id={}", planet, id);
        assureIdConsistent(planet, id);
        repository.save(planet);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void setLord(@PathVariable int id, @RequestParam int lordId) {
        log.info("Appoint Planet[id={}] to Lord[id={}]", id, lordId);
        repository.setLord(id, lordId);
    }
}
