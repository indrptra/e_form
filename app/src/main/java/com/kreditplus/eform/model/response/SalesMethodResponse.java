package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.SalesMethod;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 26/12/16.
 */

public class SalesMethodResponse {

    @SerializedName("source_applications")
    private List<SalesMethod> salesMethodList;

    public List<SalesMethod> getSalesMethodList() {
        return salesMethodList;
    }

    public void setSalesMethodList(List<SalesMethod> salesMethodList) {
        this.salesMethodList = salesMethodList;
    }
}
