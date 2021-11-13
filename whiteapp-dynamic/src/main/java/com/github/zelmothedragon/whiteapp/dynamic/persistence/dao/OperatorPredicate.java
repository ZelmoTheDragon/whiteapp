package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface OperatorPredicate {

    Predicate apply(CriteriaBuilder builder, Expression<?> expression, Object value);

    static Predicate equal(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        return builder.equal(expression, value);
    }

    static Predicate notEqual(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        return builder.notEqual(expression, value);
    }

    static Predicate like(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        var stringExpression = expression.as(String.class);
        var stringValue = (String) value;

        return builder.like(stringExpression, stringValue);
    }

    static Predicate notLike(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        var stringExpression = expression.as(String.class);
        var stringValue = (String) value;

        return builder.notLike(stringExpression, stringValue);
    }

    static Predicate greaterThan(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        var comparableExpression = expression.as(Comparable.class);
        var comparableValue = (Comparable) value;

        return builder.greaterThan(comparableExpression, comparableValue);
    }

    static Predicate greaterThanOrEqualTo(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        var comparableExpression = expression.as(Comparable.class);
        var comparableValue = (Comparable) value;

        return builder.greaterThanOrEqualTo(comparableExpression, comparableValue);
    }

    static Predicate lessThan(
            CriteriaBuilder builder,
            Expression<?> expression,
            Object value) {

        var comparableExpression = expression.as(Comparable.class);
        var comparableValue = (Comparable) value;

        return builder.lessThan(comparableExpression, comparableValue);
    }

    static Predicate lessThanOrEqualTo(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        var comparableExpression = expression.as(Comparable.class);
        var comparableValue = (Comparable) value;

        return builder.lessThanOrEqualTo(comparableExpression, comparableValue);
    }

    static Predicate isNull(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        return builder.isNull(expression);
    }

    static Predicate isNotNull(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        return builder.isNotNull(expression);
    }

    static Predicate empty(
            final CriteriaBuilder builder,
            final Expression<?> expression,
            final Object value) {

        return builder.and();
    }

}
