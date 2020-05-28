package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nurirppan on 12/27/2017.
 */

public class CoordinateResponse {

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
