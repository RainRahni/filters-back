package org.filter.service;

import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.repository.FilterRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    @InjectMocks
    private FilterServiceImpl filterService;
    void Should_SaveFilter_When_CorrectInput() {
        CriteriaDto criteriaDto = new CriteriaDto("Amount","More", "4");
        List<CriteriaDto> criteriaDtoList = new ArrayList<>();
        criteriaDtoList.add(criteriaDto);
        FilterDto filterDto = new FilterDto("TestFilter", criteriaDtoList);
        //when()

    }
}