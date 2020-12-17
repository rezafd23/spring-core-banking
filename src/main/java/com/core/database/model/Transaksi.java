package com.core.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_transaksi")
public class Transaksi {
    @Id
    private int id_transksi;

    private String nama_transaksi;
    private int nominal;
    private String status_transaksi;
    private String created_at;
    private String status;
    private int id_nasabah_card;

    public Transaksi() {
    }

    public int getId_transksi() {
        return id_transksi;
    }

    public void setId_transksi(int id_transksi) {
        this.id_transksi = id_transksi;
    }

    public String getNama_transaksi() {
        return nama_transaksi;
    }

    public void setNama_transaksi(String nama_transaksi) {
        this.nama_transaksi = nama_transaksi;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
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

    public int getId_nasabah_card() {
        return id_nasabah_card;
    }

    public void setId_nasabah_card(int id_nasabah_card) {
        this.id_nasabah_card = id_nasabah_card;
    }
}
