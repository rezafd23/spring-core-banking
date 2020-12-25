package com.core.database.repositories;

import com.core.database.model.Card;
import com.core.database.model.MutasiParams;
import com.core.database.model.ViewTransaksi;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class CardDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public CardDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public int getReadyCard(){
        String queryM="SELECT id_card_stock  from ViewReadyCard";
        Query query = entityManager.createQuery(queryM);
        String res = query.getResultList().get(0).toString();
        System.out.println("isi card: "+res);

        if (Integer.parseInt(res)!=0){
            System.out.println("Cek Card 1");
//            System.out.println("isi1: "+listTransaksi.get(0).getNama_transaksi());
            return Integer.parseInt(res);
        } else {
            System.out.println("cek Card 2");
            return 0;
        }
    }

}
