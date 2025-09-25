package com.abkschool.eduapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GradeStepValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGrade {
    String message() default "Grade must be between 0.0 and 4.0 in steps of 0.5";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
