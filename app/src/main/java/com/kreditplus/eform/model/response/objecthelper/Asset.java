package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
@Parcel
public class Asset {

    @SerializedName("AssetSeqNo")
    @Expose
    public String assetSeqNo;
    @SerializedName("AssetCode")
    @Expose
    public String assetCode;
    @SerializedName("AssetName")
    @Expose
    public String assetName;
    @SerializedName("CategoryId")
    @Expose
    public String categoryId;
    @SerializedName("Category")
    @Expose
    public String category;
    @SerializedName("MerkId")
    @Expose
    public String merkId;
    @SerializedName("Merk")
    @Expose
    public String merk;
    @SerializedName("TypeId")
    @Expose
    public String typeId;
    @SerializedName("Type")
    @Expose
    public String type;
    @SerializedName("ManufacturingYear")
    @Expose
    public String manufacturingYear;
    @SerializedName("Color")
    @Expose
    public String color;
    @SerializedName("Cylinder")
    @Expose
    public String cylinder;
    @SerializedName("PoliceNo")
    @Expose
    public String policeNo;
    @SerializedName("FrameNumber")
    @Expose
    public String frameNumber;
    @SerializedName("MachineNo")
    @Expose
    public String machineNo;
    @SerializedName("StatusVehicle")
    @Expose
    public String statusVehicle;
    @SerializedName("ValidityPeriodStnk")
    @Expose
    public String validityPeriodStnk;
    @SerializedName("ValidityPeriodTaxStnk")
    @Expose
    public String validityPeriodTaxStnk;
    @SerializedName("BpkbName")
    @Expose
    public String bpkbName;
    @SerializedName("BpkbOwnName")
    @Expose
    public String bpkbOwnName;
    @SerializedName("Branch")
    @Expose
    public String branch;
    @SerializedName("Region")
    @Expose
    public String region;

    @SerializedName("OTRPrice")
    @Expose
    public String OTRPrice;

    @SerializedName("DPAmount")
    @Expose
    public String DPAmount;

    @SerializedName("Discount")
    @Expose
    public String Discount;

    @SerializedName("TypeAsset")
    @Expose
    public String TypeAsset;

    public String getTypeAsset() {
        return TypeAsset;
    }

    public void setTypeAsset(String typeAsset) {
        TypeAsset = typeAsset;
    }

    public String getOTRPrice() {
        return OTRPrice;
    }

    public void setOTRPrice(String OTRPrice) {
        this.OTRPrice = OTRPrice;
    }

    public String getDPAmount() {
        return DPAmount;
    }

    public void setDPAmount(String DPAmount) {
        this.DPAmount = DPAmount;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getAssetSeqNo() {
        return assetSeqNo;
    }

    public void setAssetSeqNo(String assetSeqNo) {
        this.assetSeqNo = assetSeqNo;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMerkId() {
        return merkId;
    }

    public void setMerkId(String merkId) {
        this.merkId = merkId;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(String manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCylinder() {
        return cylinder;
    }

    public void setCylinder(String cylinder) {
        this.cylinder = cylinder;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getStatusVehicle() {
        return statusVehicle;
    }

    public void setStatusVehicle(String statusVehicle) {
        this.statusVehicle = statusVehicle;
    }

    public String getValidityPeriodStnk() {
        return validityPeriodStnk;
    }

    public void setValidityPeriodStnk(String validityPeriodStnk) {
        this.validityPeriodStnk = validityPeriodStnk;
    }

    public String getValidityPeriodTaxStnk() {
        return validityPeriodTaxStnk;
    }

    public void setValidityPeriodTaxStnk(String validityPeriodTaxStnk) {
        this.validityPeriodTaxStnk = validityPeriodTaxStnk;
    }

    public String getBpkbName() {
        return bpkbName;
    }

    public void setBpkbName(String bpkbName) {
        this.bpkbName = bpkbName;
    }

    public String getBpkbOwnName() {
        return bpkbOwnName;
    }

    public void setBpkbOwnName(String bpkbOwnName) {
        this.bpkbOwnName = bpkbOwnName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
