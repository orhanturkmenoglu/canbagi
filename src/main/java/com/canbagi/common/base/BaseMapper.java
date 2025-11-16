package com.canbagi.common.base;

public interface BaseMapper <D,E>{
    D toDto(E e);
    E toEntity(D d);
}
