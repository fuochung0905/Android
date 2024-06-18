package model;

public class Phone {
    private String maDienThoai;
    private String tenDienThoai;
    private double gia;

    public Phone() {
    }

    public Phone(String maDienThoai, String tenDienThoai, double gia) {
        this.maDienThoai = maDienThoai;
        this.tenDienThoai = tenDienThoai;
        this.gia = gia;
    }

    public String getMaDienThoai() {
        return maDienThoai;
    }

    public void setMaDienThoai(String maDienThoai) {
        this.maDienThoai = maDienThoai;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
