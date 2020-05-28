package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 22/02/17.
 */
@DatabaseTable(tableName = "DataFinansial")
public class DataFinansial {

    @DatabaseField(columnName = "ProductInsurancePersen")
    private String productInsurancePersen;
    @DatabaseField(columnName = "ProductInsuranceNominal")
    private int productInsuranceNominal;
    @DatabaseField(columnName = "PeopleInsurancePersen")
    private String peopleInsurancePersen;
    @DatabaseField(columnName = "PeopleInsuranceNominal")
    private int peopleInsuranceNominal;
    @DatabaseField(columnName = "TotalInsurancePersen")
    private String totalInsurancePersen;
    @DatabaseField(columnName = "TotalInsuranceNominal")
    private String totalInsuranceNominal;
    @DatabaseField(columnName = "AdminFeeLainnya")
    private String adminFeeLainnya;

    public String getProductInsurancePersen() {
        return productInsurancePersen;
    }

    public void setProductInsurancePersen(String productInsurancePersen) {
        this.productInsurancePersen = productInsurancePersen;
    }

    public int getProductInsuranceNominal() {
        return productInsuranceNominal;
    }

    public void setProductInsuranceNominal(int productInsuranceNominal) {
        this.productInsuranceNominal = productInsuranceNominal;
    }

    public String getPeopleInsurancePersen() {
        return peopleInsurancePersen;
    }

    public void setPeopleInsurancePersen(String peopleInsurancePersen) {
        this.peopleInsurancePersen = peopleInsurancePersen;
    }

    public int getPeopleInsuranceNominal() {
        return peopleInsuranceNominal;
    }

    public void setPeopleInsuranceNominal(int peopleInsuranceNominal) {
        this.peopleInsuranceNominal = peopleInsuranceNominal;
    }

    public String getTotalInsurancePersen() {
        return totalInsurancePersen;
    }

    public void setTotalInsurancePersen(String totalInsurancePersen) {
        this.totalInsurancePersen = totalInsurancePersen;
    }

    public String getTotalInsuranceNominal() {
        return totalInsuranceNominal;
    }

    public void setTotalInsuranceNominal(String totalInsuranceNominal) {
        this.totalInsuranceNominal = totalInsuranceNominal;
    }

    public String getAdminFeeLainnya() {
        return adminFeeLainnya;
    }

    public void setAdminFeeLainnya(String adminFeeLainnya) {
        this.adminFeeLainnya = adminFeeLainnya;
    }
}
