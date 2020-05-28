package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblAgunan")
public class TblAgunan {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "wilayah_kendaraan")
    private String WilayahKendaraan;
    @DatabaseField(columnName = "cabang_kendaraan")
    private String CabangKendaraan;
    @DatabaseField(columnName = "kode_jenis_kendaraan")
    private String KodeJenisKendaraan;
    @DatabaseField(columnName = "jenis_kendaraan")
    private String JenisKendaraan;
    @DatabaseField(columnName = "kode_merk_kendaraan")
    private String KodeMerkKendaraan;
    @DatabaseField(columnName = "merk_kendaraan")
    private String MerkKendaraan;
    @DatabaseField(columnName = "kode_type_kendaraan")
    private String KodeTypeKendaraan;
    @DatabaseField(columnName = "type_kendaraan")
    private String TypeKendaraan;
    @DatabaseField(columnName = "tahun_kendaraan")
    private String TahunKendaraan;
    @DatabaseField(columnName = "warna_kendaraan")
    private String WarnaKendaraan;
    @DatabaseField(columnName = "status_kendaraan")
    private String StatusKendaraan;
    @DatabaseField(columnName = "isi_silinder")
    private String IsiSilinder;
    @DatabaseField(columnName = "no_polisi")
    private String NoPolisi;
    @DatabaseField(columnName = "no_rangka")
    private String NoRangka;
    @DatabaseField(columnName = "no_mesin")
    private String NoMesin;
    @DatabaseField(columnName = "bpkb_atas_nama")
    private String BpkbAtasNama;
    @DatabaseField(columnName = "nama_bpkb")
    private String NamaBpkb;
    @DatabaseField(columnName = "masa_berlaku_stnk")
    private String MasaBerlakuStnk;
    @DatabaseField(columnName = "masa_berlaku_pajak_stnk")
    private String MasaBerlakuPajakStnk;
    @DatabaseField(columnName = "pemakaian_kendaraan")
    private String PemakaianKendaraan;
    @DatabaseField(columnName = "negara_produksi")
    private String NegaraProduksi;

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

    public String getWilayahKendaraan() {
        return WilayahKendaraan;
    }

    public void setWilayahKendaraan(String wilayahKendaraan) {
        WilayahKendaraan = wilayahKendaraan;
    }

    public String getCabangKendaraan() {
        return CabangKendaraan;
    }

    public void setCabangKendaraan(String cabangKendaraan) {
        CabangKendaraan = cabangKendaraan;
    }

    public String getKodeJenisKendaraan() {
        return KodeJenisKendaraan;
    }

    public void setKodeJenisKendaraan(String kodeJenisKendaraan) {
        KodeJenisKendaraan = kodeJenisKendaraan;
    }

    public String getJenisKendaraan() {
        return JenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        JenisKendaraan = jenisKendaraan;
    }

    public String getKodeMerkKendaraan() {
        return KodeMerkKendaraan;
    }

    public void setKodeMerkKendaraan(String kodeMerkKendaraan) {
        KodeMerkKendaraan = kodeMerkKendaraan;
    }

    public String getMerkKendaraan() {
        return MerkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        MerkKendaraan = merkKendaraan;
    }

    public String getKodeTypeKendaraan() {
        return KodeTypeKendaraan;
    }

    public void setKodeTypeKendaraan(String kodeTypeKendaraan) {
        KodeTypeKendaraan = kodeTypeKendaraan;
    }

    public String getTypeKendaraan() {
        return TypeKendaraan;
    }

    public void setTypeKendaraan(String typeKendaraan) {
        TypeKendaraan = typeKendaraan;
    }

    public String getTahunKendaraan() {
        return TahunKendaraan;
    }

    public void setTahunKendaraan(String tahunKendaraan) {
        TahunKendaraan = tahunKendaraan;
    }

    public String getWarnaKendaraan() {
        return WarnaKendaraan;
    }

    public void setWarnaKendaraan(String warnaKendaraan) {
        WarnaKendaraan = warnaKendaraan;
    }

    public String getStatusKendaraan() {
        return StatusKendaraan;
    }

    public void setStatusKendaraan(String statusKendaraan) {
        StatusKendaraan = statusKendaraan;
    }

    public String getIsiSilinder() {
        return IsiSilinder;
    }

    public void setIsiSilinder(String isiSilinder) {
        IsiSilinder = isiSilinder;
    }

    public String getNoPolisi() {
        return NoPolisi;
    }

    public void setNoPolisi(String noPolisi) {
        NoPolisi = noPolisi;
    }

    public String getNoRangka() {
        return NoRangka;
    }

    public void setNoRangka(String noRangka) {
        NoRangka = noRangka;
    }

    public String getNoMesin() {
        return NoMesin;
    }

    public void setNoMesin(String noMesin) {
        NoMesin = noMesin;
    }

    public String getBpkbAtasNama() {
        return BpkbAtasNama;
    }

    public void setBpkbAtasNama(String bpkbAtasNama) {
        BpkbAtasNama = bpkbAtasNama;
    }

    public String getNamaBpkb() {
        return NamaBpkb;
    }

    public void setNamaBpkb(String namaBpkb) {
        NamaBpkb = namaBpkb;
    }

    public String getMasaBerlakuStnk() {
        return MasaBerlakuStnk;
    }

    public void setMasaBerlakuStnk(String masaBerlakuStnk) {
        MasaBerlakuStnk = masaBerlakuStnk;
    }

    public String getMasaBerlakuPajakStnk() {
        return MasaBerlakuPajakStnk;
    }

    public void setMasaBerlakuPajakStnk(String masaBerlakuPajakStnk) {
        MasaBerlakuPajakStnk = masaBerlakuPajakStnk;
    }

    public String getPemakaianKendaraan() {
        return PemakaianKendaraan;
    }

    public void setPemakaianKendaraan(String pemakaianKendaraan) {
        PemakaianKendaraan = pemakaianKendaraan;
    }

    public String getNegaraProduksi() {
        return NegaraProduksi;
    }

    public void setNegaraProduksi(String negaraProduksi) {
        NegaraProduksi = negaraProduksi;
    }
}
