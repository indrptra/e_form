package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.ApplicationDetailResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.PengajuanDetailMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengajuanDetailPresenter implements BasePresenter<PengajuanDetailMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private PengajuanDetailMvpView mPengajuanDetailMvpView;
    private Call<BaseResponse<ApplicationDetailResponse>> call;
    private int count;

    public PengajuanDetailPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(PengajuanDetailMvpView view) {
        mPengajuanDetailMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mPengajuanDetailMvpView = null;
    }

    public void loadPengajuanDetail(String token, String id) {
        mPengajuanDetailMvpView.onPreSubmitPengajuanEditLoad();
        count = 0;
        call = apiService.applicationDetail(token, id);
        call.enqueue(new Callback<BaseResponse<ApplicationDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ApplicationDetailResponse>> call, Response<BaseResponse<ApplicationDetailResponse>> response) {
                count = 0;
                if (mPengajuanDetailMvpView != null) {
                    if (response.isSuccessful()) {
                        mPengajuanDetailMvpView.onSuccessSubmitPengajuanEditLoad(response.body().getData().getApplication());
                    } else if (response.code() == 403) {
                        mPengajuanDetailMvpView.onDetailTokenExpired();
                    } else {
                        mPengajuanDetailMvpView.onFailedSubmitPengajuanEditLoad(errorRetrofitUtil.
                                parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ApplicationDetailResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mPengajuanDetailMvpView != null) {
                            mPengajuanDetailMvpView.onFailedSubmitPengajuanEditLoad(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
