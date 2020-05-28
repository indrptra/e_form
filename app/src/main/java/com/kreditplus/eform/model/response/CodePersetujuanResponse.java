package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 09/11/16.
 */

public class CodePersetujuanResponse {

    @SerializedName("is_valid")
    private int isValid;

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
