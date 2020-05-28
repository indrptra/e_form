package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apc-lap012 on 08/02/17.
 */

public class SendSaveDraftResponse {

    @SerializedName("id")
    private List<String> idStringList;

    public List<String> getIdStringList() {
        return idStringList;
    }

    public void setIdStringList(List<String> idStringList) {
        this.idStringList = idStringList;
    }
}
