package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 11/09/17.
 */

public class QrResponse {

    @SerializedName("IsMatch")
    private int isMatch;

    public int getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(int isMatch) {
        this.isMatch = isMatch;
    }
}
