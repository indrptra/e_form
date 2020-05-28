package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

public class JenisPembiayaanFilter {
    @SerializedName("valueCode")
    private String valueCode;
    @SerializedName("value")
    private String value;

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
