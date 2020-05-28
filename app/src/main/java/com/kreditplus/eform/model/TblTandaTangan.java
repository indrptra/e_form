package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblTandaTangan")
public class TblTandaTangan {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "ttd_pemohon")
    private String TtdPemohon;
    @DatabaseField(columnName = "ttd_pasangan")
    private String TtdPasangan;
    @DatabaseField(columnName = "jumlah_ttd")
    private int JumlahTtd;

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

    public String getTtdPemohon() {
        return TtdPemohon;
    }

    public void setTtdPemohon(String ttdPemohon) {
        TtdPemohon = ttdPemohon;
    }

    public String getTtdPasangan() {
        return TtdPasangan;
    }

    public void setTtdPasangan(String ttdPasangan) {
        TtdPasangan = ttdPasangan;
    }

    public int getJumlahTtd() {
        return JumlahTtd;
    }

    public void setJumlahTtd(int jumlahTtd) {
        JumlahTtd = jumlahTtd;
    }
}
