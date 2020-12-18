package com.core.database.service;

import com.core.database.model.*;
import com.core.database.repositories.NasabahCardDao;
import com.core.database.repositories.TransaksiDao;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class TransaksiService {
    private TransaksiDao transaksiDao;
    private EntityManager entityManager;

    public TransaksiService() {
    }

    public void connectJPA() {
        this.entityManager = Persistence
                .createEntityManagerFactory("core-banking-api")
                .createEntityManager();
        transaksiDao = new TransaksiDao(entityManager);
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

    public String addTransaksi(String transaksiData) {
        String res="0";
        Transaksi transaksi = new Gson().fromJson(transaksiData,Transaksi.class);
        connectJPA();
        if (transaksi.getStatus_transaksi().equals("debit")){
            String norek=transaksiDao.getNorek(transaksi.getId_nasabah_card());
            if (!norek.equals("0")){
                int saldo = transaksiDao.checkSaldoId(norek);
                if (saldo!=0&&saldo>transaksi.getNominal()){
                    res = transaksiDao.addTransaksi(transaksi);
                } else {
                    res="2";
                    return res;
                }
            } else {
                return res;
            }
        } else {
         res = transaksiDao.addTransaksi(transaksi);
        }
//        String saldo = transaksiDao.checkSaldoId()
        try {
            commitJPA(entityManager);
            return res;
        } catch (Exception e) {
            System.out.println("ErrorAddTransaksi: ");
            e.printStackTrace();
            return "0";
        }
    }

    public String mutasi(String transaksiData) {
        String res="0";
        MutasiParams transaksi = new Gson().fromJson(transaksiData,MutasiParams.class);
        connectJPA();
        res =transaksiDao.mutasi(transaksi);
//        String saldo = transaksiDao.checkSaldoId()
        try {
            commitJPA(entityManager);
            return res;
        } catch (Exception e) {
            System.out.println("ErrorAddTransaksi: ");
            e.printStackTrace();
            return "0";
        }
    }
}
