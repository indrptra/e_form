package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/10/16.
 */

public class TenorResponse {

    @SerializedName("tenors")
    private List<String> tenors;

    public List<String> getTenors() {
        return tenors;
    }

    public void setTenors(List<String> tenors) {
        this.tenors = tenors;
    }
}
