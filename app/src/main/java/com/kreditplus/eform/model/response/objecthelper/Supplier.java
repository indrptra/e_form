package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class Supplier {

    @SerializedName("SupplierID")
    private String supplierId;
    @SerializedName("SupplierName")
    private String name;
    @SerializedName("BankAccounts")
    private List<BankAccount> bankAccounts;
    @SerializedName("Marketings")
    private List<Marketing> marketings;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<Marketing> getMarketings() {
        return marketings;
    }

    public void setMarketings(List<Marketing> marketings) {
        this.marketings = marketings;
    }

    @Override
    public String toString() {
        return name;
    }
}
