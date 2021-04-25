package com.example.doangkdragon.db.models;

import java.io.Serializable;
import java.sql.Date;

public class Phieu implements Serializable {
    private int maPhieu;
    private String ngay;
    private int maGv;

    public Phieu(int maPhieu, String ngay, int maGv) {
        this.maPhieu = maPhieu;
        this.ngay = ngay;
        this.maGv = maGv;
    }

    public Phieu(String ngay, int maGv) {
        this.ngay = ngay;
        this.maGv = maGv;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getMaGv() {
        return maGv;
    }

    public void setMaGv(int maGv) {
        this.maGv = maGv;
    }
}
