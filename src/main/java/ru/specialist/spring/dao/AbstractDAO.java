package ru.specialist.spring.dao;

import java.util.List;

public interface AbstractDAO<T> {

    //CRUD - Create Read Update Delete

    void create(T data);

    List<T> getAll();

    T getById(long id);

    void update(long id, T data);

    void delete(long id);


}
