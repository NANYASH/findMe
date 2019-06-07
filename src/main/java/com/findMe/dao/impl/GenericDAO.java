package com.findMe.dao.impl;

import com.findMe.exception.InternalServerError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public abstract class GenericDAO<T> {
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public T create(T t) throws InternalServerError {
        try {
            getEntityManager().persist(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
        return t;
    }

    public T update(T t) throws InternalServerError {
        try {
            getEntityManager().merge(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
        return t;
    }

    public T delete(long id) throws InternalServerError {
        T t;
        try {
            t = getEntityManager().find(getEntityClass(), id);
            getEntityManager().remove(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
        return t;
    }

    public T getById(long id) throws InternalServerError {
        try {
            return getEntityManager().find(getEntityClass(), id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    abstract Class<T> getEntityClass();

}
