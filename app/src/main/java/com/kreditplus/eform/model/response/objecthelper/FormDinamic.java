package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nurirppan on 12/13/2017.
 */
public class FormDinamic {

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("type_section")
    private String type_section;

    @SerializedName("isHide")
    private Boolean isHide;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_section() {
        return type_section;
    }

    public void setType_section(String type_section) {
        this.type_section = type_section;
    }

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }
}
