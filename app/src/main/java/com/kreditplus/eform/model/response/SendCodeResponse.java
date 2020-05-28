package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 27/03/17.
 */

public class SendCodeResponse {

    @SerializedName("count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
