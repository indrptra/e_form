package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.FaqObjt;
import com.kreditplus.eform.model.response.objecthelper.Syarat;
import com.kreditplus.eform.model.response.objecthelper.TidakCancel;

import java.util.List;

/**
 * Created by apc-lap012 on 01/03/17.
 */

public class SyaratDanKetentuanResponse {

    @SerializedName("syarat")
    private Syarat syarats;

    @SerializedName("tidakCancel")
    private  TidakCancel tidakCancels;

    @SerializedName("faq")
    private FaqObjt faqObjt;

    public Syarat getSyarats() {
        return syarats;
    }

    public void setSyarats(Syarat syarats) {
        this.syarats = syarats;
    }

    public TidakCancel getTidakCancels() {
        return tidakCancels;
    }

    public void setTidakCancels(TidakCancel tidakCancels) {
        this.tidakCancels = tidakCancels;
    }

    public FaqObjt getFaqObjt() {
        return faqObjt;
    }

    public void setFaqObjt(FaqObjt faqObjt) {
        this.faqObjt = faqObjt;
    }
}
