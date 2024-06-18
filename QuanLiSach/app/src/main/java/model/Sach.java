package model;

public class Sach {
    private String maSach;
    private String tenSach;
    private double giaSach;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, double giaSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaSach = giaSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public double getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(double giaSach) {
        this.giaSach = giaSach;
    }
}
