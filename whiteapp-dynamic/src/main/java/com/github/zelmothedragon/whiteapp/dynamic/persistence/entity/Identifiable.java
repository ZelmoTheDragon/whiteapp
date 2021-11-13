package com.github.zelmothedragon.whiteapp.dynamic.persistence.entity;

import java.io.Serializable;

public interface Identifiable<K extends Serializable> {

    K getId();
}
