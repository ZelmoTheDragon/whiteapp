package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import lombok.Value;

@Value
public class QueryPredicate {

    String attributeName;

    Object value;

    QueryOperator operator;

}
