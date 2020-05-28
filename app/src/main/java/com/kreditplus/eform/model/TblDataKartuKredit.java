package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataKartuKredit")
public class TblDataKartuKredit {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "nama_bank")
    private String NamaBank;
    @DatabaseField(columnName = "no_kartu_kredit")
    private String NoKartuKredit;
    @DatabaseField(columnName = "jenis_kartu_kredit")
    private String JenisKartuKredit;
    @DatabaseField(columnName = "limit_kartu_kredit")
    private String LimitKartuKredit;
    @DatabaseField(columnName = "tahun_kadaluarsa_kartu_kredit")
    private String TahunKadaluarsaKartuKredit;
    @DatabaseField(columnName = "bulan_kadaluarsa_kartu_kredit")
    private String BulanKadaluarsaKartuKredit;

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

    public String getNamaBank() {
        return NamaBank;
    }

    public void setNamaBank(String namaBank) {
        NamaBank = namaBank;
    }

    public String getNoKartuKredit() {
        return NoKartuKredit;
    }

    public void setNoKartuKredit(String noKartuKredit) {
        NoKartuKredit = noKartuKredit;
    }

    public String getJenisKartuKredit() {
        return JenisKartuKredit;
    }

    public void setJenisKartuKredit(String jenisKartuKredit) {
        JenisKartuKredit = jenisKartuKredit;
    }

    public String getLimitKartuKredit() {
        return LimitKartuKredit;
    }

    public void setLimitKartuKredit(String limitKartuKredit) {
        LimitKartuKredit = limitKartuKredit;
    }

    public String getTahunKadaluarsaKartuKredit() {
        return TahunKadaluarsaKartuKredit;
    }

    public void setTahunKadaluarsaKartuKredit(String tahunKadaluarsaKartuKredit) {
        TahunKadaluarsaKartuKredit = tahunKadaluarsaKartuKredit;
    }

    public String getBulanKadaluarsaKartuKredit() {
        return BulanKadaluarsaKartuKredit;
    }

    public void setBulanKadaluarsaKartuKredit(String bulanKadaluarsaKartuKredit) {
        BulanKadaluarsaKartuKredit = bulanKadaluarsaKartuKredit;
    }
}
