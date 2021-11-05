package com.github.gronblack.pm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "planet")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, unique = true))
public class Planet extends BaseEntity {

    @Column(name = "radius", nullable = false)
    @Positive
    private int radius;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lord_id")
    @JsonBackReference
    @ToString.Exclude
    private Lord lord;

    public Planet(Integer id, @NotBlank @Size(min = 1, max = 100) String name, int radius) {
        super(id, name);
        this.radius = radius;
    }
}
