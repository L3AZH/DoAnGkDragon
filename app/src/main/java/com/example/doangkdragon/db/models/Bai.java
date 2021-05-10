package com.example.doangkdragon.db.models;

import java.io.Serializable;

public class Bai implements Serializable {
    private int maBai;
    private int soPhieu;
    private int maMonHoc;
    private int diem;
    private String tinhTrang;

    public Bai(int maBai, int soPhieu, int maMonHoc, int diem, String tinhTrang) {
        this.maBai = maBai;
        this.soPhieu = soPhieu;
        this.maMonHoc = maMonHoc;
        this.diem = diem;
        this.tinhTrang = tinhTrang;
    }

    public Bai(int soPhieu, int maMonHoc, int diem, String tinhTrang) {
        this.soPhieu = soPhieu;
        this.maMonHoc = maMonHoc;
        this.diem = diem;
        this.tinhTrang = tinhTrang;
    }

    public int getMaBai() {
        return maBai;
    }

    public void setMaBai(int maBai) {
        this.maBai = maBai;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public int getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(int maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
