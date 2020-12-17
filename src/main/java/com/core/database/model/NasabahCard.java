package com.core.database.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name = "tbl_nasabah_card")
public class NasabahCard {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_nasabah_card", nullable = true, unique = true)
    @Id
    private int id_nasabah_card;

    private int id_card_stock;
    private int id_nasabah;
    private int request_card;
    private String created_at;
    private String status;

    public int getId_card_stock() {
        return id_card_stock;
    }

    public void setId_card_stock(int id_card_stock) {
        this.id_card_stock = id_card_stock;
    }

    public int getRequest_card() {
        return request_card;
    }

    public void setRequest_card(int request_card) {
        this.request_card = request_card;
    }

    public int getId_nasabah() {
        return id_nasabah;
    }

    public void setId_nasabah(int id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
