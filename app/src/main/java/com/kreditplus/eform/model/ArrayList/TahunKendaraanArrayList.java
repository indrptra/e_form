package com.kreditplus.eform.model.ArrayList;

public class TahunKendaraanArrayList {
    private String ManufacturingYear;

    public String getManufacturingYear() {
        return ManufacturingYear;
    }

    public void setManufacturingYear(String manufacturingYear) {
        ManufacturingYear = manufacturingYear;
    }

    public TahunKendaraanArrayList(String manufacturingYear) {
        ManufacturingYear = manufacturingYear;
    }

    @Override
    public String toString() {
        return ManufacturingYear;
    }
}
