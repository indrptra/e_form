package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblKontakDarurat")
public class TblKontakDarurat {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "nama_lengkap")
    private String NamaLengkap;
    @DatabaseField(columnName = "hubungan_kerabat")
    private String HubunganKerabat;
    @DatabaseField(columnName = "hubungan_kerabat_position")
    private int HubunganKerabatPosition;
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
    @DatabaseField(columnName = "kode_area_telepon_rumah")
    private String KodeAreaTeleponRumah;
    @DatabaseField(columnName = "nomor_telepon_rumah")
    private String NomorTeleponRumah;
    @DatabaseField(columnName = "kode_area_telepon_kantor")
    private String KodeAreaTeleponKantor;
    @DatabaseField(columnName = "nomor_telepon_kantor")
    private String NomorTeleponKantor;
    @DatabaseField(columnName = "nomor_handphone")
    private String NomorHandphone;

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

    public String getHubunganKerabat() {
        return HubunganKerabat;
    }

    public void setHubunganKerabat(String hubunganKerabat) {
        HubunganKerabat = hubunganKerabat;
    }

    public int getHubunganKerabatPosition() {
        return HubunganKerabatPosition;
    }

    public void setHubunganKerabatPosition(int hubunganKerabatPosition) {
        HubunganKerabatPosition = hubunganKerabatPosition;
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

    public String getKodeAreaTeleponRumah() {
        return KodeAreaTeleponRumah;
    }

    public void setKodeAreaTeleponRumah(String kodeAreaTeleponRumah) {
        KodeAreaTeleponRumah = kodeAreaTeleponRumah;
    }

    public String getNomorTeleponRumah() {
        return NomorTeleponRumah;
    }

    public void setNomorTeleponRumah(String nomorTeleponRumah) {
        NomorTeleponRumah = nomorTeleponRumah;
    }

    public String getKodeAreaTeleponKantor() {
        return KodeAreaTeleponKantor;
    }

    public void setKodeAreaTeleponKantor(String kodeAreaTeleponKantor) {
        KodeAreaTeleponKantor = kodeAreaTeleponKantor;
    }

    public String getNomorTeleponKantor() {
        return NomorTeleponKantor;
    }

    public void setNomorTeleponKantor(String nomorTeleponKantor) {
        NomorTeleponKantor = nomorTeleponKantor;
    }

    public String getNomorHandphone() {
        return NomorHandphone;
    }

    public void setNomorHandphone(String nomorHandphone) {
        NomorHandphone = nomorHandphone;
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
