package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.ApplicationSaveDraft;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 20/06/16.
 */
public class LoginResponse {

    @SerializedName("role")
    private String role;
    @SerializedName("token")
    private String token;
    @SerializedName("aoid")
    private String aoid;
    @SerializedName("AOSalesStatus")
    private String AOSalesStatus;
    @SerializedName("fcm_token")
    private String FcmToken;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("message")
    private String message;
    @SerializedName("email")
    private String email;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("username")
    private String username;
    @SerializedName("avatar_url")
    private String urlPhoto;
    @SerializedName("brachCode")
    private String masterBranchCode;
    @SerializedName("address")
    private String address;
    @SerializedName("IsFirstLogin")
    private int isFirstLogin;
    @SerializedName("Application")
    private List<ApplicationSaveDraft> applicationList;
    @SerializedName("status")
    private String status;

    @SerializedName("BranchLocking")
    private boolean branchLocking;


    public String getAOSalesStatus() {
        return AOSalesStatus;
    }

    public void setAOSalesStatus(String AOSalesStatus) {
        this.AOSalesStatus = AOSalesStatus;
    }


    public boolean isBranchLocking() {
        return branchLocking;
    }

    public void setBranchLocking(boolean branchLocking) {
        this.branchLocking = branchLocking;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getMasterBranchCode() {
        return masterBranchCode;
    }

    public void setMasterBranchCode(String masterBranchCode) {
        this.masterBranchCode = masterBranchCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(int isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public List<ApplicationSaveDraft> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<ApplicationSaveDraft> applicationList) {
        this.applicationList = applicationList;
    }

    public String getAoid() {
        return aoid;
    }

    public void setAoid(String aoid) {
        this.aoid = aoid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFcmToken() {
        return FcmToken;
    }

    public void setFcmToken(String fcmToken) {
        FcmToken = fcmToken;
    }
}
