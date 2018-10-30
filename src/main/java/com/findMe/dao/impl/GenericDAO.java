package com.findMe.dao.impl;


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

    public T create(T t) {
        getEntityManager().persist(t);
        return t;
    }

    public T update(T t) {
        getEntityManager().merge(t);
        return t;
    }

    public T delete(long id) {
        T t = getEntityManager().find(getEntityClass(), id);
        getEntityManager().detach(t);
        return t;
    }

    public T findById(long id){
        return getEntityManager().find(getEntityClass(), id);
    }

    abstract Class<T> getEntityClass();

}
