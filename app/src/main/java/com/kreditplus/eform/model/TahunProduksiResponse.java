package com.kreditplus.eform.model;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.TahunKendaraanObjt;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.assetMasterFilter;

import java.util.List;

public class TahunProduksiResponse {

    @SerializedName("year")
    private List<TahunKendaraanObjt> tahunKendaraanObjts;

    public List<TahunKendaraanObjt> getTahunKendaraanObjts() {
        return tahunKendaraanObjts;
    }

    public void setTahunKendaraanObjts(List<TahunKendaraanObjt> tahunKendaraanObjts) {
        this.tahunKendaraanObjts = tahunKendaraanObjts;
    }
}

