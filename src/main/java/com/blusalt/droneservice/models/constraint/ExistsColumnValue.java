package com.blusalt.droneservice.models.constraint;

import com.blusalt.droneservice.models.enums.GenericStatusConstant;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Young Maryclaret <claretyoung@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD,
        ElementType.TYPE_USE
})
@Constraint(validatedBy =  ExistsColumnValueValidator.class)
public @interface ExistsColumnValue {

    String message() default "Data do not exist";
    /**
     * The column name to check for the value
     *
     * @return
     */
    String columnName() default "id";

    String statusColumn() default "status";

    GenericStatusConstant statusValue() default GenericStatusConstant.ACTIVE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> value();

}
