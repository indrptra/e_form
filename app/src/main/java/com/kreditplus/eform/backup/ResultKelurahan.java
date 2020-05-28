package com.kreditplus.eform.backup;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 21/02/17.
 */
@DatabaseTable(tableName = "resultKelurahan")
public class ResultKelurahan {

    @DatabaseField(generatedId = true, columnName = "id")
    private long id;

    @DatabaseField(columnName = "result")
    private String result;

    @DatabaseField(columnName = "ZipCode")
    private int zipcode;

    @DatabaseField(columnName = "Kelurahan")
    private  String kelurahan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }
}
