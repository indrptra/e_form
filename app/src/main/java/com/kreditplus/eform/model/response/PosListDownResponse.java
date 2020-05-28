package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.POSMasterObjct;
import com.kreditplus.eform.model.response.objecthelper.ProductOfTenorObjt;

import java.util.List;

public class PosListDownResponse {
    @SerializedName("POSMaster")
    private List<POSMasterObjct> posMasters;

    public List<POSMasterObjct> getPosMasters() {
        return posMasters;
    }

    public void setPosMasters(List<POSMasterObjct> posMasters) {
        this.posMasters = posMasters;
    }
}
