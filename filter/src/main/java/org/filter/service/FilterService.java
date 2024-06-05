package org.filter.service;

import org.filter.dto.FilterDto;
import org.filter.model.Filter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilterService {
    void createNewFilter(FilterDto filterDto);
    List<FilterDto> readAllFilters();
}
