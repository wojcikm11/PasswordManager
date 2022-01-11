package pl.edu.pw.passwordmanager.security.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final Object firstFieldValue = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
        final Object secondFieldValue = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
        return firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
    }
}
