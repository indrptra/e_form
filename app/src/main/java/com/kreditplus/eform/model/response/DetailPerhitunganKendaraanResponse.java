package com.kreditplus.eform.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPerhitunganKendaraanResponse extends BaseResponse {
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("tenor")
    @Expose
    private Integer tenor;
    @SerializedName("harga_agunan")
    @Expose
    private String hargaAgunan;
    @SerializedName("down_payment")
    @Expose
    private String downPayment;
    @SerializedName("down_payment_lama")
    @Expose
    private String downPaymentLama;
    @SerializedName("pokok_pembiayaan")
    @Expose
    private String pokokPembiayaan;
    @SerializedName("pokok_pembiayaan_lama")
    @Expose
    private String pokokPembiayaanLama;
    @SerializedName("biaya_administrasi")
    @Expose
    private String biayaAdministrasi;
    @SerializedName("biaya_provisi")
    @Expose
    private String biayaProvisi;
    @SerializedName("asuransi_agunan")
    @Expose
    private String asuransiAgunan;
    @SerializedName("asuransi_jiwa")
    @Expose
    private String asuransiJiwa;
    @SerializedName("jumlah_pembiayaan")
    @Expose
    private String jumlahPembiayaan;
    @SerializedName("bunga_pembiayaan")
    @Expose
    private String bungaPembiayaan;
    @SerializedName("total_pinjaman")
    @Expose
    private String totalPinjaman;
    @SerializedName("pinjaman")
    @Expose
    private String Pinjaman;
    @SerializedName("angsuran_perbulan")
    @Expose
    private String angsuranPerbulan;
    @SerializedName("angsuran_perbulan_lama")
    @Expose
    private String angsuranPerbulanLama;
    @SerializedName("bunga_flat_per_tahun")
    @Expose
    private String bungaFlatPerTahun;
    @SerializedName("setor_pertama")
    @Expose
    private String setorPertama;

    public String getAngsuranPerbulanLama() {
        return angsuranPerbulanLama;
    }

    public void setAngsuranPerbulanLama(String angsuranPerbulanLama) {
        this.angsuranPerbulanLama = angsuranPerbulanLama;
    }

    public String getDownPaymentLama() {
        return downPaymentLama;
    }

    public void setDownPaymentLama(String downPaymentLama) {
        this.downPaymentLama = downPaymentLama;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public String getHargaAgunan() {
        return hargaAgunan;
    }

    public void setHargaAgunan(String hargaAgunan) {
        this.hargaAgunan = hargaAgunan;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getPokokPembiayaan() {
        return pokokPembiayaan;
    }

    public void setPokokPembiayaan(String pokokPembiayaan) {
        this.pokokPembiayaan = pokokPembiayaan;
    }

    public String getPokokPembiayaanLama() {
        return pokokPembiayaanLama;
    }

    public void setPokokPembiayaanLama(String pokokPembiayaanLama) {
        this.pokokPembiayaanLama = pokokPembiayaanLama;
    }

    public String getBiayaAdministrasi() {
        return biayaAdministrasi;
    }

    public void setBiayaAdministrasi(String biayaAdministrasi) {
        this.biayaAdministrasi = biayaAdministrasi;
    }

    public String getBiayaProvisi() {
        return biayaProvisi;
    }

    public void setBiayaProvisi(String biayaProvisi) {
        this.biayaProvisi = biayaProvisi;
    }

    public String getAsuransiAgunan() {
        return asuransiAgunan;
    }

    public void setAsuransiAgunan(String asuransiAgunan) {
        this.asuransiAgunan = asuransiAgunan;
    }

    public String getAsuransiJiwa() {
        return asuransiJiwa;
    }

    public void setAsuransiJiwa(String asuransiJiwa) {
        this.asuransiJiwa = asuransiJiwa;
    }

    public String getJumlahPembiayaan() {
        return jumlahPembiayaan;
    }

    public void setJumlahPembiayaan(String jumlahPembiayaan) {
        this.jumlahPembiayaan = jumlahPembiayaan;
    }

    public String getBungaPembiayaan() {
        return bungaPembiayaan;
    }

    public void setBungaPembiayaan(String bungaPembiayaan) {
        this.bungaPembiayaan = bungaPembiayaan;
    }

    public String getTotalPinjaman() {
        return totalPinjaman;
    }

    public void setTotalPinjaman(String totalPinjaman) {
        this.totalPinjaman = totalPinjaman;
    }

    public String getAngsuranPerbulan() {
        return angsuranPerbulan;
    }

    public void setAngsuranPerbulan(String angsuranPerbulan) {
        this.angsuranPerbulan = angsuranPerbulan;
    }

    public String getBungaFlatPerTahun() {
        return bungaFlatPerTahun;
    }

    public void setBungaFlatPerTahun(String bungaFlatPerTahun) {
        this.bungaFlatPerTahun = bungaFlatPerTahun;
    }

    public String getSetorPertama() {
        return setorPertama;
    }

    public void setSetorPertama(String setorPertama) {
        this.setorPertama = setorPertama;
    }

    public String getPinjaman() {
        return Pinjaman;
    }

    public void setPinjaman(String pinjaman) {
        Pinjaman = pinjaman;
    }
}
