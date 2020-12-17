package com.core.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_nasabah")
public class Nasabah {
    @Id
    private int id_nasabah;

    private String no_rekening;
    private Date created_at;
    private String status;

    public Nasabah() {
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_nasabah() {
        return id_nasabah;
    }

    public void setId_nasabah(int id_nasabah) {
        this.id_nasabah = id_nasabah;
    }
}
