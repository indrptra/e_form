package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.BodyProfile;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by apc-lap012 on 30/03/17.
 */

public class ProfilResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("succeded")
    private Boolean succeded;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSucceded() {
        return succeded;
    }

    public void setSucceded(Boolean succeded) {
        this.succeded = succeded;
    }
}
