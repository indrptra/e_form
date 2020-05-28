package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 14/07/16.
 */
public class Profession {

    @SerializedName("ProfessionID")
    private String id;
    @SerializedName("Description")
    private String description;
    @SerializedName("IsActive")
    private String isActive;

    public Profession(String id, String description, String isActive) {
        this.id = id;
        this.description = description;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return description;
    }
}
