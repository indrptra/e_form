package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

public class POSMasterObjct {

    @SerializedName("POSID")
    private String POSID;
    @SerializedName("POSName")
    private String POSName;

    public String getPOSID() {
        return POSID;
    }

    public void setPOSID(String POSID) {
        this.POSID = POSID;
    }

    public String getPOSName() {
        return POSName;
    }

    public void setPOSName(String POSName) {
        this.POSName = POSName;
    }
}
