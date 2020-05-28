package com.kreditplus.eform.model;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.KelurahanFilter;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.assetMasterFilter;

import java.util.List;

public class KelurahanResponse {
    @SerializedName("KelurahanFilter")
    private List<KelurahanFilter> kelurahanFilter;

    public List<KelurahanFilter> getKelurahanFilter() {
        return kelurahanFilter;
    }

    public void setKelurahanFilter(List<KelurahanFilter> kelurahanFilter) {
        this.kelurahanFilter = kelurahanFilter;
    }
}
