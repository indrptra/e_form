package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.SaldoKreditmuObjt;

import java.util.List;

/**
 * Created by apc-lap012 on 02/08/17.
 */

public class SaldoKreditmuResponse {

    @SerializedName("CekPlafonResponse")
    private SaldoKreditmuObjt saldoKreditmuObjt;

    public SaldoKreditmuObjt getSaldoKreditmuObjt() {
        return saldoKreditmuObjt;
    }

    public void setSaldoKreditmuObjt(SaldoKreditmuObjt saldoKreditmuObjt) {
        this.saldoKreditmuObjt = saldoKreditmuObjt;
    }
}
