package com.kreditplus.eform.model.ArrayList;

public class RekomendasiArrayList {
    private String id;
    private String recomendation;

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

    public RekomendasiArrayList(String id, String recomendation) {
        this.id = id;
        this.recomendation = recomendation;
    }

    @Override
    public String toString() {
        return recomendation;
    }
}
