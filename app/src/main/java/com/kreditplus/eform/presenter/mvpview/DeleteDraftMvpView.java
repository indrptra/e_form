package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.DetailPerhitunganKendaraanResponse;

public interface DeleteDraftMvpView extends BaseMvpView {
    void onPreDeleteDraft();

    void onSuccessDeleteDraft();

    void onFailedDeleteDraft(String message);
}