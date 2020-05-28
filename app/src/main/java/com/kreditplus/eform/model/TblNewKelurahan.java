package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tblNewKelurahan")
public class TblNewKelurahan {

    @DatabaseField(columnName = "City")
    private String City;
    @DatabaseField(columnName = "Kelurahan")
    private String Kelurahan;
    @DatabaseField(columnName = "Kecamatan")
    private String Kecamatan;
    @DatabaseField(columnName = "ZipCode")
    private String ZipCode;
    @DatabaseField(columnName = "IsActive")
    private String IsActive;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getKelurahan() {
        return Kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        Kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
