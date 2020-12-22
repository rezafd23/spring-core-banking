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

    public String getNasabahInfo(String no_ktp){
//        String getNasabahIndo = "SELECT a.no_rekening from tbl_nasabah a " +
//                "INNER JOIN tbl_nasabah_card b on a.id_nasabah = b.id_nasabah " +
//                "WHERE b.id_nasabah_card=:id_card and a.status='1' and b.status='1'";
        String getNasabahIndo = "SELECT a.no_rekening, a.no_ktp, c.card_no, c.expired_at, c.cvv, d.card_type, d.card_category,\n" +
                "       d.card_daily_limit\n" +
                "FROM tbl_nasabah a\n" +
                "         INNER JOIN tbl_nasabah_card b on a.id_nasabah = b.id_nasabah\n" +
                "         INNER JOIN tbl_card_stock c on b.id_card_stock = c.id_card_stock\n" +
                "         INNER JOIN tbl_card d on c.id_card = d.id_card\n" +
                "where a.no_ktp =:no_ktp and a.status='1' and b.status='1' and c.status='1' and d.status='1'\n";
        Query query = entityManager.createNativeQuery(getNasabahIndo);
        query.setParameter("no_ktp",no_ktp);
        if (query.getResultList().size()!=0){
            System.out.println("Cek Norek 12");
            return String.valueOf(query.getResultList().get(0));
        } else {
            System.out.println("cek Norek 22");
            return "0";
        }
    }
}
