package com.doviettue.models;

public class Product {
    private String tenSP;
    private String maSp;
    private double giaSp;

    public Product(String maSp, String tenSP, double giaSp) {
        this.tenSP = tenSP;
        this.maSp = maSp;
        this.giaSp = giaSp;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public double getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(double giaSp) {
        this.giaSp = giaSp;
    }
}
