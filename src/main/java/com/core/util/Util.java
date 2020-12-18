package com.core.util;

import com.core.database.repositories.NasabahCardDao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Util {
    public static String geneateRekening() {
        int n = 4;
        String AlphaNumericString = "0123456789";

        StringBuilder sb = new StringBuilder(n);
        sb.append("00000000");

        for (int i = 0; i < n; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static String generateUniqueCode(int length,String prefix) {
        int n = length;
        String AlphaNumericString = "abcdefghijklmnopqrstuvwxyz"
                +"0123456789";

        StringBuilder sb = new StringBuilder(n);
        sb.append(prefix);

        for (int i = 0; i < n; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

//    public static void connectJPA(EntityManager entityManager, Class aClass) {
//        entityManager = Persistence
//                .createEntityManagerFactory("core-banking-api")
//                .createEntityManager();
////        nasabahCardDao = new NasabahCardDao(entityManager);
//        try {
//            entityManager.getTransaction().begin();
//        } catch (IllegalStateException e) {
//            entityManager.getTransaction().rollback();
//        }
//    }
}
