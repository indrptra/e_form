package com.kreditplus.eform.model.ArrayList;

public class JenisKendaraanArrayList {
    private String CategoryID;
    private String Description;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public JenisKendaraanArrayList(String categoryID, String description) {
        CategoryID = categoryID;
        Description = description;
    }

    @Override
    public String toString() {
        return Description;
    }


}
