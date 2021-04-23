package com.example.doangkdragon.db.models;

public class GiaoVien {
    private int maGv;
    private String hoTenGv;
    private int SDT;
    private byte[] hinh;

    public GiaoVien(int maGv, String hoTenGv, int SDT, byte[] hinh) {
        this.maGv = maGv;
        this.hoTenGv = hoTenGv;
        this.SDT = SDT;
        this.hinh = hinh;
    }

    public String getHoTenGv() {
        return hoTenGv;
    }

    public void setHoTenGv(String hoTenGv) {
        this.hoTenGv = hoTenGv;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public int getMaGv() {
        return maGv;
    }

    public void setMaGv(int maGv) {
        this.maGv = maGv;
    }
}
