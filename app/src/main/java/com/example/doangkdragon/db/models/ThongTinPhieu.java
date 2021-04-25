package com.example.doangkdragon.db.models;

public class ThongTinPhieu {
    private int maPhieu;
    private int maMon;
    private int soBai;

    public ThongTinPhieu(int maPhieu, int maMon, int soBai) {
        this.maPhieu = maPhieu;
        this.maMon = maMon;
        this.soBai = soBai;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getSoBai() {
        return soBai;
    }

    public void setSoBai(int soBai) {
        this.soBai = soBai;
    }
}
