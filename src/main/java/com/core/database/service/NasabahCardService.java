package com.core.database.service;

import com.core.database.model.Nasabah;
import com.core.database.model.NasabahCard;
import com.core.database.model.RegisterNasabah;
import com.core.database.repositories.NasabahCardDao;
import com.core.database.repositories.NasabahDao;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class NasabahCardService {
    private NasabahCardDao nasabahCardDao;
    private EntityManager entityManager;

    public NasabahCardService() {
    }

    public void connectJPA() {
        this.entityManager = Persistence
                .createEntityManagerFactory("core-banking-api")
                .createEntityManager();
        nasabahCardDao = new NasabahCardDao(entityManager);
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

    public int addNasabahCard(int id_nasabah,String nasabahData) {
        RegisterNasabah nasabah = new Gson().fromJson(nasabahData,RegisterNasabah.class);
        connectJPA();
        NasabahCard nasabahCard = new NasabahCard();
        nasabahCard.setId_nasabah(id_nasabah);
        nasabahCard.setRequest_card(nasabah.getRequest_card());
        int res = nasabahCardDao.addNasabahCard(nasabahCard);
        try {
            commitJPA(entityManager);
            return res;
        } catch (Exception e) {
            System.out.println("ErrorAddNasabahCard: ");
            e.printStackTrace();
            return 0;
        }
    }
}
