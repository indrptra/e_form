package com.kreditplus.eform.backup;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 16/02/17.
 */
@DatabaseTable(tableName = "kelurahan")
public class Kelurahan {

    public static final String City = "City";
    public static final String Kecamatan = "Kecamatan";
    public static final String kelurahan1 = "Kelurahan";
    public static final String ZipCode = "ZipCode";
    public static final String IsActive = "IsActive";

    @DatabaseField(columnName = "id", generatedId = true)
    private long id;

    @DatabaseField(columnName = "City")
    private String city;

    @DatabaseField(columnName = "Kecamatan")
    private String kecamatan;

    @DatabaseField(columnName = "kelurahan")
    private String kelurahan;

    @DatabaseField(columnName = "ZipCode")
    private int zipCode;

    @DatabaseField(columnName = "IsActive")
    private String isactive;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
