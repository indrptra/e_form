package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.MaskingObjt;

import java.util.List;

/**
 * Created by Ignatius on 10/30/2017.
 */

public class MaskingResponse {

    @SerializedName("masking")
    private List<MaskingObjt> MaskingValues;

    public List<MaskingObjt> getMaskingValues() {
        return MaskingValues;
    }

    public void setMaskingValues(List<MaskingObjt> maskingValues) {
        MaskingValues = maskingValues;
    }
}
