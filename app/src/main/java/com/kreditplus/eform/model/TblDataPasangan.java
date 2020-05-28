package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataPasangan")
public class TblDataPasangan {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "id_pasangan")
    private String IdPasangan;
    @DatabaseField(columnName = "nama_lengkap")
    private String NamaLengkap;
    @DatabaseField(columnName = "nomor_ktp")
    private String NomorKtp;
    @DatabaseField(columnName = "tempat_lahir")
    private String TempatLahir;
    @DatabaseField(columnName = "tanggal_lahir")
    private String TanggalLahir;
    @DatabaseField(columnName = "nomor_handphone")
    private String NomorHandphone;
    @DatabaseField(columnName = "kode_area_telepon_rumah")
    private String KodeAreaTeleponRumah;
    @DatabaseField(columnName = "no_telepon_rumah")
    private String NoTeleponRumah;
    @DatabaseField(columnName = "kode_area_telepon_perusahaan")
    private String KodeAreaTeleponPerusahaan;
    @DatabaseField(columnName = "no_telepon_perusahaan")
    private String NoTeleponPerusahaan;
    @DatabaseField(columnName = "alamat_pasangan")
    private String AlamatPasangan;
    @DatabaseField(columnName = "kota_pasangan")
    private String KotaPasangan;
    @DatabaseField(columnName = "profesi_pasangan")
    private String ProfesiPasangan;
    @DatabaseField(columnName = "job_type_pasangan")
    private String JobTypePasangan;
    @DatabaseField(columnName = "job_position_pasangan")
    private String JobPositionPasangan;
    @DatabaseField(columnName = "industri_pasangan")
    private String IndustriPasangan;
    @DatabaseField(columnName = "status_pasangan")
    private String StatusPasangan;
    @DatabaseField(columnName = "nama_perusahaan_pasangan")
    private String NamaPerusahaanPasangan;
    @DatabaseField(columnName = "kecamatan_pasangan")
    private String KecamatanPasangan;
    @DatabaseField(columnName = "kelurahan_pasangan")
    private String KelurahanPasangan;
    @DatabaseField(columnName = "zipcode_pasangan")
    private String ZipcodePasangan;
    @DatabaseField(columnName = "kode_profesi_pasangan")
    private String KodeProfesiPasangan;
    @DatabaseField(columnName = "kode_job_type_pasangan")
    private String KodeJobTypePasangan;
    @DatabaseField(columnName = "kode_job_position_pasangan")
    private String KodeJobPositionPasangan;
    @DatabaseField(columnName = "kode_industri_pasangan")
    private String KodeIndustriPasangan;

    public String getIdPasangan() {
        return IdPasangan;
    }

    public void setIdPasangan(String idPasangan) {
        IdPasangan = idPasangan;
    }

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

    public String getNamaLengkap() {
        return NamaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        NamaLengkap = namaLengkap;
    }

    public String getNomorKtp() {
        return NomorKtp;
    }

    public void setNomorKtp(String nomorKtp) {
        NomorKtp = nomorKtp;
    }

    public String getTempatLahir() {
        return TempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        TempatLahir = tempatLahir;
    }

    public String getTanggalLahir() {
        return TanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        TanggalLahir = tanggalLahir;
    }

    public String getNomorHandphone() {
        return NomorHandphone;
    }

    public void setNomorHandphone(String nomorHandphone) {
        NomorHandphone = nomorHandphone;
    }

    public String getKodeAreaTeleponRumah() {
        return KodeAreaTeleponRumah;
    }

    public void setKodeAreaTeleponRumah(String kodeAreaTeleponRumah) {
        KodeAreaTeleponRumah = kodeAreaTeleponRumah;
    }

    public String getNoTeleponRumah() {
        return NoTeleponRumah;
    }

    public void setNoTeleponRumah(String noTeleponRumah) {
        NoTeleponRumah = noTeleponRumah;
    }

    public String getKodeAreaTeleponPerusahaan() {
        return KodeAreaTeleponPerusahaan;
    }

    public void setKodeAreaTeleponPerusahaan(String kodeAreaTeleponPerusahaan) {
        KodeAreaTeleponPerusahaan = kodeAreaTeleponPerusahaan;
    }

    public String getNoTeleponPerusahaan() {
        return NoTeleponPerusahaan;
    }

    public void setNoTeleponPerusahaan(String noTeleponPerusahaan) {
        NoTeleponPerusahaan = noTeleponPerusahaan;
    }

    public String getAlamatPasangan() {
        return AlamatPasangan;
    }

    public void setAlamatPasangan(String alamatPasangan) {
        AlamatPasangan = alamatPasangan;
    }

    public String getKotaPasangan() {
        return KotaPasangan;
    }

    public void setKotaPasangan(String kotaPasangan) {
        KotaPasangan = kotaPasangan;
    }

    public String getProfesiPasangan() {
        return ProfesiPasangan;
    }

    public void setProfesiPasangan(String profesiPasangan) {
        ProfesiPasangan = profesiPasangan;
    }

    public String getJobTypePasangan() {
        return JobTypePasangan;
    }

    public void setJobTypePasangan(String jobTypePasangan) {
        JobTypePasangan = jobTypePasangan;
    }

    public String getJobPositionPasangan() {
        return JobPositionPasangan;
    }

    public void setJobPositionPasangan(String jobPositionPasangan) {
        JobPositionPasangan = jobPositionPasangan;
    }

    public String getIndustriPasangan() {
        return IndustriPasangan;
    }

    public void setIndustriPasangan(String industriPasangan) {
        IndustriPasangan = industriPasangan;
    }

    public String getStatusPasangan() {
        return StatusPasangan;
    }

    public void setStatusPasangan(String statusPasangan) {
        StatusPasangan = statusPasangan;
    }

    public String getNamaPerusahaanPasangan() {
        return NamaPerusahaanPasangan;
    }

    public void setNamaPerusahaanPasangan(String namaPerusahaanPasangan) {
        NamaPerusahaanPasangan = namaPerusahaanPasangan;
    }

    public String getKecamatanPasangan() {
        return KecamatanPasangan;
    }

    public void setKecamatanPasangan(String kecamatanPasangan) {
        KecamatanPasangan = kecamatanPasangan;
    }

    public String getKelurahanPasangan() {
        return KelurahanPasangan;
    }

    public void setKelurahanPasangan(String kelurahanPasangan) {
        KelurahanPasangan = kelurahanPasangan;
    }

    public String getZipcodePasangan() {
        return ZipcodePasangan;
    }

    public void setZipcodePasangan(String zipcodePasangan) {
        ZipcodePasangan = zipcodePasangan;
    }

    public String getKodeProfesiPasangan() {
        return KodeProfesiPasangan;
    }

    public void setKodeProfesiPasangan(String kodeProfesiPasangan) {
        KodeProfesiPasangan = kodeProfesiPasangan;
    }

    public String getKodeJobTypePasangan() {
        return KodeJobTypePasangan;
    }

    public void setKodeJobTypePasangan(String kodeJobTypePasangan) {
        KodeJobTypePasangan = kodeJobTypePasangan;
    }

    public String getKodeJobPositionPasangan() {
        return KodeJobPositionPasangan;
    }

    public void setKodeJobPositionPasangan(String kodeJobPositionPasangan) {
        KodeJobPositionPasangan = kodeJobPositionPasangan;
    }

    public String getKodeIndustriPasangan() {
        return KodeIndustriPasangan;
    }

    public void setKodeIndustriPasangan(String kodeIndustriPasangan) {
        KodeIndustriPasangan = kodeIndustriPasangan;
    }
}
