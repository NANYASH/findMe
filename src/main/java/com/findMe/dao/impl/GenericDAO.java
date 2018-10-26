package com.findMe.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class GenericDAO<T> {
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public T save(T t) {
        getEntityManager().persist(t);
        return t;
    }

    public T update(T t) {
        getEntityManager().merge(t);
        return t;
    }

    public T delete(Class<T> c,long id) {
        T t = getEntityManager().find(c, id);
        getEntityManager().detach(t);
        return t;
    }

    public T findById(Class<T> c,long id){
        return getEntityManager().find(c, id);
    }

}
