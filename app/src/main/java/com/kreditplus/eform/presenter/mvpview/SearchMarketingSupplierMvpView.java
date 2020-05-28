package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.MarketingSupplierResponse;


public interface SearchMarketingSupplierMvpView extends BaseMvpView {
    void onPreSearchMarketingSupplier();

    void onSuccessSearchMarketingSupplier(MarketingSupplierResponse data);

    void onFailedMarketingSupplier(String message);
}
