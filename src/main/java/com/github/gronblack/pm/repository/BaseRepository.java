package com.github.gronblack.pm.repository;

import com.github.gronblack.pm.util.validation.ValidationUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query.spel-expressions
    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} u WHERE u.id=:id")
    int delete(int id);

    default void deleteExisted(int id) {
        ValidationUtil.checkModification(delete(id), id);
    }

    default PageRequest createPageRequest(Map<String, String> params) {
        int PAGE_NUMBER = 0;
        int PAGE_SIZE = 10;
        String ORDER = "name";

        int pageNumber = params.containsKey("pageNumber") ? Integer.parseInt(params.get("pageNumber")) : PAGE_NUMBER;
        int pageSize = params.containsKey("pageSize") ? Integer.parseInt(params.get("pageSize")) : PAGE_SIZE;
        String order = params.getOrDefault("order", ORDER);

        return PageRequest.of(pageNumber, pageSize, Sort.by(order));
    }
}
