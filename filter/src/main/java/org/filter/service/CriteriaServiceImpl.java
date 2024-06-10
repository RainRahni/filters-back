package org.filter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filter.dto.CriteriaDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.repository.CriteriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {
    private final CriteriaRepository criteriaRepository;
    private final CriteriaMapper criteriaMapper;
    @Override
    public void createCriterias(List<CriteriaDto> criteriaDtos, Filter filter) {
        List<Criteria> criterias = criteriaMapper.toCriteriaList(criteriaDtos);
        for (Criteria criteria : criterias) {
            criteria.setFilter(filter);
            criteriaRepository.save(criteria);
        }
        log.info("Saved criteria");
    }
}
