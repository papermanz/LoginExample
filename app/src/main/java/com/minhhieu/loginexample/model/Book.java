package com.minhhieu.loginexample.model;




public class Book implements Comparable<Book> {
    private Integer Id;
    private String tenSach;
    private String tacGia;
    private String NhaSX;
    private String theLoai;
    private String ngayXB;
    private Integer trang;
    private Float gia;
    private String noiDung;
    private String anh;

    public Book() {
    }

    public Book(Integer id, String tenSach, String tacGia, String nhaSX, String theLoai, String ngayXB, Integer trang, Float gia, String noiDung, String anh) {
        Id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        NhaSX = nhaSX;
        this.theLoai = theLoai;
        this.ngayXB = ngayXB;
        this.trang = trang;
        this.gia = gia;
        this.noiDung = noiDung;
        this.anh = anh;
    }



    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNhaSX() {
        return NhaSX;
    }

    public void setNhaSX(String nhaSX) {
        NhaSX = nhaSX;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNgayXB() {
        return ngayXB;
    }

    public void setNgayXB(String ngayXB) {
        this.ngayXB = ngayXB;
    }

    public Integer getTrang() {
        return trang;
    }

    public void setTrang(Integer trang) {
        this.trang = trang;
    }

    public Float getGia() {
        return gia;
    }

    public void setGia(Float gia) {
        this.gia = gia;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    @Override
    public int compareTo(Book o) {
        return this.trang - o.getTrang();
    }


}