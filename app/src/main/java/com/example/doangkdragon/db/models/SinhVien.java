package com.example.doangkdragon.db.models;

public class SinhVien {
    private String hoTenSv;
    private byte[] hinh;

    public String getHoTenSv() {
        return hoTenSv;
    }

    public void setHoTenSv(String hoTenSv) {
        this.hoTenSv = hoTenSv;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
