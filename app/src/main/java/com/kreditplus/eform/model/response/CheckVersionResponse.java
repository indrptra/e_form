package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Industri;

import java.util.List;

/**
 * Created by apc-lap012 on 15/06/17.
 */

public class CheckVersionResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
