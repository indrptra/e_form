package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AoBranchMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetCategoryMasterSyncList;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetMasterMerkSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetMasterTypeSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.DataFinansialSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.MarketPriceMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.PosMaterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOfTenorSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOffSupplierMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOfferingMaterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierEmpSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.TenorsMasterSyncList;

import java.util.List;

/**
 * Created by apc-lap012 on 09/02/17.
 */

public class MasterSyncResponse extends BaseResponse {

    @SerializedName("ProductOffMaster")
    private List<ProductOfferingMaterSync> productOfferingMaterSyncList;

    @SerializedName("ProductOffTenor")
    private List<ProductOfTenorSync> productOfTenorSyncList;

    @SerializedName("SupplierEmp")
    private List<SupplierEmpSync> supplierEmpSyncList;

    @SerializedName("SupplierMaster")
    private List<SupplierMasterSync> supplierMasterSyncList;

    @SerializedName("POSMaster")
    private List<PosMaterSync> posList;

    @SerializedName("AssetMaster")
    private List<AssetMasterSync> assetMasterSyncList;

    @SerializedName("AssetCategory")
    private List<AssetCategoryMasterSyncList> assetCategoryMasterSyncList;

    @SerializedName("Region")
    private String Region;

    @SerializedName("Tenors")
    private List<TenorsMasterSyncList> tenorsMasterSyncList;


//    @SerializedName("DataFinancial")
//    private DataFinansialSync datafinancial;

//    @SerializedName("BranchPrimary")
//    private String branchPrimary;

//    @SerializedName("AOBranch")
//    private List<AoBranchMasterSync> aoBranch;

//    @SerializedName("ProductOffSupplier")
//    private List<ProductOffSupplierMasterSync> productOffSupplierMasterSyncs;


    public List<TenorsMasterSyncList> getTenorsMasterSyncList() {
        return tenorsMasterSyncList;
    }

    public void setTenorsMasterSyncList(List<TenorsMasterSyncList> tenorsMasterSyncList) {
        this.tenorsMasterSyncList = tenorsMasterSyncList;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public List<AssetCategoryMasterSyncList> getAssetCategoryMasterSyncList() {
        return assetCategoryMasterSyncList;
    }

    public void setAssetCategoryMasterSyncList(List<AssetCategoryMasterSyncList> assetCategoryMasterSyncList) {
        this.assetCategoryMasterSyncList = assetCategoryMasterSyncList;
    }

    public List<ProductOfferingMaterSync> getProductOfferingMaterSyncList() {
        return productOfferingMaterSyncList;
    }

    public void setProductOfferingMaterSyncList(List<ProductOfferingMaterSync> productOfferingMaterSyncList) {
        this.productOfferingMaterSyncList = productOfferingMaterSyncList;
    }

    public List<ProductOfTenorSync> getProductOfTenorSyncList() {
        return productOfTenorSyncList;
    }

    public void setProductOfTenorSyncList(List<ProductOfTenorSync> productOfTenorSyncList) {
        this.productOfTenorSyncList = productOfTenorSyncList;
    }

    public List<SupplierEmpSync> getSupplierEmpSyncList() {
        return supplierEmpSyncList;
    }

    public void setSupplierEmpSyncList(List<SupplierEmpSync> supplierEmpSyncList) {
        this.supplierEmpSyncList = supplierEmpSyncList;
    }

    public List<SupplierMasterSync> getSupplierMasterSyncList() {
        return supplierMasterSyncList;
    }

    public void setSupplierMasterSyncList(List<SupplierMasterSync> supplierMasterSyncList) {
        this.supplierMasterSyncList = supplierMasterSyncList;
    }

    public List<PosMaterSync> getPosList() {
        return posList;
    }

    public void setPosList(List<PosMaterSync> posList) {
        this.posList = posList;
    }

    public List<AssetMasterSync> getAssetMasterSyncList() {
        return assetMasterSyncList;
    }

    public void setAssetMasterSyncList(List<AssetMasterSync> assetMasterSyncList) {
        this.assetMasterSyncList = assetMasterSyncList;
    }

    @Override
    public String toString() {
        return Region;
    }
}
