package com.core.database.repositories;

import com.core.database.model.Nasabah;
import com.core.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NasabahDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public NasabahDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public int addNasabah(Nasabah nasabah) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        nasabah.setNo_rekening(Util.geneateRekening());
        nasabah.setStatus("1");
        nasabah.setCreated_at(dtf.format(now));
        entityManager.persist(nasabah);
        return nasabah.getId_nasabah();
    }
}
