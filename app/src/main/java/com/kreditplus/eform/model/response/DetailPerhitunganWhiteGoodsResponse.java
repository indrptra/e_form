package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

public class DetailPerhitunganWhiteGoodsResponse {
    @SerializedName("dp")
    private int dp;
    @SerializedName("dp_minimum")
    private int dp_minimum;
    @SerializedName("asuransi")
    private int asuransi;
    @SerializedName("jumlah_pembiayaan")
    private int jumlah_pembiayaan;
    @SerializedName("bunga_pembiayaan")
    private int bunga_pembiayaan;
    @SerializedName("total_pinjaman")
    private int total_pinjaman;
    @SerializedName("angsuran")
    private int angsuran;
    @SerializedName("pembayaran_pertama")
    private int pembayaran_pertama;
    @SerializedName("angsuran_bebas_bunga")
    private int angsuran_bebas_bunga;
    @SerializedName("jumlah_bebas_bunga")
    private int jumlah_bebas_bunga;
    @SerializedName("bunga_pembiayaan_perbulan")
    private int bunga_pembiayaan_perbulan;

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public int getDp_minimum() {
        return dp_minimum;
    }

    public void setDp_minimum(int dp_minimum) {
        this.dp_minimum = dp_minimum;
    }

    public int getBunga_pembiayaan_perbulan() {
        return bunga_pembiayaan_perbulan;
    }

    public void setBunga_pembiayaan_perbulan(int bunga_pembiayaan_perbulan) {
        this.bunga_pembiayaan_perbulan = bunga_pembiayaan_perbulan;
    }

    public int getAngsuran_bebas_bunga() {
        return angsuran_bebas_bunga;
    }

    public void setAngsuran_bebas_bunga(int angsuran_bebas_bunga) {
        this.angsuran_bebas_bunga = angsuran_bebas_bunga;
    }

    public int getJumlah_bebas_bunga() {
        return jumlah_bebas_bunga;
    }

    public void setJumlah_bebas_bunga(int jumlah_bebas_bunga) {
        this.jumlah_bebas_bunga = jumlah_bebas_bunga;
    }

    public int getAsuransi() {
        return asuransi;
    }

    public void setAsuransi(int asuransi) {
        this.asuransi = asuransi;
    }

    public int getJumlah_pembiayaan() {
        return jumlah_pembiayaan;
    }

    public void setJumlah_pembiayaan(int jumlah_pembiayaan) {
        this.jumlah_pembiayaan = jumlah_pembiayaan;
    }

    public int getBunga_pembiayaan() {
        return bunga_pembiayaan;
    }

    public void setBunga_pembiayaan(int bunga_pembiayaan) {
        this.bunga_pembiayaan = bunga_pembiayaan;
    }

    public int getTotal_pinjaman() {
        return total_pinjaman;
    }

    public void setTotal_pinjaman(int total_pinjaman) {
        this.total_pinjaman = total_pinjaman;
    }

    public int getAngsuran() {
        return angsuran;
    }

    public void setAngsuran(int angsuran) {
        this.angsuran = angsuran;
    }

    public int getPembayaran_pertama() {
        return pembayaran_pertama;
    }

    public void setPembayaran_pertama(int pembayaran_pertama) {
        this.pembayaran_pertama = pembayaran_pertama;
    }
}
