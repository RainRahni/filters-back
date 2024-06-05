package org.filter.service;

import org.filter.model.Criteria;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CriteriaValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Criteria.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
