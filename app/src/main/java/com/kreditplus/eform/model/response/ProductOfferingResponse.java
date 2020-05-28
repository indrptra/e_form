package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.ProductOffering;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class ProductOfferingResponse {

    @SerializedName("ProductOffMaster")
    private List<ProductOffering> productOfferings;

    public List<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public void setProductOfferings(List<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
    }
}
