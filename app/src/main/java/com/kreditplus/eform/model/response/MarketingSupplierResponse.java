package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierEmpSync;

import java.util.List;

public class MarketingSupplierResponse {
    @SerializedName("SupplierEmp")
    private List<SupplierEmpSync> marketingSupplierList;

    public List<SupplierEmpSync> getMarketingSupplierList() {
        return marketingSupplierList;
    }

    public void setMarketingSupplierList(List<SupplierEmpSync> marketingSupplierList) {
        this.marketingSupplierList = marketingSupplierList;
    }
}
