package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblCaraPembayaran")
public class TblCaraPembayaran {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "CaraPembayaran")
    private String CaraPembayaran;
    @DatabaseField(columnName = "CaraPembayaranPosition")
    private String CaraPembayaranPosition;

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

    public String getCaraPembayaran() {
        return CaraPembayaran;
    }

    public void setCaraPembayaran(String caraPembayaran) {
        CaraPembayaran = caraPembayaran;
    }

    public String getCaraPembayaranPosition() {
        return CaraPembayaranPosition;
    }

    public void setCaraPembayaranPosition(String caraPembayaranPosition) {
        CaraPembayaranPosition = caraPembayaranPosition;
    }
}
