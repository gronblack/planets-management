package com.github.gronblack.pm.service;

import com.github.gronblack.pm.model.Lord;
import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.repository.LordRepository;
import com.github.gronblack.pm.repository.PlanetRepository;
import com.github.gronblack.pm.to.PlanetTo;
import com.github.gronblack.pm.util.PlanetsUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final LordRepository lordRepository;

    public Planet saveFromTo(PlanetTo to) {
        Planet planet = PlanetsUtil.getFromTo(to);
        if (to.getLordId() != null) {
            planet.setLord(lordRepository.getById(to.getLordId()));
        }
        return planetRepository.save(planet);
    }

    public void setLord(int id, Integer lordId) {
        Lord lord = lordId == null ? null : lordRepository.getById(lordId);
        planetRepository.getById(id).setLord(lord);
    }
}
