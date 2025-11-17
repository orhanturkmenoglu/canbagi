package com.canbagi.common.base;

import java.util.List;

public interface BaseMapper <D,E>{
    D toDto(E e);
    E toEntity(D d);
    List<D> toDtoList(List<E> entities);
    List<E> toEntityList(List<D> dtos);
}
