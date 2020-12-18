package com.core.database.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_transaksi")
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_transaksi;

    private String nama_transaksi;
    private int nominal;
    private String status_transaksi;

    private String trx_refferal_code;

    @Column(insertable = false)
    private String created_at;

    @Column(insertable = false)
    private String status;

    private int id_nasabah_card;

    public Transaksi() {
    }

    public Transaksi(String nama_transaksi,String trx_refferal_code, int nominal, String status_transaksi, int id_nasabah_card) {
        this.nama_transaksi = nama_transaksi;
        this.trx_refferal_code = trx_refferal_code;
        this.nominal = nominal;
        this.status_transaksi = status_transaksi;
        this.id_nasabah_card = id_nasabah_card;
    }

    public String getTrx_refferal_code() {
        return trx_refferal_code;
    }

    public void setTrx_refferal_code(String trx_refferal_code) {
        this.trx_refferal_code = trx_refferal_code;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
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
