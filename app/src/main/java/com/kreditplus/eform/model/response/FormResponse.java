package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.FormDinamic;

import java.util.List;

/**
 * Created by nurirppan on 12/13/2017.
 */

public class FormResponse{

    @SerializedName("formDynamic")
    private List<FormDinamic> formDinamic;

    public List<FormDinamic> getFormDinamic() {
        return formDinamic;
    }

    public void setFormDinamic(List<FormDinamic> formDinamic) {
        this.formDinamic = formDinamic;
    }
}
