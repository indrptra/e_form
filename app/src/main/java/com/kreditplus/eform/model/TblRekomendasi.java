package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblRekomendasi")
public class TblRekomendasi {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "id_rekomendasi")
    private String IdRekomendasi;
    @DatabaseField(columnName = "rekomendasi")
    private String Rekomendasi;
    @DatabaseField(columnName = "catatan")
    private String Catatan;
    @DatabaseField(columnName = "jabar")
    private int Jabar;

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

    public String getIdRekomendasi() {
        return IdRekomendasi;
    }

    public void setIdRekomendasi(String idRekomendasi) {
        IdRekomendasi = idRekomendasi;
    }

    public String getRekomendasi() {
        return Rekomendasi;
    }

    public void setRekomendasi(String rekomendasi) {
        Rekomendasi = rekomendasi;
    }

    public String getCatatan() {
        return Catatan;
    }

    public void setCatatan(String catatan) {
        Catatan = catatan;
    }

    public int getJabar() {
        return Jabar;
    }

    public void setJabar(int jabar) {
        Jabar = jabar;
    }
}
