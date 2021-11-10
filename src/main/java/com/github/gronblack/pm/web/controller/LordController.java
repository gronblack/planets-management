package com.github.gronblack.pm.web.controller;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.repository.LordRepository;
import com.github.gronblack.pm.to.LordFullTo;
import com.github.gronblack.pm.to.LordTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.github.gronblack.pm.util.LordsUtil.*;
import static com.github.gronblack.pm.util.validation.ValidationUtil.assureIdConsistent;
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
    public List<LordTo> getAll(@RequestParam Map<String,String> params) {
        log.info("getAll");
        return getTos(repository.findAll(repository.createPageRequest(params)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LordFullTo> getWithPlanets(@PathVariable int id) {
        log.info("getWithPlanets {}", id);
        return ResponseEntity.of(createFullToOptional(repository.getWithPlanets(id), id));
    }

    @GetMapping("/count")
    public int count(@RequestParam @Nullable Boolean idle) {
        return Boolean.TRUE.equals(idle) ? repository.idleCount() : (int) repository.count();
    }

    @GetMapping("/idle")
    public List<LordTo> getIdle(@RequestParam Map<String,String> params) {
        log.info("getIdle");
        return getTos(repository.getIdle(repository.createPageRequest(params)));
    }

    @GetMapping("/young")
    public List<LordTo> getYoung() {
        log.info("getIdle");
        return getTos(repository.getYoung());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lord> createWithLocation(@Valid @RequestBody LordTo to) {
        log.info("create {}", to);
        checkNew(to);
        Lord created = repository.save(getFromTo(to));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody LordTo to) {
        log.info("update {} with id={}", to, id);
        assureIdConsistent(to, id);
        repository.save(getFromTo(to));
    }
}
