package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblKartuMembership")
public class TblDetailAsset {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "nama_barang")
    private String NamaBarang;
    @DatabaseField(columnName = "tipe")
    private  String Type;
    @DatabaseField(columnName = "price")
    private String Price;
    @DatabaseField(columnName = "dp")
    private String Dp;
    @DatabaseField(columnName = "discount")
    private String Discount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MasterFormPengajuan getMasterFormPengajuan() {
        return masterFormPengajuan;
    }

    public void setMasterFormPengajuan(MasterFormPengajuan masterFormPengajuan) {
        this.masterFormPengajuan = masterFormPengajuan;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDp() {
        return Dp;
    }

    public void setDp(String dp) {
        Dp = dp;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
