package com.core.database.service;

import com.core.database.model.MutasiParams;
import com.core.database.repositories.CardDao;
import com.core.database.repositories.TransaksiDao;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class CardService {

    private CardDao cardDao;
    private EntityManager entityManager;

    public CardService() {
    }

    public void connectJPA() {
        this.entityManager = Persistence
                .createEntityManagerFactory("core-banking-api")
                .createEntityManager();
        cardDao = new CardDao(entityManager);
        try {
            entityManager.getTransaction().begin();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void commitJPA(EntityManager entity) {
        try {
            entity.getTransaction().commit();
            entity.close();
        } catch (IllegalStateException e) {
            entity.getTransaction().rollback();
        }
    }

    public int getCardReady() {
        int res=0;
        connectJPA();
        res =cardDao.getReadyCard();
        try {
            commitJPA(entityManager);
            return res;
        } catch (Exception e) {
            System.out.println("Error Get Card Ready: ");
            e.printStackTrace();
            return res;
        }
    }
}
