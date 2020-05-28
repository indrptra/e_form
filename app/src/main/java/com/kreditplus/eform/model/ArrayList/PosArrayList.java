package com.kreditplus.eform.model.ArrayList;

public class PosArrayList {
    private String POSID;
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

    public PosArrayList(String POSID, String POSName) {
        this.POSID = POSID;
        this.POSName = POSName;
    }

    @Override
    public String toString() {
        return POSName;
    }
}
