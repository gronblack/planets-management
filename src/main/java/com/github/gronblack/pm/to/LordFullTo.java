package com.github.gronblack.pm.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LordFullTo extends LordTo {

    Set<PlanetTo> planetTos;

    public LordFullTo(Integer id, String name, int age, Collection<PlanetTo> planetTos) {
        super(id, name, age);
        this.planetTos = planetTos == null ? null : new HashSet<>(planetTos);
    }
}
