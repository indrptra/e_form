package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.ProductOfferingResponse;

public interface SearchProductOfferingMvpView extends BaseMvpView {
    void onPreSearchProductOffering();

    void onSuccessSearchProductOffering(ProductOfferingResponse data);

    void onFailedSearchProductOffering(String message);
}
