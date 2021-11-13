package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import com.github.zelmothedragon.whiteapp.dynamic.persistence.entity.Identifiable;
import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

@FunctionalInterface
public interface QuerySelection<T, K extends Serializable, E extends Identifiable<K>> {

    Selection<T> apply(CriteriaBuilder builder, CriteriaQuery<T> query, Root<E> root);

    static <T, K extends Serializable, E extends Identifiable<K>> QuerySelection<T, K, E> select() {
        return (b, q, r) -> q.select(r);
    }

    static <T, K extends Serializable, E extends Identifiable<K>> QuerySelection<T, K, E> count() {
        return (b, q, r) -> q.select(b.count(r));
    }
}
