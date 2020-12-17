package com.core.util;

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
}
