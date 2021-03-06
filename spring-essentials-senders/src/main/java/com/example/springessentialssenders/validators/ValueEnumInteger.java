package com.example.springessentialssenders.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueEnumValidatorInteger.class)
public @interface ValueEnumInteger {
    Class<? extends Enum> classeEnum();

    String message() default "Inform only the numbers 1 (user) or 2 (admin)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
