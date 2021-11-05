package com.github.gronblack.pm.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanetTo extends BaseTo {
    public PlanetTo(Integer id, @NotBlank @Size(min = 1, max = 100) String name) {
        super(id, name);
    }
}
