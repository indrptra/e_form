package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblKartuMembership")
public class TblKartuMembership {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "no_membership")
    private String NoMembership;
    @DatabaseField(columnName = "tanggal_efektif")
    private String TanggalEfektif;
    @DatabaseField(columnName = "tanggal_exipred")
    private String TanggalExipred;

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

    public String getNoMembership() {
        return NoMembership;
    }

    public void setNoMembership(String noMembership) {
        NoMembership = noMembership;
    }

    public String getTanggalEfektif() {
        return TanggalEfektif;
    }

    public void setTanggalEfektif(String tanggalEfektif) {
        TanggalEfektif = tanggalEfektif;
    }

    public String getTanggalExipred() {
        return TanggalExipred;
    }

    public void setTanggalExipred(String tanggalExipred) {
        TanggalExipred = tanggalExipred;
    }
}
