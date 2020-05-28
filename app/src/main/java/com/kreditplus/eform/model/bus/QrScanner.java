package com.kreditplus.eform.model.bus;

/**
 * Created by apc-lap012 on 27/07/17.
 */

public class QrScanner {

    private String result;
    private String appId;

    public QrScanner(String result, String appId) {
        this.result = result;
        this.appId = appId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
