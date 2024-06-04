package org.filter.service;

import lombok.RequiredArgsConstructor;
import org.filter.dto.FilterCreationDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.repository.CriteriaRepository;
import org.filter.repository.FilterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;
    private final CriteriaRepository criteriaRepository;
    private final CriteriaMapper criteriaMapper;

    @Override
    public void createNewFilter(FilterCreationDto filterCreationDto) {
        List<Criteria> criterias = criteriaMapper.toCriteriaList(filterCreationDto.criterias());
        Filter filter = new Filter();
        filter.setName(filterCreationDto.filterName());
        filter.setCriterias(criterias);
        filterRepository.save(filter);
        for (Criteria criteria : criterias) {
            criteria.setFilter(filter);
            criteriaRepository.save(criteria);
        }

    }
}