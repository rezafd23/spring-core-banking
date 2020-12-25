package com.core.database.repositories;

import com.core.database.model.ViewNasabahInfo;
import com.core.database.model.Nasabah;
import com.core.database.model.ViewTransaksi;
import com.core.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
//        System.out.println("isi No KTP: "+no_ktp.replace("\"",""));
        String select = "SELECT no_ktp,no_rekening,id_nasabah_card, card_no, expired_at, cvv, card_type, " +
                "card_category,card_daily_limit from view_nasabah_info WHERE no_ktp=:no_ktp";
        Query query = entityManager.createNativeQuery(select,ViewNasabahInfo.class);
        query.setParameter("no_ktp",no_ktp.replace("\"",""));
        if(query.getResultList().size()==0){
            return "0";
        }
        ViewNasabahInfo nasabahInfo = (ViewNasabahInfo) query.getResultList().get(0);
        System.out.println("isiLISTNASABAH: "+nasabahInfo.getCard_no());

        String response = new Gson().toJson(nasabahInfo);

        if (query.getResultList().size()!=0){
            System.out.println("Cek Norek 12");
            return response;
        } else {
            System.out.println("cek Norek 22");
            return "0";
        }
    }
}
