package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 15/02/17.
 */
@DatabaseTable(tableName = "tblreligion")
public class Religion {

    @DatabaseField(columnName = "ID")
    private Integer id;

    @DatabaseField(columnName = "Description")
    private String description;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
