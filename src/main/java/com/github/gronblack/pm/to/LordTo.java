package com.github.gronblack.pm.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LordTo extends BaseTo {

    @Positive int age;

    public LordTo(Integer id, @NotBlank @Size(min = 1, max = 100) String name, int age) {
        super(id, name);
        this.age = age;
    }
}
