package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 02/06/17.
 */

public class LogoutResponse {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
