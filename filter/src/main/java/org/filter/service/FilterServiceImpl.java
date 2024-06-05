package org.filter.service;

import lombok.RequiredArgsConstructor;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.repository.CriteriaRepository;
import org.filter.repository.FilterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;
    private final CriteriaRepository criteriaRepository;
    private final CriteriaMapper criteriaMapper;

    @Override
    public void createNewFilter(FilterDto filterDto) {
        Filter filter = new Filter();
        filter.setName(filterDto.filterName());
        List<Criteria> criterias = criteriaMapper.toCriteriaList(filterDto.criterias());
        filterRepository.save(filter);
        for (Criteria criteria : criterias) {
            criteria.setFilter(filter);
            criteriaRepository.save(criteria);
        }
    }

    @Override
    public List<FilterDto> readAllFilters() {
        List<Filter> filters = filterRepository.findAll();
        List<FilterDto> filterDtos = new ArrayList<>();
        for (Filter filter : filters) {
            List<CriteriaDto> criteriaDtos = criteriaMapper.toDtoList(filter.getCriterias());
            FilterDto filterDto = new FilterDto(filter.getName(), criteriaDtos);
            filterDtos.add(filterDto);
        }
        return filterDtos;
    }
}