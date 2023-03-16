package com.blusalt.droneservice.models.constraint;

import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface AppRepository {

    @Transactional(readOnly = true)
    <E> Optional<E> findFirstByField(Class<E> type, String columnName, Object value, String statusColumn, GenericStatusConstant statusValue);

}
