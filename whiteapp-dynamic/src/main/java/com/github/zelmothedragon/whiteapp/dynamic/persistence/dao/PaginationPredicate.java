package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import java.util.List;
import java.util.Map;
import lombok.Value;

@Value
public final class PaginationPredicate {

    public static final int DEFAULT_PAGE_SIZE = 100;

    public static final int DEFAULT_PAGE_NUMBER = 1;

    String keyword;

    List<QueryPredicate> predicates;

    Map<String, Boolean> orders;

    int pageSize;

    int pageNumber;

    int getFirstResult() {
        return Math.max(0, this.pageNumber - 1) * this.pageSize;
    }

    int getMaxResult() {
        return this.pageSize;
    }
}
