package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.RecomendationResponse;
import com.kreditplus.eform.model.response.objecthelper.RecomendationObjt;

import java.util.List;

/**
 * Created by nurirppan on 08-Mar-18.
 */

public interface RecomendationMvpView extends BaseMvpView{
    void onPreRecomendation();
    void onSuccessRecomendation(RecomendationResponse recomendationResponse);
    void onFailedRecomendation(String message);
    void onTokenExpiredRecomendation();
    void onCheckFpd();
    void onCheckFpdFailed(String message);
}
