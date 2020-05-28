package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.ProductOfTenorObjt;

import java.util.List;

public class ProductOffTenorResponse {
    @SerializedName("ProductOffTenor")
    private List<ProductOfTenorObjt> productOfTenorObjts;

    public List<ProductOfTenorObjt> getProductOfTenorObjts() {
        return productOfTenorObjts;
    }

    public void setProductOfTenorObjts(List<ProductOfTenorObjt> productOfTenorObjts) {
        this.productOfTenorObjts = productOfTenorObjts;
    }
}
