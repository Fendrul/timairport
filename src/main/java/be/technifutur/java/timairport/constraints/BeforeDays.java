package be.technifutur.java.timairport.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//Take the parameter days so it can pass it to the class that implements the interface

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BeforeDaysValidator.class)
public @interface BeforeDays {

    String message() default "The date must be before %d days";
    int value() default 7;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
