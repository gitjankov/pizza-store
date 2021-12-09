package com.example.pizzastore.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LowerCaseValidation implements ConstraintValidator<LowerCase, String> {

    public void initialize(LowerCase constraintAnnotation) {
        //nothing to do
    }

    public boolean isValid(String object,
                           ConstraintValidatorContext constraintContext) {

        if (object == null)
            return true;

        return object.equals(object.toLowerCase());
    }
}
