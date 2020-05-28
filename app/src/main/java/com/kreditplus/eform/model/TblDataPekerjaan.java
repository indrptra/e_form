package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataPekerjaan")
public class TblDataPekerjaan {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "nama_perubahaan")
    private String NamaPerubahaan;
    @DatabaseField(columnName = "alamat")
    private String Alamat;
    @DatabaseField(columnName = "rt")
    private String Rt;
    @DatabaseField(columnName = "rw")
    private String Rw;
    @DatabaseField(columnName = "kota")
    private String Kota;
    @DatabaseField(columnName = "kecamatan")
    private String Kecamatan;
    @DatabaseField(columnName = "kelurahan")
    private String Kelurahan;
    @DatabaseField(columnName = "zipcode")
    private String Zipcode;
    @DatabaseField(columnName = "kode_area_telepon")
    private String KodeAreaTelepon;
    @DatabaseField(columnName = "nomor_telepon")
    private String NomorTelepon;
    @DatabaseField(columnName = "bekerja_sejak")
    private String BekerjaSejak;
    @DatabaseField(columnName = "kode_profesi")
    private String KodeProfesi;
    @DatabaseField(columnName = "profesi")
    private String Profesi;
    @DatabaseField(columnName = "tipe_pekerjaan")
    private String TipePekerjaan;
    @DatabaseField(columnName = "kode_tipe_pekerjaan")
    private String KodeTipePekerjaan;
    @DatabaseField(columnName = "posisi_pekerjaan")
    private String PosisiPekerjaan;
    @DatabaseField(columnName = "kode_posisi_pekerjaan")
    private String KodePosisiPekerjaan;
    @DatabaseField(columnName = "industri")
    private String Industri;
    @DatabaseField(columnName = "kode_industri")
    private String KodeIndustri;
    @DatabaseField(columnName = "penghasilan_tetap")
    private String PenghasilanTetap;
    @DatabaseField(columnName = "penghasilan_lain")
    private String PenghasilanLain;
    @DatabaseField(columnName = "penghasilan_pasangan")
    private String PenghasilanPasangan;
    @DatabaseField(columnName = "biaya_hidup")
    private String BiayaHidup;

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

    public String getNamaPerubahaan() {
        return NamaPerubahaan;
    }

    public void setNamaPerubahaan(String namaPerubahaan) {
        NamaPerubahaan = namaPerubahaan;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getRt() {
        return Rt;
    }

    public void setRt(String rt) {
        Rt = rt;
    }

    public String getRw() {
        return Rw;
    }

    public void setRw(String rw) {
        Rw = rw;
    }

    public String getKota() {
        return Kota;
    }

    public void setKota(String kota) {
        Kota = kota;
    }

    public String getKodeAreaTelepon() {
        return KodeAreaTelepon;
    }

    public void setKodeAreaTelepon(String kodeAreaTelepon) {
        KodeAreaTelepon = kodeAreaTelepon;
    }

    public String getNomorTelepon() {
        return NomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        NomorTelepon = nomorTelepon;
    }

    public String getBekerjaSejak() {
        return BekerjaSejak;
    }

    public void setBekerjaSejak(String bekerjaSejak) {
        BekerjaSejak = bekerjaSejak;
    }

    public String getKodeProfesi() {
        return KodeProfesi;
    }

    public void setKodeProfesi(String kodeProfesi) {
        KodeProfesi = kodeProfesi;
    }

    public String getProfesi() {
        return Profesi;
    }

    public void setProfesi(String profesi) {
        Profesi = profesi;
    }

    public String getTipePekerjaan() {
        return TipePekerjaan;
    }

    public void setTipePekerjaan(String tipePekerjaan) {
        TipePekerjaan = tipePekerjaan;
    }

    public String getKodeTipePekerjaan() {
        return KodeTipePekerjaan;
    }

    public void setKodeTipePekerjaan(String kodeTipePekerjaan) {
        KodeTipePekerjaan = kodeTipePekerjaan;
    }

    public String getPosisiPekerjaan() {
        return PosisiPekerjaan;
    }

    public void setPosisiPekerjaan(String posisiPekerjaan) {
        PosisiPekerjaan = posisiPekerjaan;
    }

    public String getKodePosisiPekerjaan() {
        return KodePosisiPekerjaan;
    }

    public void setKodePosisiPekerjaan(String kodePosisiPekerjaan) {
        KodePosisiPekerjaan = kodePosisiPekerjaan;
    }

    public String getIndustri() {
        return Industri;
    }

    public void setIndustri(String industri) {
        Industri = industri;
    }

    public String getKodeIndustri() {
        return KodeIndustri;
    }

    public void setKodeIndustri(String kodeIndustri) {
        KodeIndustri = kodeIndustri;
    }

    public String getPenghasilanTetap() {
        return PenghasilanTetap;
    }

    public void setPenghasilanTetap(String penghasilanTetap) {
        PenghasilanTetap = penghasilanTetap;
    }

    public String getPenghasilanLain() {
        return PenghasilanLain;
    }

    public void setPenghasilanLain(String penghasilanLain) {
        PenghasilanLain = penghasilanLain;
    }

    public String getPenghasilanPasangan() {
        return PenghasilanPasangan;
    }

    public void setPenghasilanPasangan(String penghasilanPasangan) {
        PenghasilanPasangan = penghasilanPasangan;
    }

    public String getBiayaHidup() {
        return BiayaHidup;
    }

    public void setBiayaHidup(String biayaHidup) {
        BiayaHidup = biayaHidup;
    }

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return Kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        Kelurahan = kelurahan;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }
}
