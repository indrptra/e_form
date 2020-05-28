package com.kreditplus.eform.model.ArrayList;

public class MerkKendaraanArrayList {
    private String Merk;

    public String getMerk() {
        return Merk;
    }

    public void setMerk(String merk) {
        Merk = merk;
    }

    public MerkKendaraanArrayList(String merk) {
        Merk = merk;
    }

    @Override
    public String toString() {
        return Merk;
    }
}
