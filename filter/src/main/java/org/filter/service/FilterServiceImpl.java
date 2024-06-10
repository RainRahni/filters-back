package org.filter.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.mapper.FilterMapper;
import org.filter.model.Filter;
import org.filter.repository.FilterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;
    private final ValidationServiceImpl validationService;
    private final CriteriaServiceImpl criteriaService;
    private final CriteriaMapper criteriaMapper;
    private final FilterMapper filterMapper;

    @Override
    @Transactional
    public void createNewFilter(FilterDto filterDto) {
        validationService.validateFilterCreation(filterDto);
        log.info("Create new filter");
        Filter filter = filterMapper.toFilter(filterDto);
        filterRepository.save(filter);
        criteriaService.createCriterias(filterDto.criterias(), filter);
        log.info("Saved filter");
    }

    @Override
    public List<FilterDto> readAllFilters() {
        log.info("Read All Filters");
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