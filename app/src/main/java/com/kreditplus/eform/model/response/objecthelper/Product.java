package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class Product {

    @SerializedName("ProductCode")
    private String productCode;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("ProductType")
    private String ProductType;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    @Override
    public String toString() {
        return productName;
    }
}
