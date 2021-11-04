package com.github.gronblack.pm.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

    public Lord(Integer id, @NotBlank @Size(min = 1, max = 100) String name, int age) {
        super(id, name);
        this.age = age;
    }
}
