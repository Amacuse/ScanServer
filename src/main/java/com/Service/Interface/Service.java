package com.Service.Interface;

public interface Service<T> extends HeaderService {

    T get(Long id);

    void add(T t);

    void update(T t, Long id);

    void remove(Long id);

}
