package com.example.doangkdragon.db.models;

public class Mon {
    private int maMh;
    private String tenMh;
    private double chiPhi;

    public Mon(int maMon,String tenMon, double chiPhi){
        this.maMh = maMon;
        this.tenMh = tenMon;
        this.chiPhi = chiPhi;
    }

    public Mon(String tenMon, double chiPhi){
        this.tenMh = tenMon;
        this.chiPhi = chiPhi;
    }

    public String getTenMh() {
        return tenMh;
    }

    public void setTenMh(String tenMh) {
        this.tenMh = tenMh;
    }

    public double getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(double chiPhi) {
        this.chiPhi = chiPhi;
    }

    public int getMaMh() {
        return maMh;
    }
}
