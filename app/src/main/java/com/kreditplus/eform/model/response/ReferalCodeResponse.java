package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

public class ReferalCodeResponse {
    @SerializedName("refferal_code")
    String refferal_code;

    @SerializedName("lob")
    String lob;

    @SerializedName("branch_id")
    String branch_id;

    @SerializedName("id_supplier")
    String id_supplier;

    @SerializedName("supplier_name")
    String supplier_name;

    @SerializedName("fullname")
    String fullname;

    @SerializedName("id_number")
    String id_number;

    @SerializedName("phone")
    String phone;

    @SerializedName("bank")
    String bank;

    @SerializedName("account_number")
    String account_number;

    @SerializedName("account_owner_name")
    String account_owner_name;


    @SerializedName("is_active")
    boolean is_active;

    public String getRefferal_code() {
        return refferal_code;
    }

    public void setRefferal_code(String refferal_code) {
        this.refferal_code = refferal_code;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_owner_name() {
        return account_owner_name;
    }

    public void setAccount_owner_name(String account_owner_name) {
        this.account_owner_name = account_owner_name;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

}
