package com.kreditplus.eform.model.ArrayList;

public class JenisPembiayaanArrayList {
    private String valueCode;
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

    public JenisPembiayaanArrayList(String valueCode, String value) {
        this.valueCode = valueCode;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
