package com.DAO.Impl;

import com.DAO.Interface.Dao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public abstract class AbstractDAO<T> implements Dao<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> clazz;

    @Override
    public void add(T ob) throws EntityExistsException {
        em.persist(ob);
    }

    @Override
    @Transactional(readOnly = true)
    public T getByID(Long id) {
        return em.find(clazz, id);
    }

    @Override
    public void update(T ob) {
        em.merge(ob);
    }

    @Override
    public void remove(T ob) {
        em.remove(em.contains(ob) ? ob : em.merge(ob));
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
