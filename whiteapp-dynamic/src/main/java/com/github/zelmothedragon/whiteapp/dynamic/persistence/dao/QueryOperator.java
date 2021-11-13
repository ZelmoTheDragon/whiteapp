package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum QueryOperator {

    EQUAL("eq", OperatorPredicate::equal),
    NOT_EQUAL("neq", OperatorPredicate::notEqual),
    LIKE("li", OperatorPredicate::like),
    NOT_LIKE("nli", OperatorPredicate::notLike),
    GREATER_THAN("gt", OperatorPredicate::greaterThan),
    GREATER_THAN_OR_EQUAL("ge", OperatorPredicate::greaterThanOrEqualTo),
    LESS_THAN("lt", OperatorPredicate::lessThan),
    LESS_THAN_OR_EQUAL("le", OperatorPredicate::lessThanOrEqualTo),
    IS_NULL("nil", OperatorPredicate::isNull),
    IS_NOT_NULL("nnil", OperatorPredicate::isNotNull),
    EMPTY("", OperatorPredicate::empty);

    String symbol;

    OperatorPredicate predicate;

    public static QueryOperator of(String symbol) {
        return Stream
                .of(values())
                .filter(e -> Objects.equals(e, symbol))
                .findFirst()
                .orElse(EMPTY);
    }
}
