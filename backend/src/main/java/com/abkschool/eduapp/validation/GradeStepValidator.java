package com.abkschool.eduapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GradeStepValidator implements ConstraintValidator<ValidGrade, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) return true; // nullable
        if (value < 0.0 || value > 4.0) return false;

        // /0.0, 0.5, 1.0, 1.5, ..., 4.0)
        return (value * 10) % 5 == 0;
    }
}
