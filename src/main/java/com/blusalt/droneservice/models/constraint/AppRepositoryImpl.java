package com.blusalt.droneservice.models.constraint;

import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;


@Repository
class AppRepositoryImpl implements AppRepository {

    private final Logger log = LoggerFactory.getLogger(AppRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public <E> Optional<E> findFirstByField(Class<E> type, String columnName, Object value, String statusColumn, GenericStatusConstant statusValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<E> from = criteriaQuery.from(type);
        criteriaQuery.select(from);


        if(!statusColumn.isEmpty()) {

            Predicate predicateForColumnName
                    = criteriaBuilder.equal(from.get(columnName), value);

            Predicate predicateForStatus
                    = criteriaBuilder.equal(from.get(statusColumn), statusValue);

            Predicate predicate
                    = criteriaBuilder.and(predicateForColumnName, predicateForStatus);

                criteriaQuery.where(predicate);
        } else{
            criteriaQuery.where(criteriaBuilder.equal(from.get(columnName), value));
        }

        try {
           return Optional.ofNullable(entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult());
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    private static String firstCharToLowerCase(String str) {

        if(str == null || str.length() == 0)
            return "";

        if(str.length() == 1)
            return str.toLowerCase();

        char[] chArr = str.toCharArray();
        chArr[0] = Character.toLowerCase(chArr[0]);

        return new String(chArr);
    }

}
