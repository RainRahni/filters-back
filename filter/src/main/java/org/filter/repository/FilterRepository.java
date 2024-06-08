package org.filter.repository;

import org.filter.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    boolean existsByName(String name);
}
