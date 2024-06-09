package org.filter.service;

import org.filter.dto.CriteriaDto;
import org.filter.mapper.CriteriaMapper;
import org.filter.mapper.CriteriaMapperImpl;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.model.enums.CriteriaType;
import org.filter.repository.CriteriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriteriaServiceImplTest {
    @Mock
    private CriteriaRepository criteriaRepository;
    @Mock
    private CriteriaMapper criteriaMapper = new CriteriaMapperImpl();
    @InjectMocks
    private CriteriaServiceImpl criteriaService;

    @Test
    void Should_SaveCriterias_When_CorrectInput() {
        List<CriteriaDto> dtos = new ArrayList<>();
        Filter filter = new Filter();
        CriteriaDto criteriaDto = new CriteriaDto("Amount", "More", "4");
        dtos.add(criteriaDto);
        Criteria criteria = new Criteria(1L, CriteriaType.AMOUNT, "More", "4", filter);
        when(criteriaMapper.toCriteriaList(dtos)).thenReturn(List.of(criteria));
        when(criteriaRepository.save(any(Criteria.class))).thenReturn(criteria);

        criteriaService.createCriterias(dtos, filter);

        verify(criteriaRepository, times(1)).save(criteria);
        verify(criteriaMapper, times(1)).toCriteriaList(dtos);
        assertEquals(filter, criteria.getFilter());
    }
}