package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.KreditmuList;

import java.util.List;

/**
 * Created by apc-lap012 on 23/08/17.
 */

public class KreditmuListResponse {

    @SerializedName("kreditmuList")
    private List<KreditmuList> kreditmuListList;

    public List<KreditmuList> getKreditmuListList() {
        return kreditmuListList;
    }

    public void setKreditmuListList(List<KreditmuList> kreditmuListList) {
        this.kreditmuListList = kreditmuListList;
    }
}
