package com.core.database.model;


import javax.persistence.*;

@SqlResultSetMapping(
        name="CardReadyResult",
        entities={
                @EntityResult(
                        entityClass = ViewTransaksi.class,
                        fields={
                                @FieldResult(name="id_card_stock",column="id_card_stock"),
                                @FieldResult(name="id_card",column="id_card"),
                                @FieldResult(name="expired_at",column="expired_at"),
                                @FieldResult(name="cvv", column="cvv")}
                )
        }
)

@Entity
@Table(name = "view_ready_card")
public class ViewReadyCard {
    @Id
    private int id_card_stock;
    private int id_card;
    private String expired_at;
    private String cvv;

    public ViewReadyCard() {
    }

    public int getId_card_stock() {
        return id_card_stock;
    }

    public void setId_card_stock(int id_card_stock) {
        this.id_card_stock = id_card_stock;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
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
}
