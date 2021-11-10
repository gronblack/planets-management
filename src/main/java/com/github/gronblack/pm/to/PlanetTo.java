package com.github.gronblack.pm.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanetTo extends BaseTo {

    Integer lordId;

    public PlanetTo(Integer id, @NotBlank @Size(min = 1, max = 100) String name, Integer lordId) {
        super(id, name);
        this.lordId = lordId;
    }
}
