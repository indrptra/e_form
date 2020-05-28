package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nurirppan on 09-Mar-18.
 */

public class RecomendationObjt {
    @SerializedName("id")
    private String id;

    @SerializedName("recomendation")
    private String recomendation;

    public RecomendationObjt(String id, String recomendation) {
        this.id = id;
        this.recomendation = recomendation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(String recomendation) {
        this.recomendation = recomendation;
    }

    @Override
    public String toString() {
        return recomendation;
    }

}
