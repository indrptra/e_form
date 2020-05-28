package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Application;

import java.util.List;

/**
 * Created by apc-lap012 on 09/02/17.
 */

public class DetailSaveDraftResponse {
    @SerializedName("Application")
    private List<Application> applicationList;

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }
}
