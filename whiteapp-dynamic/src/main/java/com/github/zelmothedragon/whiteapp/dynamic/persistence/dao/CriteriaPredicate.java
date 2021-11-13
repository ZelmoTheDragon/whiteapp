package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import com.github.zelmothedragon.whiteapp.dynamic.persistence.entity.Identifiable;
import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface CriteriaPredicate<K extends Serializable, E extends Identifiable<K>> {
    
    Predicate apply(CriteriaBuilder builder, CriteriaQuery<E> query, Root<E> root);
    
    default CriteriaPredicate<K, E> and(CriteriaPredicate<K, E> predicate) {
        return (b, q, r) -> b.and(predicate.apply(b, q, r));
    }
    
    default CriteriaPredicate<K, E> or(CriteriaPredicate<K, E> predicate) {
        return (b, q, r) -> b.or(predicate.apply(b, q, r));
    }
    
    static <K extends Serializable, E extends Identifiable<K>> CriteriaPredicate<K, E> empty() {
        return (b, q, r) -> b.and();
    }
}
