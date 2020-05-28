package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 03/08/16.
 */
public class RefreshTokenResponse {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
