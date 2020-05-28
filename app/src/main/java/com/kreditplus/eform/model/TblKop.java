package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblKop")
public class TblKop {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "metode_penjualan")
    private String metodePenjualan;
    @DatabaseField(columnName = "metode_penjualan_position")
    private int metodePenjualanPosition;
    @DatabaseField(columnName = "StatusKreditmu")
    private String StatusKreditmu;
    @DatabaseField(columnName = "status_customer")
    private String StatusCustomer;
    @DatabaseField(columnName = "JenisPembiayaan")
    private String JenisPembiayaan;
    @DatabaseField(columnName = "JenisPembiayaanPosition")
    private String JenisPembiayaanPosition;
    @DatabaseField(columnName = "TujuanDana")
    private String TujuanDana;
    @DatabaseField(columnName = "TujuanDanaPosition")
    private String TujuanDanaPosition;
    @DatabaseField(columnName = "TujuanDanaLain")
    private String TujuanDanaLain;
    @DatabaseField(columnName = "MetodePenjualanLain")
    private String MetodePenjualanLain;

    public String getStatusKreditmu() {
        return StatusKreditmu;
    }

    public void setStatusKreditmu(String statusKreditmu) {
        StatusKreditmu = statusKreditmu;
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

    public String getMetodePenjualan() {
        return metodePenjualan;
    }

    public void setMetodePenjualan(String metodePenjualan) {
        this.metodePenjualan = metodePenjualan;
    }

    public int getMetodePenjualanPosition() {
        return metodePenjualanPosition;
    }

    public void setMetodePenjualanPosition(int metodePenjualanPosition) {
        this.metodePenjualanPosition = metodePenjualanPosition;
    }

    public String getStatusCustomer() {
        return StatusCustomer;
    }

    public void setStatusCustomer(String statusCustomer) {
        StatusCustomer = statusCustomer;
    }

    public String getJenisPembiayaan() {
        return JenisPembiayaan;
    }

    public void setJenisPembiayaan(String jenisPembiayaan) {
        JenisPembiayaan = jenisPembiayaan;
    }

    public String getJenisPembiayaanPosition() {
        return JenisPembiayaanPosition;
    }

    public void setJenisPembiayaanPosition(String jenisPembiayaanPosition) {
        JenisPembiayaanPosition = jenisPembiayaanPosition;
    }

    public String getTujuanDana() {
        return TujuanDana;
    }

    public void setTujuanDana(String tujuanDana) {
        TujuanDana = tujuanDana;
    }

    public String getTujuanDanaPosition() {
        return TujuanDanaPosition;
    }

    public void setTujuanDanaPosition(String tujuanDanaPosition) {
        TujuanDanaPosition = tujuanDanaPosition;
    }

    public String getTujuanDanaLain() {
        return TujuanDanaLain;
    }

    public void setTujuanDanaLain(String tujuanDanaLain) {
        TujuanDanaLain = tujuanDanaLain;
    }

    public String getMetodePenjualanLain() {
        return MetodePenjualanLain;
    }

    public void setMetodePenjualanLain(String metodePenjualanLain) {
        MetodePenjualanLain = metodePenjualanLain;
    }
}
