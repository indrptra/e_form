package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataAsuransi")
public class TblDataAsuransi {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "rb_premi_asuransi")
    private String RbPremiAsuransi;
    @DatabaseField(columnName = "rb_manual_premi_asuransi")
    private String RbManualPremiAsuransi;
    @DatabaseField(columnName = "nilai_manual_premi_asuransi")
    private String NilaiManualPremiAsuransi;

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

    public String getRbPremiAsuransi() {
        return RbPremiAsuransi;
    }

    public void setRbPremiAsuransi(String rbPremiAsuransi) {
        RbPremiAsuransi = rbPremiAsuransi;
    }

    public String getRbManualPremiAsuransi() {
        return RbManualPremiAsuransi;
    }

    public void setRbManualPremiAsuransi(String rbManualPremiAsuransi) {
        RbManualPremiAsuransi = rbManualPremiAsuransi;
    }

    public String getNilaiManualPremiAsuransi() {
        return NilaiManualPremiAsuransi;
    }

    public void setNilaiManualPremiAsuransi(String nilaiManualPremiAsuransi) {
        NilaiManualPremiAsuransi = nilaiManualPremiAsuransi;
    }
}
