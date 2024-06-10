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
    public void createCriteria(List<CriteriaDto> criteriaDtos, Filter filter) {
        List<Criteria> criteria = criteriaMapper.toCriteriaList(criteriaDtos);
        for (Criteria criterion : criteria) {
            criterion.setFilter(filter);
            criteriaRepository.save(criterion);
        }
        log.info("Saved criteria");
    }
}
