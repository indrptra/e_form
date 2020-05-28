package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.SupplierResponse;

public interface SearchSupplierMasterMvpView extends BaseMvpView {
    void onPreSearchSupplierMaster();

    void onSuccessSearchSupplierMaster(SupplierResponse data);

    void onFailedSearchSupplierMaster(String message);
}
