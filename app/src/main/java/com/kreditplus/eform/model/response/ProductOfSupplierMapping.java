package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.POS;

import java.util.List;

public class ProductOfSupplierMapping {

    @SerializedName("prod_off_supp_mapping")
    private List<ProdOfSuppMapping> prodOfSuppMappings;

    public List<ProdOfSuppMapping> getProdOfSuppMappings() {
        return prodOfSuppMappings;
    }

    public void setProdOfSuppMappings(List<ProdOfSuppMapping> prodOfSuppMappings) {
        this.prodOfSuppMappings = prodOfSuppMappings;
    }
}
