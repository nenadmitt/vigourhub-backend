package com.vigourhub.backend.infrastructure.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UuidValidator implements ConstraintValidator<ValidUUID, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (s == null) {
                return false;
            }
            UUID.fromString(s);
            return true;
        }catch(IllegalArgumentException ex) {
            return false;
        }
    }
}
