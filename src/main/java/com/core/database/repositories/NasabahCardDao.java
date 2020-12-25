package com.core.database.repositories;

import com.core.database.model.Nasabah;
import com.core.database.model.NasabahCard;
import com.core.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NasabahCardDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public NasabahCardDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }
    public int addNasabahCard(NasabahCard nasabahCard) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        nasabahCard.setStatus("1");
        String queryInsert="INSERT INTO tbl_nasabah_card (id_nasabah,id_card_stock,request_card) VALUES(:id_nasabah,:id_card_stock,:request_card)";
        Query query = entityManager.createNativeQuery(queryInsert);
        query.setParameter("id_nasabah",nasabahCard.getId_nasabah());
        query.setParameter("id_card_stock",nasabahCard.getId_card_stock());
        query.setParameter("request_card",nasabahCard.getRequest_card());
//        nasabahCard.setCreated_at(dtf.format(now));
//        entityManager.persist(nasabahCard);
        return query.executeUpdate();
    }

}
