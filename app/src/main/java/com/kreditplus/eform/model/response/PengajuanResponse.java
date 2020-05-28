package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class PengajuanResponse {

    @SerializedName("TotalData")
    private int totalData;
    @SerializedName("Applications")
    private List<Pengajuan> pengajuanList;

    public List<Pengajuan> getPengajuanList() {
        return pengajuanList;
    }

    public void setPengajuanList(List<Pengajuan> pengajuanList) {
        this.pengajuanList = pengajuanList;
    }

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }
}
