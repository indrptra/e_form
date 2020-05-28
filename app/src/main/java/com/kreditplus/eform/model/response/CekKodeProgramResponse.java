package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.CekKodeProgramObjct;
import com.kreditplus.eform.model.response.objecthelper.POSMasterObjct;
import com.kreditplus.eform.model.response.objecthelper.ProductOfTenorObjt;

import java.util.List;

public class CekKodeProgramResponse {
    @SerializedName("data")
    private CekKodeProgramObjct kodeProgramObjct;

    public CekKodeProgramObjct getKodeProgramObjct() {
        return kodeProgramObjct;
    }

    public void setKodeProgramObjct(CekKodeProgramObjct kodeProgramObjct) {
        this.kodeProgramObjct = kodeProgramObjct;
    }
}
