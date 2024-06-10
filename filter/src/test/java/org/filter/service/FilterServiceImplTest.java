package org.filter.service;

import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.filter.config.Constants;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.mapper.FilterMapper;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.model.enums.CriteriaType;
import org.filter.repository.FilterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ValidationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilterServiceImplTest {
    @Mock
    private FilterRepository filterRepository;
    @Mock
    private ValidationServiceImpl validationService;
    @Mock
    private CriteriaServiceImpl criteriaService;
    @Mock
    private CriteriaMapper criteriaMapper;
    @Mock
    private FilterMapper filterMapper;
    @InjectMocks
    private FilterServiceImpl filterService;
    @Test
    void Should_SaveFilter_When_CorrectInput() {
        CriteriaDto criteriaDto = new CriteriaDto("Amount","More", "4");
        List<CriteriaDto> criteriaDtoList = new ArrayList<>();
        criteriaDtoList.add(criteriaDto);
        FilterDto filterDto = new FilterDto("TestFilter", criteriaDtoList);
        Criteria criteria = Criteria.builder()
                .type(CriteriaType.AMOUNT)
                .comparator("More")
                .metric("4")
                .build();
        Filter filter = Filter.builder()
                .name("TestFilter")
                .criterias(List.of(criteria))
                .build();
        criteria.setFilter(filter);

        doNothing().when(validationService).validateFilterCreation(filterDto);
        when(filterMapper.toFilter(filterDto)).thenReturn(filter);
        when(filterRepository.save(filter)).thenReturn(filter);
        doNothing().when(criteriaService).createCriterias(anyList(), any(Filter.class));

        filterService.createNewFilter(filterDto);
        String expectedName = filterDto.name();
        int expectedCriteriasSize = 1;
        Criteria expectedCriteria = criteria;

        verify(validationService, times(1)).validateFilterCreation(filterDto);
        verify(filterMapper, times(1)).toFilter(filterDto);
        verify(filterRepository, times(1)).save(filter);
        verify(criteriaService, times(1)).createCriterias(criteriaDtoList, filter);

        assertEquals(expectedName, filter.getName());
        assertEquals(expectedCriteriasSize, filter.getCriterias().size());
        assertEquals(expectedCriteria, filter.getCriterias().get(0));
    }

    @Test
    void Should_ThrowException_When_IncorrectInput() {
        CriteriaDto criteriaDto = new CriteriaDto("NoTHIN","syke", "null");
        List<CriteriaDto> criteriaDtoList = new ArrayList<>();
        criteriaDtoList.add(criteriaDto);
        FilterDto filterDto = new FilterDto("", criteriaDtoList);
        doThrow(new ValidationException(Constants.INVALID_INPUT_MESSAGE))
                .when(validationService).validateFilterCreation(filterDto);

        assertThrows(ValidationException.class, () -> filterService.createNewFilter(filterDto));
    }
    @Test
    void Should_ReturnFilters_When_MultipleFilters() {
        Criteria criteriaOne = Criteria.builder().type(CriteriaType.AMOUNT).build();
        Filter filterOne = Filter.builder()
                .name("FilterOne")
                .criterias(List.of(criteriaOne))
                .build();

        Criteria criteriaTwo = Criteria.builder().type(CriteriaType.TITLE).build();
        Filter filterTwo = Filter.builder()
                .name("FilterTwo")
                .criterias(List.of(criteriaTwo))
                .build();
        CriteriaDto criteriaDtoOne = new CriteriaDto("Amount", "More", "4");
        FilterDto expectedFilterDtoOne = new FilterDto("FilterOne", List.of(criteriaDtoOne));

        CriteriaDto criteriaDtoTwo = new CriteriaDto("Title", "Starts", "da");
        FilterDto expectedFilterDtoTwo = new FilterDto("FilterTwo", List.of(criteriaDtoTwo));

        when(filterRepository.findAll()).thenReturn(Arrays.asList(filterOne, filterTwo));
        when(criteriaMapper.toDtoList(filterOne.getCriterias())).thenReturn(List.of(criteriaDtoOne));
        when(criteriaMapper.toDtoList(filterTwo.getCriterias())).thenReturn(List.of(criteriaDtoTwo));
        List<FilterDto> result = filterService.readAllFilters();

        verify(filterRepository, times(1)).findAll();
        verify(criteriaMapper, times(2)).toDtoList(anyList());

        assertEquals(2, result.size());
        assertEquals(expectedFilterDtoOne, result.get(0));
        assertEquals(expectedFilterDtoTwo, result.get(1));
    }

    @Test
    void Should_ReturnFilters_When_SingleFilter() {
        Criteria criteriaOne = Criteria.builder().type(CriteriaType.AMOUNT).build();
        Filter filterOne = Filter.builder()
                .name("FilterOne")
                .criterias(List.of(criteriaOne))
                .build();

        CriteriaDto criteriaDtoOne = new CriteriaDto("Amount", "More", "4");
        FilterDto expectedFilterDtoOne = new FilterDto("FilterOne", List.of(criteriaDtoOne));


        when(filterRepository.findAll()).thenReturn(Collections.singletonList(filterOne));
        when(criteriaMapper.toDtoList(filterOne.getCriterias())).thenReturn(List.of(criteriaDtoOne));
        List<FilterDto> result = filterService.readAllFilters();

        verify(filterRepository, times(1)).findAll();
        verify(criteriaMapper, times(1)).toDtoList(anyList());

        assertEquals(1, result.size());
        assertEquals(expectedFilterDtoOne, result.getFirst());
    }
}