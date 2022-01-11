package pl.edu.pw.passwordmanager.security.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
//        boolean hasUppercase = !password.equals(password.toLowerCase());
//        boolean hasLowercase = !password.equals(password.toUpperCase());
//        boolean isAtLeast8 = password.length() >= 8;
//        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");
//
//        boolean isValid = true;
//
//        if (!hasUppercase || !hasLowercase || !isAtLeast8 || !hasSpecial) {
//            isValid = false;
//            context.disableDefaultConstraintViolation();
//            String message = "Provided password: \n";
//            if (!hasUppercase) {
////                context.buildConstraintViolationWithTemplate(message.toString()).addConstraintViolation();
//                message += "-Does not contain upper case letter\n";
//            }
//            if (!hasLowercase) {
//                message += "-Does not contain lower case letter\n";
//            }
//            if (!isAtLeast8) {
//                message += "-Is not at least 8 letters long\n";
//            }
//            if (!hasSpecial) {
//                message += "-Does not contain special character";
//            }
//
//            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//        }
//
//        return isValid;
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(3),
                new SpecialCharacterRule(1),
                new AlphabeticalSequenceRule(3, false),
                new WhitespaceRule()
        ));
        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }
}
