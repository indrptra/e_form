package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.model.response.PosResponse;
import com.kreditplus.eform.model.response.objecthelper.CekKodeProgramObjct;

public interface CekKodeProgramMvpView {
    void onPreCekKodeProgram();

    void onSuccessCekKodeProgram(CekKodeProgramObjct data);

    void onFailedCekKodeProgram(String message);
}
