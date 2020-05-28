package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblAlamat")
public class TblAlamat {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "alamat_tinggal")
    private String AlamatTinggal;
    @DatabaseField(columnName = "rt_tinggal")
    private String RtTinggal;
    @DatabaseField(columnName = "rw_tinggal")
    private String RwTinggal;
    @DatabaseField(columnName = "kota_tinggal")
    private String KotaTinggal;
    @DatabaseField(columnName = "kecamatan_tinggal")
    private String KecamatanTinggal;
    @DatabaseField(columnName = "kelurahan_tinggal")
    private String KelurahanTinggal;
    @DatabaseField(columnName = "zipcode_tinggal")
    private String ZipcodeTinggal;
    @DatabaseField(columnName = "kode_area_telepon_tinggal")
    private String KodeAreaTeleponTinggal;
    @DatabaseField(columnName = "nomor_telepon_tinggal")
    private String NomorTeleponTinggal;
    @DatabaseField(columnName = "alamat_sama_ktp")
    private String AlamatSamaKtp;
    @DatabaseField(columnName = "alamat_ktp")
    private String AlamatKtp;
    @DatabaseField(columnName = "rt_ktp")
    private String RtKtp;
    @DatabaseField(columnName = "rw_ktp")
    private String RwKtp;
    @DatabaseField(columnName = "kota_ktp")
    private String KotaKtp;
    @DatabaseField(columnName = "kecamatan_ktp")
    private String KecamatanKtp;
    @DatabaseField(columnName = "kelurahan_ktp")
    private String KelurahanKtp;
    @DatabaseField(columnName = "zipcode_ktp")
    private String ZipcodeKtp;
    @DatabaseField(columnName = "kode_area_telepon_ktp")
    private String KodeAreaTeleponKtp;
    @DatabaseField(columnName = "nomor_telepon_ktp")
    private String NomorTeleponKtp;

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

    public String getAlamatTinggal() {
        return AlamatTinggal;
    }

    public void setAlamatTinggal(String alamatTinggal) {
        AlamatTinggal = alamatTinggal;
    }

    public String getRtTinggal() {
        return RtTinggal;
    }

    public void setRtTinggal(String rtTinggal) {
        RtTinggal = rtTinggal;
    }

    public String getRwTinggal() {
        return RwTinggal;
    }

    public void setRwTinggal(String rwTinggal) {
        RwTinggal = rwTinggal;
    }

    public String getKotaTinggal() {
        return KotaTinggal;
    }

    public void setKotaTinggal(String kotaTinggal) {
        KotaTinggal = kotaTinggal;
    }

    public String getKodeAreaTeleponTinggal() {
        return KodeAreaTeleponTinggal;
    }

    public void setKodeAreaTeleponTinggal(String kodeAreaTeleponTinggal) {
        KodeAreaTeleponTinggal = kodeAreaTeleponTinggal;
    }

    public String getNomorTeleponTinggal() {
        return NomorTeleponTinggal;
    }

    public void setNomorTeleponTinggal(String nomorTeleponTinggal) {
        NomorTeleponTinggal = nomorTeleponTinggal;
    }

    public String getAlamatSamaKtp() {
        return AlamatSamaKtp;
    }

    public void setAlamatSamaKtp(String alamatSamaKtp) {
        AlamatSamaKtp = alamatSamaKtp;
    }

    public String getAlamatKtp() {
        return AlamatKtp;
    }

    public void setAlamatKtp(String alamatKtp) {
        AlamatKtp = alamatKtp;
    }

    public String getRtKtp() {
        return RtKtp;
    }

    public void setRtKtp(String rtKtp) {
        RtKtp = rtKtp;
    }

    public String getRwKtp() {
        return RwKtp;
    }

    public void setRwKtp(String rwKtp) {
        RwKtp = rwKtp;
    }

    public String getKotaKtp() {
        return KotaKtp;
    }

    public void setKotaKtp(String kotaKtp) {
        KotaKtp = kotaKtp;
    }

    public String getKodeAreaTeleponKtp() {
        return KodeAreaTeleponKtp;
    }

    public void setKodeAreaTeleponKtp(String kodeAreaTeleponKtp) {
        KodeAreaTeleponKtp = kodeAreaTeleponKtp;
    }

    public String getNomorTeleponKtp() {
        return NomorTeleponKtp;
    }

    public void setNomorTeleponKtp(String nomorTeleponKtp) {
        NomorTeleponKtp = nomorTeleponKtp;
    }

    public String getKecamatanTinggal() {
        return KecamatanTinggal;
    }

    public void setKecamatanTinggal(String kecamatanTinggal) {
        KecamatanTinggal = kecamatanTinggal;
    }

    public String getKelurahanTinggal() {
        return KelurahanTinggal;
    }

    public void setKelurahanTinggal(String kelurahanTinggal) {
        KelurahanTinggal = kelurahanTinggal;
    }

    public String getZipcodeTinggal() {
        return ZipcodeTinggal;
    }

    public void setZipcodeTinggal(String zipcodeTinggal) {
        ZipcodeTinggal = zipcodeTinggal;
    }

    public String getKecamatanKtp() {
        return KecamatanKtp;
    }

    public void setKecamatanKtp(String kecamatanKtp) {
        KecamatanKtp = kecamatanKtp;
    }

    public String getKelurahanKtp() {
        return KelurahanKtp;
    }

    public void setKelurahanKtp(String kelurahanKtp) {
        KelurahanKtp = kelurahanKtp;
    }

    public String getZipcodeKtp() {
        return ZipcodeKtp;
    }

    public void setZipcodeKtp(String zipcodeKtp) {
        ZipcodeKtp = zipcodeKtp;
    }
}
