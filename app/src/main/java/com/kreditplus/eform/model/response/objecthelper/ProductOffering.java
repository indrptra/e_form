package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class ProductOffering {

    @SerializedName("ProductID")
    private String ProductID;
    @SerializedName("ProductOfferingID")
    private String ProductOfferingID;
    @SerializedName("ProductOfferingIDDescription")
    private String ProductOfferingIDDescription;

    public String getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public String getProductOfferingIDDescription() {
        return ProductOfferingIDDescription;
    }

    public void setProductOfferingIDDescription(String productOfferingIDDescription) {
        ProductOfferingIDDescription = productOfferingIDDescription;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }
}
