package com.kreditplus.eform.model.ArrayList;

public class TipePengajuanArrayList {
    private String type;

    public TipePengajuanArrayList(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
