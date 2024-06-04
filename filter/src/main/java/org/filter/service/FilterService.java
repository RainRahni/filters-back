package org.filter.service;

import org.filter.dto.FilterCreationDto;
import org.springframework.stereotype.Service;

@Service
public interface FilterService {
    void createNewFilter(FilterCreationDto filterCreationDto);
}
