package com.core.database.model;

public class RegisterNasabah {

    private String no_ktp;
    private int request_card;

    public RegisterNasabah() {
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public int getRequest_card() {
        return request_card;
    }

    public void setRequest_card(int request_card) {
        this.request_card = request_card;
    }
}
