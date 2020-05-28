package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.model.response.objecthelper.CekKodeProgramObjct;
import com.kreditplus.eform.presenter.mvpview.CekKodeProgramMvpView;
import com.kreditplus.eform.presenter.mvpview.PosKreditvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekKodeProgramPresenter implements BasePresenter<CekKodeProgramMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private CekKodeProgramMvpView cekKodeProgramMvpView;
    private Call<BaseResponse<CekKodeProgramObjct>> call;
    private int count;

    public CekKodeProgramPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(CekKodeProgramMvpView view) {
        cekKodeProgramMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        cekKodeProgramMvpView = null;
    }

    public void checkKodeProgram(String token,
                             String poId) {
        count = 0;
        cekKodeProgramMvpView.onPreCekKodeProgram();
        call = apiService.cekKodeProgram(token, poId);
        call.enqueue(new Callback<BaseResponse<CekKodeProgramObjct>>() {
            @Override
            public void onResponse(Call<BaseResponse<CekKodeProgramObjct>> call, Response<BaseResponse<CekKodeProgramObjct>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    cekKodeProgramMvpView.onSuccessCekKodeProgram(response.body().getData());
                } else {
                    cekKodeProgramMvpView.onFailedCekKodeProgram(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CekKodeProgramObjct>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (cekKodeProgramMvpView != null) {
                            cekKodeProgramMvpView.onFailedCekKodeProgram(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }
}
