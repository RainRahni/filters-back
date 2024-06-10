package org.filter.service;

import jakarta.validation.ValidationException;
import org.filter.config.Constants;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.mapper.CriteriaMapperImpl;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.model.enums.CriteriaType;
import org.filter.repository.FilterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private FilterRepository filterRepository;
    @Mock
    private CriteriaMapperImpl criteriaMapper;
    @InjectMocks
    private ValidationServiceImpl validationService;
    @Test
    void Should_ThrowException_When_InvalidName() {
        CriteriaDto criteriaDto = new CriteriaDto("Amount", "More", "4");
        FilterDto filterDto = new FilterDto("", List.of(criteriaDto));

        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }

    @Test
    void Should_ThrowException_When_EmptyCriteria() {
        CriteriaDto criteriaDto = new CriteriaDto("", "More", "4");
        FilterDto filterDto = new FilterDto("Test", List.of(criteriaDto));

        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }
    @Test
    void Should_ThrowException_When_InvalidCriteriaType() {
        CriteriaDto criteriaDto = new CriteriaDto("DressCode", "Can be", "Black");
        FilterDto filterDto = new FilterDto("Test", List.of(criteriaDto));

        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }
    @Test
    void Should_ThrowException_When_InvalidCriteriaParameter() {
        CriteriaDto criteriaDto = new CriteriaDto("AMOUNT", "More", "Black");
        FilterDto filterDto = new FilterDto("Test", List.of(criteriaDto));

        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }
    @Test
    void Should_ThrowException_When_NotEnoughCriterias() {
        List<CriteriaDto> criteriaDtoList = new ArrayList<>();
        for (int i = 0; i < Constants.MINIMUM_REQUIRED_CRITERIAS - 1; i++) {
            CriteriaDto criteriaDto = new CriteriaDto("Amount", "More", String.format("%d", i));
            criteriaDtoList.add(criteriaDto);
        }
        FilterDto filterDto = new FilterDto("Test", criteriaDtoList);

        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }
    @Test
    void Should_ThrowException_When_NotUniqueName() {
        CriteriaDto criteriaDto = new CriteriaDto("AMOUNT", "More", "4");
        FilterDto filterDto = new FilterDto("Test", List.of(criteriaDto));
        when(filterRepository.existsByName("Test")).thenReturn(true);
        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }
    @Test
    void Should_ThrowException_When_NotUniqueCriteria() {
        CriteriaDto criteriaDto = new CriteriaDto("AMOUNT", "More", "4");
        FilterDto filterDto = new FilterDto("Test", List.of(criteriaDto));

        Criteria criteria = Criteria.builder()
                .type(CriteriaType.AMOUNT)
                .comparator("More")
                .metric("4")
                .build();
        Filter filter = Filter.builder()
                .name("ExistingFilter")
                .criterias(List.of(criteria))
                .build();
        when(filterRepository.findAll()).thenReturn(List.of(filter));

        assertThrows(ValidationException.class, () -> validationService.validateFilterCreation(filterDto));
    }
}