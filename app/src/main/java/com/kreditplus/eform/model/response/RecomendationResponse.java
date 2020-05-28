package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.RecomendationObjt;

import java.util.List;

/**
 * Created by nurirppan on 08-Mar-18.
 */

public class RecomendationResponse extends BaseResponse{

    @SerializedName("recomendation")
    private List<RecomendationObjt> recomendationObjtList;

    public List<RecomendationObjt> getRecomendationObjtList() {
        return recomendationObjtList;
    }

    public void setRecomendationObjtList(List<RecomendationObjt> recomendationObjtList) {
        this.recomendationObjtList = recomendationObjtList;
    }
}
