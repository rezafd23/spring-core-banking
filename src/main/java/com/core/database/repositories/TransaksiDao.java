package com.core.database.repositories;

import com.core.database.model.MutasiParams;
import com.core.database.model.NasabahCard;
import com.core.database.model.Transaksi;
import com.core.database.model.ViewTransaksi;
import com.core.util.Util;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class TransaksiDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public TransaksiDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public String addTransaksi(Transaksi transaksi) {
        String trx_code=Util.generateUniqueCode(15,"trx");
        Transaksi newTransaksi = new Transaksi(transaksi.getNama_transaksi(), trx_code,transaksi.getNominal(),
                transaksi.getStatus_transaksi(),transaksi.getId_nasabah_card());
        entityManager.persist(newTransaksi);
        return trx_code;
    }

    public int checkSaldoId(String no_rekening){
//        String select = "SELECT Saldo FROM Saldo WHERE nasabahId=:nasabahId";
        String select = "SELECT sum(nominal) as saldo from view_transaksi where " +
                "status_transaksi='kredit' AND no_rekening=:no_rekening";
        Query query = entityManager.createNativeQuery(select);
        query.setParameter("no_rekening", no_rekening);
        if (query.getResultList().size()!=0){
            System.out.println("masuk cek saldo 12");
            BigDecimal saldo= (BigDecimal)query.getResultList().get(0);
            if (saldo==null){
                return 0;
            } else {
            return Integer.parseInt(String.valueOf(saldo));
            }
        } else {
            System.out.println("masuk cek saldo 22");
            return query.getResultList().size();
        }
    }
    public String getNorek(int id_card){
        String getNorek = "SELECT a.no_rekening from tbl_nasabah a " +
                "INNER JOIN tbl_nasabah_card b on a.id_nasabah = b.id_nasabah " +
                "WHERE b.id_nasabah_card=:id_card and a.status='1' and b.status='1'";
        Query query = entityManager.createNativeQuery(getNorek);
        query.setParameter("id_card",id_card);
        if (query.getResultList().size()!=0){
            System.out.println("Cek Norek 12");
            return String.valueOf(query.getResultList().get(0));
        } else {
            System.out.println("cek Norek 22");
            return "0";
        }
    }

    public String mutasi(MutasiParams transaksi){
        String queryM="SELECT no_rekening,nama_transaksi,nominal,status_transaksi," +
                "card_no,tgl_transaksi from view_transaksi where no_rekening=:no_rekening " +
                "AND tgl_transaksi BETWEEN :start_date AND :end_date";
        Query query = entityManager.createNativeQuery(queryM,ViewTransaksi.class);
        query.setParameter("no_rekening",transaksi.getNo_rekening());
        query.setParameter("start_date",transaksi.getStart_date());
        query.setParameter("end_date",transaksi.getEnd_date());
        List<ViewTransaksi> transaksiList = query.getResultList();

//        for (int i=0;i<transaksiList.size();i++){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("nama_transaksi",transaksiList.get(0).getNama_transaksi());
//        }
        if (query.getResultList().size()!=0){
            System.out.println("Cek mutasi 12");
//            System.out.println("isi1: "+listTransaksi.get(0).getNama_transaksi());
            return String.valueOf(new Gson().toJson(transaksiList));
        } else {
            System.out.println("cek mutasi 22");
            return "0";
        }
    }

//    public String mutasi(MutasiParams transaksi){
////        private String nama_transaksi;
////        private int nominal;
////        private String status_transaksi;
////        private String card_no;
////        private String tgl_transaksi;
//
//        String mutasi = "SELECT no_rekening,nominal,status_transaksi,card_no,tgl_transaksi from ViewTransaksi where no_rekening=:no_rekening " +
//                "and tgl_transaksi BETWEEN :start_date AND :end_date";
//
////        TypedQuery<ViewTransaksi> query1 = (TypedQuery<ViewTransaksi>) entityManager.createNamedQuery(mutasi);
//        Query query = entityManager.createQuery(mutasi);
//        query.setParameter("no_rekening",transaksi.getNo_rekening());
//        query.setParameter("start_date",transaksi.getStart_date());
//        query.setParameter("end_date",transaksi.getEnd_date());
//        List<ViewTransaksi> listTransaksi =  (List<ViewTransaksi>) query.getResultList();
//
//        List listTransaksi2 = query.getResultList();
//        if (query.getResultList().size()!=0){
//            System.out.println("Cek mutasi 12");
//            System.out.println("isi1: "+listTransaksi.get(0).getNama_transaksi());
//            return String.valueOf(listTransaksi);
//        } else {
//            System.out.println("cek mutasi 22");
//            return "0";
//        }
//    }


}
