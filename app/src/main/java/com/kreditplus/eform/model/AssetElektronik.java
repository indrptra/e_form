package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Iwan Nurdesa on 03/07/16.
 */
@DatabaseTable(tableName = "detail_asset")
public class AssetElektronik {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "kode_barang")
    private String kodeBarang;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "pengajuan_id")
    private MasterFormPengajuan masterFormPengajuan;
    @DatabaseField(columnName = "nama_barang")
    private String namaBarang;
    @DatabaseField(columnName = "price")
    private String price;
    @DatabaseField(columnName = "dp")
    private String dp;
    @DatabaseField(columnName = "discount")
    private String discount;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "category")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public MasterFormPengajuan getMasterFormPengajuan() {
        return masterFormPengajuan;
    }

    public void setMasterFormPengajuan(MasterFormPengajuan masterFormPengajuan) {
        this.masterFormPengajuan = masterFormPengajuan;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
