package com.codeosseum.ares.security.registration.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueUsername {
    String message() default "UNIQUE_USERNAME_CONSTRAINT_VIOLATION";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
