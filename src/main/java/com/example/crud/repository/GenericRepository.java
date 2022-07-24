package com.example.crud.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    T getById(ID id);

    boolean delete(ID id);

    T update(T t);

    List<T> getAll();

    void save (T t);
}
