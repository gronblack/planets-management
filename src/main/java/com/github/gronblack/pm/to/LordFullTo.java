package com.github.gronblack.pm.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LordFullTo extends BaseTo {

    @Positive int age;

    @Positive int height;

    Set<PlanetTo> planets;

    public LordFullTo(Integer id, @NotBlank @Size(min = 1, max = 100) String name, int age, int height, List<PlanetTo> planetTos) {
        super(id, name);
        this.age = age;
        this.height = height;
        this.planets = planetTos == null ? null : new HashSet<>(planetTos);
    }
}
