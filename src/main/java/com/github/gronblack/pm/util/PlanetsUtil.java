package com.github.gronblack.pm.util;

import com.github.gronblack.pm.model.Planet;
import com.github.gronblack.pm.to.PlanetFullTo;
import com.github.gronblack.pm.to.PlanetTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.gronblack.pm.util.ErrorUtil.notFound;

@UtilityClass
public class PlanetsUtil {
    public static List<PlanetTo> getTos(Collection<Planet> planets) {
        return planets.stream()
                .map(PlanetsUtil::createTo)
                .collect(Collectors.toList());
    }

    private static PlanetTo createTo(Planet planet) {
        return new PlanetTo(planet.getId(), planet.getName(), planet.getLord() == null ? null : planet.getLord().getId());
    }

    public static PlanetFullTo createFullTo(Planet planet) {
        return new PlanetFullTo(planet.getId(), planet.getName(), planet.getLord() == null ? null : LordsUtil.createTo(planet.getLord()));
    }

    public static Optional<PlanetFullTo> createFullToOptional(Optional<Planet> planetOptional, int id) {
        return Optional.of(createFullTo(planetOptional.orElseThrow(notFound(Planet.class, id))));
    }

    public static Planet getFromTo(PlanetTo to) {
        return to == null ? null : new Planet(to.getId(), to.getName());
    }
}
