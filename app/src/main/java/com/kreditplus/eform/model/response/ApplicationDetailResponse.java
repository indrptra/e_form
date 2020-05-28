package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Application;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class ApplicationDetailResponse {

    @SerializedName("Application")
    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
