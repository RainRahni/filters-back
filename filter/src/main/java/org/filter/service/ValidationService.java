package org.filter.service;

import org.filter.dto.FilterDto;

public interface ValidationService {
    void validateFilterCreation(FilterDto filterDto);
}
