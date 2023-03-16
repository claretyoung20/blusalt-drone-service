package com.blusalt.droneservice.models.constraint;


import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Young Maryclaret <claretyoung@gmail.com>
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExistsColumnValueValidator implements ConstraintValidator<ExistsColumnValue, Object> {


    @Inject
    private AppRepository appRepository;
    private Class<?> entityType;
    private String columnName;

    private String statusColumn;

    private GenericStatusConstant statusValue;

    @Override
    public void initialize(ExistsColumnValue constraintAnnotation) {
        this.entityType = constraintAnnotation.value();
        this.columnName = constraintAnnotation.columnName();
        this.statusValue = constraintAnnotation.statusValue();
        this.statusColumn = constraintAnnotation.statusColumn();
    }


    @Transactional
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return appRepository.findFirstByField(entityType, columnName, value, statusColumn, statusValue).isPresent();
    }
}
