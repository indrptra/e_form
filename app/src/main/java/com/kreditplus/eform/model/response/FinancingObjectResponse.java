package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.AssetMasterResponseList;

import java.util.List;

public class FinancingObjectResponse { // Ambil Description doang
    @SerializedName("FinancingObject")
    private List<AssetMasterResponseList> productOfTenorObjts;

    public List<AssetMasterResponseList> getProductOfTenorObjts() {
        return productOfTenorObjts;
    }

    public void setProductOfTenorObjts(List<AssetMasterResponseList> productOfTenorObjts) {
        this.productOfTenorObjts = productOfTenorObjts;
    }
}
