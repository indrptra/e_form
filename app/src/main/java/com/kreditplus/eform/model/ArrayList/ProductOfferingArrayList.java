package com.kreditplus.eform.model.ArrayList;

public class ProductOfferingArrayList {
    private String ProductID;
    private String ProductOfferingID;
    private String ProductIDDescription;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public String getProductIDDescription() {
        return ProductIDDescription;
    }

    public void setProductIDDescription(String productIDDescription) {
        ProductIDDescription = productIDDescription;
    }

    public ProductOfferingArrayList(String productID, String productOfferingID, String productIDDescription) {
        ProductID = productID;
        ProductOfferingID = productOfferingID;
        ProductIDDescription = productIDDescription;
    }

    @Override
    public String toString() {
        return ProductOfferingID + " - " + ProductIDDescription;
    }
}
