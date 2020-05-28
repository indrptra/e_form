package com.kreditplus.eform.helper;

import com.kreditplus.eform.BuildConfig;

/**
 * Created by apc-lap012 on 26/09/17.
 */

public class JniUtil {

    static {
        System.loadLibrary("Constant");
    }

    public String apiUrl(){
        String API_PROD = "production";
        String API_STAG = "staging";
        String API_UAT = "uat";

        if (BuildConfig.FLAVOR.equalsIgnoreCase(API_STAG)){
            return getApiUrlStaging();
        }else if (BuildConfig.FLAVOR.equalsIgnoreCase(API_UAT)) {
            return getApiUrlUAT();
        }else if (BuildConfig.FLAVOR.equalsIgnoreCase(API_PROD)) {
            return getApiUrlProduction();
        }else{
            throw new IllegalStateException("Invalid Build Flavor");
        }
    }

    private native String getApiUrlStaging();
    private native String getApiUrlUAT();
    private native String getApiUrlProduction();


}



