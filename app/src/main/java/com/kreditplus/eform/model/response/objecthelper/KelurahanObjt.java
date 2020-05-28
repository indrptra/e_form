package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 20/02/17.
 */

public class KelurahanObjt {

    @SerializedName("City")
    private String city;

    @SerializedName("Kecamatan")
    private String kecamatan;

    @SerializedName("Kelurahan")
    private String kelurahan;

    @SerializedName("ZipCode")
    private String zipCode;

    @SerializedName("IsActive")
    private String isactive;

    public KelurahanObjt(String city, String kecamatan, String kelurahan, String zipCode) {
        this.city = city;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.zipCode = zipCode;
    }

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    @Override
    public String toString() {
        return city + " , " + kecamatan + " , " + kelurahan + " , " + zipCode;
    }
}
