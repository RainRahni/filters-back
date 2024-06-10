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

        boolean isEnoughCriterias = filterDto.criterias().size() >= Constants.MINIMUM_REQUIRED_CRITERIAS;
        if (isEnoughCriterias) {
            for (CriteriaDto criteriaDtos: filterDto.criterias()) {
                isInvalidCriteria = validateCriteriaDto(criteriaDtos);
            }
        }
        if (isInvalidFilterName || isInvalidCriteria || !isEnoughCriterias || !isUniqueFilter(filterDto)) {
            throw new ValidationException(Constants.INVALID_INPUT_MESSAGE);
        }
    }
    private boolean validateCriteriaDto(CriteriaDto criteriaDto) {
        log.info("Validating criteria dto: {}", criteriaDto);
        boolean isSomethingEmpty = criteriaDto.type().isEmpty() ||
                criteriaDto.comparator().isEmpty() ||
                criteriaDto.metric().isEmpty();

        boolean isCorrectType = Arrays.stream(CriteriaType.values())
                .anyMatch(criteriaType -> criteriaType.name().equalsIgnoreCase(criteriaDto.type()));

        if (isSomethingEmpty || !isCorrectType) {
            return true;
        }
        for (String type: Constants.TYPES) {
            Class<?> expectedClass = Constants.TYPE_METRIC_MAP.get(type);
            Class<?> actualClass = getClassForParameter(criteriaDto.metric());
            if (expectedClass.isAssignableFrom(actualClass)) {
                return false;
            }
        }
        return true;
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
        List<Criteria> newCriterias = criteriaMapperImpl.toCriteriaList(filterDto.criterias());
        boolean isUniqueCriterias = true;
        for (Filter existingFilter : existingFilters) {
            log.info("checking existing criterias in database: {} and new filter criterias: {}",
                    existingFilter.getCriterias(), newCriterias);
            if (new HashSet<>(newCriterias).equals(new HashSet<>(existingFilter.getCriterias()))) {
                isUniqueCriterias = false;
                break;
            }
        }
        return isUniqueCriterias && !isNamePresent;
    }
}
