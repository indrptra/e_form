package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataPribadi")
public class TblDataPribadi {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "no_ktp")
    private String NoKtp;
    @DatabaseField(columnName = "no_npwp")
    private String NoNpwp;
    @DatabaseField(columnName = "status_pernikahan")
    private String StatusPernikahan;
    @DatabaseField(columnName = "status_pernikahan_position")
    private int StatusPernikahanPosition;
    @DatabaseField(columnName = "tanggal_lahir")
    private String TanggalLahir;
    @DatabaseField(columnName = "nomor_handphone")
    private String NomorHandphone;
    @DatabaseField(columnName = "nama_lengkap")
    private String NamaLengkap;
    @DatabaseField(columnName = "nama_ktp")
    private String NamaKtp;
    @DatabaseField(columnName = "tanngal_terbit_ktp")
    private String TanngalTerbitKtp;
    @DatabaseField(columnName = "jenis_kelamin")
    private String JenisKelamin;
    @DatabaseField(columnName = "jenis_kelamin_position")
    private int JenisKelaminPosition;
    @DatabaseField(columnName = "tempat_lahir")
    private String TempatLahir;
    @DatabaseField(columnName = "nama_ibu_kandung")
    private String NamaIbuKandung;
    @DatabaseField(columnName = "status_pendidikan")
    private String StatusPendidikan;
    @DatabaseField(columnName = "status_pendidikan_position")
    private int StatusPendidikanPosition;
    @DatabaseField(columnName = "status_rumah")
    private String StatusRumah;
    @DatabaseField(columnName = "status_rumah_position")
    private int StatusRumahPosition;
    @DatabaseField(columnName = "tinggal_sejak_tahun")
    private String TinggalSejakTahun;
    @DatabaseField(columnName = "tinggal_sejak_bulan")
    private String TinggalSejakBulan;
    @DatabaseField(columnName = "agama")
    private String Agama;
    @DatabaseField(columnName = "agama_position")
    private int AgamaPosition;
    @DatabaseField(columnName = "jumlah_tanggungan")
    private String JumlahTanggungan;
    @DatabaseField(columnName = "email")
    private String Email;
    @DatabaseField(columnName = "warga_negara")
    private String WargaNegara;
    @DatabaseField(columnName = "warga_negara_position")
    private int WargaNegaraPosition;
    @DatabaseField(columnName = "PerjanjianPisahHarta")
    private String PerjanjianPisahHarta;

    public String getPerjanjianPisahHarta() {
        return PerjanjianPisahHarta;
    }

    public void setPerjanjianPisahHarta(String perjanjianPisahHarta) {
        PerjanjianPisahHarta = perjanjianPisahHarta;
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

    public String getNoKtp() {
        return NoKtp;
    }

    public void setNoKtp(String noKtp) {
        NoKtp = noKtp;
    }

    public String getNoNpwp() {
        return NoNpwp;
    }

    public void setNoNpwp(String noNpwp) {
        NoNpwp = noNpwp;
    }

    public String getStatusPernikahan() {
        return StatusPernikahan;
    }

    public void setStatusPernikahan(String statusPernikahan) {
        StatusPernikahan = statusPernikahan;
    }

    public int getStatusPernikahanPosition() {
        return StatusPernikahanPosition;
    }

    public void setStatusPernikahanPosition(int statusPernikahanPosition) {
        StatusPernikahanPosition = statusPernikahanPosition;
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

    public String getNamaLengkap() {
        return NamaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        NamaLengkap = namaLengkap;
    }

    public String getNamaKtp() {
        return NamaKtp;
    }

    public void setNamaKtp(String namaKtp) {
        NamaKtp = namaKtp;
    }

    public String getTanngalTerbitKtp() {
        return TanngalTerbitKtp;
    }

    public void setTanngalTerbitKtp(String tanngalTerbitKtp) {
        TanngalTerbitKtp = tanngalTerbitKtp;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        JenisKelamin = jenisKelamin;
    }

    public int getJenisKelaminPosition() {
        return JenisKelaminPosition;
    }

    public void setJenisKelaminPosition(int jenisKelaminPosition) {
        JenisKelaminPosition = jenisKelaminPosition;
    }

    public String getTempatLahir() {
        return TempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        TempatLahir = tempatLahir;
    }

    public String getNamaIbuKandung() {
        return NamaIbuKandung;
    }

    public void setNamaIbuKandung(String namaIbuKandung) {
        NamaIbuKandung = namaIbuKandung;
    }

    public String getStatusPendidikan() {
        return StatusPendidikan;
    }

    public void setStatusPendidikan(String statusPendidikan) {
        StatusPendidikan = statusPendidikan;
    }

    public int getStatusPendidikanPosition() {
        return StatusPendidikanPosition;
    }

    public void setStatusPendidikanPosition(int statusPendidikanPosition) {
        StatusPendidikanPosition = statusPendidikanPosition;
    }

    public String getStatusRumah() {
        return StatusRumah;
    }

    public void setStatusRumah(String statusRumah) {
        StatusRumah = statusRumah;
    }

    public int getStatusRumahPosition() {
        return StatusRumahPosition;
    }

    public void setStatusRumahPosition(int statusRumahPosition) {
        StatusRumahPosition = statusRumahPosition;
    }

    public String getTinggalSejakTahun() {
        return TinggalSejakTahun;
    }

    public void setTinggalSejakTahun(String tinggalSejakTahun) {
        TinggalSejakTahun = tinggalSejakTahun;
    }

    public String getTinggalSejakBulan() {
        return TinggalSejakBulan;
    }

    public void setTinggalSejakBulan(String tinggalSejakBulan) {
        TinggalSejakBulan = tinggalSejakBulan;
    }

    public String getAgama() {
        return Agama;
    }

    public void setAgama(String agama) {
        Agama = agama;
    }

    public int getAgamaPosition() {
        return AgamaPosition;
    }

    public void setAgamaPosition(int agamaPosition) {
        AgamaPosition = agamaPosition;
    }

    public String getJumlahTanggungan() {
        return JumlahTanggungan;
    }

    public void setJumlahTanggungan(String jumlahTanggungan) {
        JumlahTanggungan = jumlahTanggungan;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWargaNegara() {
        return WargaNegara;
    }

    public void setWargaNegara(String wargaNegara) {
        WargaNegara = wargaNegara;
    }

    public int getWargaNegaraPosition() {
        return WargaNegaraPosition;
    }

    public void setWargaNegaraPosition(int wargaNegaraPosition) {
        WargaNegaraPosition = wargaNegaraPosition;
    }
}
