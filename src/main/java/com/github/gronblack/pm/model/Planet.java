package com.github.gronblack.pm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "planet")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, unique = true))
public class Planet extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lord_id")
    @JsonIgnore
    @ToString.Exclude
    private Lord lord;

    public Planet(Integer id, @NotBlank @Size(min = 1, max = 100) String name) {
        super(id, name);
    }
}
