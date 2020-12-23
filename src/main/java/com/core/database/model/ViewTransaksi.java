package com.core.database.model;

import javax.persistence.*;

@SqlResultSetMapping(
        name="MutasiResult",
        entities={
                @EntityResult(
                        entityClass = ViewTransaksi.class,
                        fields={@FieldResult(name="no_rekening", column="no_rekening"),
                                @FieldResult(name="nama_transaksi", column="nama_transaksi"),
                                @FieldResult(name="nominal", column="nominal"),
                                @FieldResult(name="status_transaksi", column="status_transaksi"),
                                @FieldResult(name="card_no", column="card_no"),
                                @FieldResult(name="tgl_transaksi", column="tgl_transaksi")}
                )
        }
)



//@NamedNativeQuery(
//        name="MutasiQuery",
//        query="SELECT no_rekening,nama_transaksi,nominal,status_transaksi,card_no,tgl_transaksi from view_transaksi where no_rekening='000000000029' and tgl_transaksi BETWEEN '2020-11-11' AND '2020-12-12'",
//        resultSetMapping = "MutasiResult"
//)

@Entity
@Table(name ="view_transaksi" )
public class ViewTransaksi {
    @Id
    private String no_rekening;
    private String nama_transaksi;
    private int nominal;
    private String status_transaksi;
    private String card_no;
    private String tgl_transaksi;



    public ViewTransaksi() {
    }

    public ViewTransaksi(String no_rekening, String nama_transaksi, int nominal, String status_transaksi, String card_no, String tgl_transaksi) {
        this.no_rekening = no_rekening;
        this.nama_transaksi = nama_transaksi;
        this.nominal = nominal;
        this.status_transaksi = status_transaksi;
        this.card_no = card_no;
        this.tgl_transaksi = tgl_transaksi;
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

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }



}
