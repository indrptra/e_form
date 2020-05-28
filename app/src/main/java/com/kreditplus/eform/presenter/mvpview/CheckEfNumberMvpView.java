package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;

public interface CheckEfNumberMvpView extends BaseMvpView{
    void onPreCheckEFNumber();
    void onSuccessCheckEFNumber(CheckEfNumberResponse mCheckEfNumberResponse, MasterFormPengajuan mMasterFormPengajuan);
    void onFailedCheckEFNumber(String message);
    void onTokenCheckEFNumber();
}
