package be.technifutur.java.timairport.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BeforeDaysValidator implements ConstraintValidator<BeforeDays, LocalDate> {
    private BeforeDays constraint;

    @Override
    public void initialize(BeforeDays constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if (value.isBefore(LocalDate.now().minusDays(constraint.value()))) {
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(String.format("The date must be before %d days", constraint.value()))
                    .addConstraintViolation();

            return true;
        }

        return false;
    }
}
