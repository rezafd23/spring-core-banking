package com.core.database.service;

import com.core.database.model.Nasabah;
import com.core.database.model.NasabahCard;
import com.core.database.model.RegisterNasabah;
import com.core.database.repositories.NasabahDao;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class NasabahService {
    private NasabahDao nasabahDao;
    private EntityManager entityManager;
    private NasabahCardService nasabahCardService = new NasabahCardService();

    public NasabahService() {
    }

    public void connectJPA() {
        this.entityManager = Persistence
                .createEntityManagerFactory("core-banking-api")
                .createEntityManager();
        nasabahDao = new NasabahDao(entityManager);
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

    public int addNasabah(String register) {
        RegisterNasabah nasabah = new Gson().fromJson(register,RegisterNasabah.class);
        connectJPA();
        Nasabah nasabah1= new Nasabah();
        nasabah1.setNo_ktp(nasabah.getNo_ktp());
        int res = nasabahDao.addNasabah(nasabah1);
        NasabahCard nasabahCard = new NasabahCard();
        nasabahCard.setRequest_card(nasabah.getRequest_card());
        nasabahCard.setId_nasabah(res);
//        int res2 = nasabahCardService.addNasabahCard(nasabahCard);
        System.out.println("isi RES: "+res);
//        System.out.println("PowerService1_1: " + res);
        try {
            commitJPA(entityManager);
            return res;
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("PowerService1_2");
            return 0;
        }
    }
}
