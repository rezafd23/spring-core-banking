package com.core.database.model;


import com.core.database.model.ViewTransaksi;

import javax.persistence.*;

@SqlResultSetMapping(
        name="NasabahResult",
        entities={
                @EntityResult(
                        entityClass = ViewTransaksi.class,
                        fields={
                                @FieldResult(name="no_ktp",column="no_ktp"),
                                @FieldResult(name="no_rekening",column="no_rekening"),
                                @FieldResult(name="card_no", column="card_no"),
                                @FieldResult(name="expired_at", column="expired_at"),
                                @FieldResult(name="cvv", column="cvv"),
                                @FieldResult(name="card_type", column="card_type"),
                                @FieldResult(name="card_category", column="card_category"),
                                @FieldResult(name="card_daily_limit", column="card_daily_limit")}
                )
        }
)
@Entity
@Table(name = "view_nasabah_info")
public class ViewNasabahInfo {
    @Id
    private String no_ktp;

    private String no_rekening;
    private String card_no;
    private String expired_at;
    private String cvv;
    private String card_type;
    private String card_category;
    private int card_daily_limit;

    public ViewNasabahInfo() {
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_category() {
        return card_category;
    }

    public void setCard_category(String card_category) {
        this.card_category = card_category;
    }

    public int getCard_daily_limit() {
        return card_daily_limit;
    }

    public void setCard_daily_limit(int card_daily_limit) {
        this.card_daily_limit = card_daily_limit;
    }
}
