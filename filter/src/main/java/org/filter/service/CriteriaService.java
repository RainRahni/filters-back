package org.filter.service;

import org.filter.dto.CriteriaDto;
import org.filter.model.Filter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CriteriaService {
    void createCriterias(List<CriteriaDto> criteriaDtos, Filter filter);
}
