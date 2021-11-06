package com.github.gronblack.pm.repository;

import com.github.gronblack.pm.model.Planet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlanetRepository extends BaseRepository<Planet> {

    @EntityGraph(attributePaths = {"lord"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Planet p WHERE p.id=:id")
    Optional<Planet> getWithLord(int id);

    @Modifying
    @Query(value = "UPDATE Planet p SET p.lord.id =:lordId WHERE p.id=:id")
    void setLord(int id, int lordId);
}
