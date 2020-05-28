package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 23/08/17.
 */

public class KreditmuList {

    @SerializedName("CustomerName")
    private String nama;

    @SerializedName("IDNumber")
    private String noKtp;

    @SerializedName("OrderDate")
    private String tanggal;

    @SerializedName("BirthDate")
    private String tanggalLahir;

    @SerializedName("OrderID")
    private String orderId;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
