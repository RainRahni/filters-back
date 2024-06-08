package org.filter.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.filter.config.Constants;
import org.filter.dto.CriteriaDto;
import org.filter.dto.FilterDto;
import org.filter.model.enums.CriteriaType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateFilterCreation(FilterDto filterDto) {
        boolean isInvalidFilterName = filterDto.filterName().isEmpty();
        boolean isInvalidCriteria = false;
        for (CriteriaDto criteriaDtos: filterDto.criterias()) {
            isInvalidCriteria = validateCriteriaDto(criteriaDtos);
        }
        if (isInvalidFilterName || isInvalidCriteria) {
            throw new ValidationException(Constants.INVALID_INPUT_MESSAGE);
        }
    }
    private boolean validateCriteriaDto(CriteriaDto criteriaDto) {
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
            if (!expectedClass.isAssignableFrom(actualClass)) {
                return true;
            }
        }
        return false;
    }
    private Class<?> getClassForParameter(String parameter) {
        if (parameter.matches(Constants.NUMBER_PATTERN)) {
            return Number.class;
        } else if (parameter.matches(Constants.DATE_PATTERN)) {
            return LocalDate.class;
        }
        return String.class;
    }
}
