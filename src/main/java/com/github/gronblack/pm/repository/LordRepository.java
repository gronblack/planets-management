package com.github.gronblack.pm.repository;

import com.github.gronblack.pm.model.Lord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LordRepository extends BaseRepository<Lord> {
    String idleCountQuery = "SELECT COUNT(*) FROM LORD l LEFT JOIN PLANET p ON l.ID = p.LORD_ID WHERE p.ID IS NULL";

    @EntityGraph(attributePaths = {"planets"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT l FROM Lord l WHERE l.id=:id")
    Optional<Lord> getWithPlanets(int id);

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query.native
    @Query(value = "SELECT l.* FROM LORD l LEFT JOIN PLANET p ON l.ID = p.LORD_ID WHERE p.ID IS NULL",
            countQuery = idleCountQuery,
            nativeQuery = true)
    List<Lord> getIdle(Pageable pageable);

    @Query(value = idleCountQuery, nativeQuery = true)
    int idleCount();

    @Query(value = "SELECT TOP 10 * FROM Lord ORDER BY age", nativeQuery = true)
    List<Lord> getYoung();
}
