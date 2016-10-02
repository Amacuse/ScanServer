package com.DAO.Interface;

public interface Dao<T> {
    void add(T ob);

    T getByID(Long id);

    void update(T ob);

    void remove(T ob);
}
