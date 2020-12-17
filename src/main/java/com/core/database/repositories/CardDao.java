package com.core.database.repositories;

import com.core.database.model.Card;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CardDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public CardDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

}
