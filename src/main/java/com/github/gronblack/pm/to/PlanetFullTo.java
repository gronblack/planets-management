package com.github.gronblack.pm.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanetFullTo extends BaseTo {

    @Positive int radius;

    LordTo lord;

    public PlanetFullTo(Integer id, @NotBlank @Size(min = 1, max = 100) String name, int radius, LordTo lordTo) {
        super(id, name);
        this.radius = radius;
        this.lord = lordTo;
    }
}
