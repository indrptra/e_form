package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblAsuransi")
public class TblAsuransi {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "manual_agunan")
    private String ManualAgunan;
    @DatabaseField(columnName = "manual_premi")
    private String ManualPremi;

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

    public String getManualAgunan() {
        return ManualAgunan;
    }

    public void setManualAgunan(String manualAgunan) {
        ManualAgunan = manualAgunan;
    }

    public String getManualPremi() {
        return ManualPremi;
    }

    public void setManualPremi(String manualPremi) {
        ManualPremi = manualPremi;
    }
}
