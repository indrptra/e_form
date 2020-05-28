package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataPerhitunganKendaraan")
public class TblDataPerhitunganKendaraan {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "harga_agunan")
    private String HargaAgunan;
    @DatabaseField(columnName = "pokok_pembiayaan")
    private String PokokPembiayaan;
    @DatabaseField(columnName = "biaya_admin_perhitungan_kendaraan")
    private String BiayaAdminPerhitunganKendaraan;
    @DatabaseField(columnName = "premi_asuransi_perhitungan_agunan_kendaraan")
    private String PremiAsuransiPerhitunganAgunanKendaraan;
    @DatabaseField(columnName = "premi_asuransi_perhitungan_jiwa_kendaraan")
    private String PremiAsuransiPerhitunganJiwaKendaraan;
    @DatabaseField(columnName = "jumlah_pembiayaan_perhitungan_kendaraan")
    private String JumlahPembiayaanPerhitunganKendaraan;
    @DatabaseField(columnName = "bunga_pembiayaan_kendaraan")
    private String BungaPembiayaanKendaraan;
    @DatabaseField(columnName = "total_pinjaman_perhitungan_kendaraan")
    private String TotalPinjamanPerhitunganKendaraan;
    @DatabaseField(columnName = "nilai_agunan_perhitungan_kendaraan")
    private String NilaiAgunanPerhitunganKendaraan;
    @DatabaseField(columnName = "angsuran_perbulan_perhitungan_kendaraan")
    private String AngsuranPerbulanPerhitunganKendaraan;
    @DatabaseField(columnName = "flat_pertahun_perhitungan_kendaraan")
    private String FlatPertahunPerhitunganKendaraan;
    @DatabaseField(columnName = "flat_perbulan_perhitungan_kendaraan")
    private String FlatPerbulanPerhitunganKendaraan;
    @DatabaseField(columnName = "asuransi_agunan")
    private String AsuransiAgunan;
    @DatabaseField(columnName = "tenor_perhitungan_kendaraan")
    private String TenorPerhitunganKendaraan;
    @DatabaseField(columnName = "fidusia_perhitungan_kendaraan")
    private String FidusiaPerhitunganKendaraan;
    @DatabaseField(columnName = "stnk_perhitungan_kendaraan")
    private String StnkPerhitunganKendaraan;
    @DatabaseField(columnName = "biaya_lainnya_perhitungan_kendaraan")
    private String BiayaLainnyaPerhitunganKendaraan;
    @DatabaseField(columnName = "angsuran_pertama_perhitungan_kendaraan")
    private String AngsuranPertamaPerhitunganKendaraan;

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

    public String getHargaAgunan() {
        return HargaAgunan;
    }

    public void setHargaAgunan(String hargaAgunan) {
        HargaAgunan = hargaAgunan;
    }

    public String getPokokPembiayaan() {
        return PokokPembiayaan;
    }

    public void setPokokPembiayaan(String pokokPembiayaan) {
        PokokPembiayaan = pokokPembiayaan;
    }

    public String getBiayaAdminPerhitunganKendaraan() {
        return BiayaAdminPerhitunganKendaraan;
    }

    public void setBiayaAdminPerhitunganKendaraan(String biayaAdminPerhitunganKendaraan) {
        BiayaAdminPerhitunganKendaraan = biayaAdminPerhitunganKendaraan;
    }

    public String getPremiAsuransiPerhitunganAgunanKendaraan() {
        return PremiAsuransiPerhitunganAgunanKendaraan;
    }

    public void setPremiAsuransiPerhitunganAgunanKendaraan(String premiAsuransiPerhitunganAgunanKendaraan) {
        PremiAsuransiPerhitunganAgunanKendaraan = premiAsuransiPerhitunganAgunanKendaraan;
    }

    public String getPremiAsuransiPerhitunganJiwaKendaraan() {
        return PremiAsuransiPerhitunganJiwaKendaraan;
    }

    public void setPremiAsuransiPerhitunganJiwaKendaraan(String premiAsuransiPerhitunganJiwaKendaraan) {
        PremiAsuransiPerhitunganJiwaKendaraan = premiAsuransiPerhitunganJiwaKendaraan;
    }

    public String getJumlahPembiayaanPerhitunganKendaraan() {
        return JumlahPembiayaanPerhitunganKendaraan;
    }

    public void setJumlahPembiayaanPerhitunganKendaraan(String jumlahPembiayaanPerhitunganKendaraan) {
        JumlahPembiayaanPerhitunganKendaraan = jumlahPembiayaanPerhitunganKendaraan;
    }

    public String getBungaPembiayaanKendaraan() {
        return BungaPembiayaanKendaraan;
    }

    public void setBungaPembiayaanKendaraan(String bungaPembiayaanKendaraan) {
        BungaPembiayaanKendaraan = bungaPembiayaanKendaraan;
    }

    public String getTotalPinjamanPerhitunganKendaraan() {
        return TotalPinjamanPerhitunganKendaraan;
    }

    public void setTotalPinjamanPerhitunganKendaraan(String totalPinjamanPerhitunganKendaraan) {
        TotalPinjamanPerhitunganKendaraan = totalPinjamanPerhitunganKendaraan;
    }

    public String getNilaiAgunanPerhitunganKendaraan() {
        return NilaiAgunanPerhitunganKendaraan;
    }

    public void setNilaiAgunanPerhitunganKendaraan(String nilaiAgunanPerhitunganKendaraan) {
        NilaiAgunanPerhitunganKendaraan = nilaiAgunanPerhitunganKendaraan;
    }

    public String getAngsuranPerbulanPerhitunganKendaraan() {
        return AngsuranPerbulanPerhitunganKendaraan;
    }

    public void setAngsuranPerbulanPerhitunganKendaraan(String angsuranPerbulanPerhitunganKendaraan) {
        AngsuranPerbulanPerhitunganKendaraan = angsuranPerbulanPerhitunganKendaraan;
    }

    public String getFlatPertahunPerhitunganKendaraan() {
        return FlatPertahunPerhitunganKendaraan;
    }

    public void setFlatPertahunPerhitunganKendaraan(String flatPertahunPerhitunganKendaraan) {
        FlatPertahunPerhitunganKendaraan = flatPertahunPerhitunganKendaraan;
    }

    public String getFlatPerbulanPerhitunganKendaraan() {
        return FlatPerbulanPerhitunganKendaraan;
    }

    public void setFlatPerbulanPerhitunganKendaraan(String flatPerbulanPerhitunganKendaraan) {
        FlatPerbulanPerhitunganKendaraan = flatPerbulanPerhitunganKendaraan;
    }

    public String getAsuransiAgunan() {
        return AsuransiAgunan;
    }

    public void setAsuransiAgunan(String asuransiAgunan) {
        AsuransiAgunan = asuransiAgunan;
    }

    public String getTenorPerhitunganKendaraan() {
        return TenorPerhitunganKendaraan;
    }

    public void setTenorPerhitunganKendaraan(String tenorPerhitunganKendaraan) {
        TenorPerhitunganKendaraan = tenorPerhitunganKendaraan;
    }

    public String getFidusiaPerhitunganKendaraan() {
        return FidusiaPerhitunganKendaraan;
    }

    public void setFidusiaPerhitunganKendaraan(String fidusiaPerhitunganKendaraan) {
        FidusiaPerhitunganKendaraan = fidusiaPerhitunganKendaraan;
    }

    public String getStnkPerhitunganKendaraan() {
        return StnkPerhitunganKendaraan;
    }

    public void setStnkPerhitunganKendaraan(String stnkPerhitunganKendaraan) {
        StnkPerhitunganKendaraan = stnkPerhitunganKendaraan;
    }

    public String getBiayaLainnyaPerhitunganKendaraan() {
        return BiayaLainnyaPerhitunganKendaraan;
    }

    public void setBiayaLainnyaPerhitunganKendaraan(String biayaLainnyaPerhitunganKendaraan) {
        BiayaLainnyaPerhitunganKendaraan = biayaLainnyaPerhitunganKendaraan;
    }

    public String getAngsuranPertamaPerhitunganKendaraan() {
        return AngsuranPertamaPerhitunganKendaraan;
    }

    public void setAngsuranPertamaPerhitunganKendaraan(String angsuranPertamaPerhitunganKendaraan) {
        AngsuranPertamaPerhitunganKendaraan = angsuranPertamaPerhitunganKendaraan;
    }
}
