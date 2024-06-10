package org.filter.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filter.config.Constants;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.mapper.CriteriaMapperImpl;
import org.filter.model.Criteria;
import org.filter.model.Filter;
import org.filter.model.enums.CriteriaType;
import org.filter.repository.FilterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationServiceImpl implements ValidationService {
    private final FilterRepository filterRepository;
    private final CriteriaMapperImpl criteriaMapperImpl;

    @Override
    public void validateFilterCreation(FilterDto filterDto) {
        log.info("Validating filter creation");
        boolean isInvalidFilterName = filterDto.name().isEmpty();
        boolean isInvalidCriteria = false;

        boolean isEnoughCriteria = filterDto.criteria().size() >= Constants.MINIMUM_REQUIRED_CRITERIA;
        if (isEnoughCriteria) {
            for (CriteriaDto criteriaDto: filterDto.criteria()) {
                isInvalidCriteria = validateCriteriaDto(criteriaDto);
                if (!isInvalidCriteria) {
                    break;
                }
            }
        }
        if (isInvalidFilterName || isInvalidCriteria || !isEnoughCriteria || !isUniqueFilter(filterDto)) {
            throw new ValidationException(Constants.INVALID_INPUT_MESSAGE);
        }
    }
    private boolean validateCriteriaDto(CriteriaDto criteriaDto) {
        log.info("Validating criteria dto: {}", criteriaDto);
        boolean isSomethingEmpty = criteriaDto.type().isEmpty() ||
                criteriaDto.condition().isEmpty() ||
                criteriaDto.metric().isEmpty();

        String criteriaTypeUpper = criteriaDto.type().toUpperCase();
        boolean isCorrectType = Arrays.stream(CriteriaType.values())
                .anyMatch(criteriaType -> criteriaType.name().equals(criteriaTypeUpper));

        if (isSomethingEmpty || !isCorrectType) {
            return true;
        }
        Class<?> expectedClass = Constants.TYPE_METRIC_MAP.get(criteriaTypeUpper);
        Class<?> actualClass = getClassForParameter(criteriaDto.metric());
        return !expectedClass.isAssignableFrom(actualClass);
    }
    private Class<?> getClassForParameter(String parameter) {
        log.info("Validating parameter: {}", parameter);
        if (parameter.matches(Constants.NUMBER_PATTERN)) {
            return Number.class;
        } else if (parameter.matches(Constants.DATE_PATTERN)) {
            return LocalDate.class;
        }
        return String.class;
    }
    private boolean isUniqueFilter(FilterDto filterDto) {
        boolean isNamePresent = filterRepository.existsByName(filterDto.name());
        List<Filter> existingFilters = filterRepository.findAll();
        List<Criteria> newCriteria = criteriaMapperImpl.toCriteriaList(filterDto.criteria());
        boolean isUniqueCriteria = true;
        for (Filter existingFilter : existingFilters) {
            log.info("Checking existing criteria in database: {} and new filter criteria: {}",
                    existingFilter.getCriteria(), newCriteria);
            if (new HashSet<>(newCriteria).equals(new HashSet<>(existingFilter.getCriteria()))) {
                isUniqueCriteria = false;
                break;
            }
        }
        return isUniqueCriteria && !isNamePresent;
    }
}
