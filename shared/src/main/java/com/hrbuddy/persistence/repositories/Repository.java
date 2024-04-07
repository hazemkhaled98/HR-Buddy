package com.hrbuddy.persistence.repositories;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public abstract class Repository<T> {

    protected Class<T> type;

    protected void setType(Class<T> type) {
        this.type = type;
    }

    protected final EntityManager entityManager;

    protected Repository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<T> get(int id ) {
        return Optional.ofNullable(entityManager.find(type, id ));
    }

    public List<T> getAll() {
        return entityManager.createQuery("from " + type.getName(), type)
                .getResultList();
    }

    public T create( T entity ) {
        entityManager.persist(entity);
        return entity;
    }

    public T update( T entity ) {
        return entityManager.merge(entity);
    }

    public void delete( T entity ) {
        entityManager.remove(entity);
    }

    public void deleteById(int entityId) {
        T entity = entityManager.find(type, entityId);
        delete(entity);
    }

}
