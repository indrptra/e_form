package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by apc-lap012 on 29/03/17.
 */

public class BodyProfile {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("address")
    private String address;

    @SerializedName("avatar")
    private MultipartBody.Part avatar;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MultipartBody.Part getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartBody.Part avatar) {
        this.avatar = avatar;
    }
}
