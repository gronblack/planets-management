package com.github.gronblack.pm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "lord")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Lord extends BaseEntity {

    @Column(name = "age", nullable = false)
    @Positive
    private int age;

    @Column(name = "height", nullable = false)
    @Positive
    private int height;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lord")
    @JsonManagedReference
    @ToString.Exclude
    private Set<Planet> planets;

    public Lord(Integer id, @NotBlank @Size(min = 1, max = 100) String name, int age, int height) {
        super(id, name);
        this.age = age;
        this.height = height;
    }
}
