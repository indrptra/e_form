package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.DataTenor;
import com.kreditplus.eform.model.response.objecthelper.Product;
import com.kreditplus.eform.model.response.objecthelper.Supplier;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class SupplierResponse {

    @SerializedName("SupplierMaster")
    private List<Supplier> suppliers;
    @SerializedName("Products")
    private List<Product> products;
    @SerializedName("Tenors")
    private List<String> tenors;
    @SerializedName("FirstInstallment")
    private String firstInstallment;
    @SerializedName("DataTenor")
    private List<DataTenor> dataTenor;

    public List<String> getTenors() {
        return tenors;
    }

    public void setTenors(List<String> tenors) {
        this.tenors = tenors;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(String firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public List<DataTenor> getDataTenor() {
        return dataTenor;
    }

    public void setDataTenor(List<DataTenor> dataTenor) {
        this.dataTenor = dataTenor;
    }
}
