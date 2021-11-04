package com.github.gronblack.pm.repository;

import com.github.gronblack.pm.model.Lord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LordRepository extends BaseRepository<Lord> {

    @EntityGraph(attributePaths = {"planets"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT l FROM Lord l WHERE l.id=:id")
    Optional<Lord> getWithPlanets(int id);

    @Query(value = "SELECT l.* FROM LORD l LEFT JOIN PLANET p ON l.ID = p.LORD_ID WHERE p.ID IS NULL", nativeQuery = true)
    List<Lord> getIdle();

    @Query(value = "SELECT TOP 10 * FROM Lord ORDER BY age", nativeQuery = true)
    List<Lord> getYoung();
}
