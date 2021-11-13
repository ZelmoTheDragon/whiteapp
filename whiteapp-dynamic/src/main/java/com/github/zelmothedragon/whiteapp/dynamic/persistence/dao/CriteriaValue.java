package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import lombok.Value;

@Value
public class CriteriaValue<T extends Comparable<? super T>>  {

    T value;
}
