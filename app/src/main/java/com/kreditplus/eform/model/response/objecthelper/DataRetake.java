package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 30/08/16.
 */

public class DataRetake {

    @SerializedName("application_id")
    String applicationId;
    @SerializedName("name")
    String name;
    @SerializedName("data_type")
    String dataType;
    @SerializedName("address")
    String address;
    @SerializedName("mobile_phone")
    String mobilePhone;
    @SerializedName("product_name")
    String productName;
    @SerializedName("qty")
    String quantity;
    @SerializedName("ApplicationDate")
    String applicationDate;
    @SerializedName("Asset")
    List<Asset> assetList;
    @SerializedName("DetailFinancing")
    DetailFinancing detailFinancing;
    @SerializedName("Attachment")
    List<String> attachmentList;
    @SerializedName("description_retake")
    private String descriptionRetake;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public List<Asset> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
    }

    public DetailFinancing getDetailFinancing() {
        return detailFinancing;
    }

    public void setDetailFinancing(DetailFinancing detailFinancing) {
        this.detailFinancing = detailFinancing;
    }

    public List<String> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<String> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescriptionRetake() {
        return descriptionRetake;
    }

    public void setDescriptionRetake(String descriptionRetake) {
        this.descriptionRetake = descriptionRetake;
    }
}
