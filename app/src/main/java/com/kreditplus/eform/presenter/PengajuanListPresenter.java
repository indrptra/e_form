package com.kreditplus.eform.presenter;

import android.util.Log;

import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.PengajuanResponse;
import com.kreditplus.eform.presenter.mvpview.PengajuanListMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengajuanListPresenter implements BasePresenter<PengajuanListMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private PengajuanListMvpView mPengajuanListMvpView;
    private Call<BaseResponse<PengajuanResponse>> call;
    private int count;

    public PengajuanListPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(PengajuanListMvpView view) {
        mPengajuanListMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mPengajuanListMvpView = null;
    }

    public void pengajuanList(String token, int offset, int limit, String filter, int filterValue, String dateSubmit, String search, Map<String, String> map, String type, String isDraft) {
        if (mPengajuanListMvpView != null) {
            mPengajuanListMvpView.onPreLoadPengajuan();
        }
        count = 0;
        call = apiService.pengajuan(token, offset, limit, filter, filterValue, search, dateSubmit, type, map, isDraft);
        call.enqueue(new Callback<BaseResponse<PengajuanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PengajuanResponse>> call, Response<BaseResponse<PengajuanResponse>> response) {
                if (mPengajuanListMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        mPengajuanListMvpView.onSuccessLoadPengajuan(response.body().getData().getTotalData(), response.body().getData().getPengajuanList());
                    } else if (response.code() == 403) {
                        mPengajuanListMvpView.onTokenExpired();
                    } else {
                        mPengajuanListMvpView.onFailedLoadPengajuan(errorRetrofitUtil.
                                parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PengajuanResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (BuildConfig.DEBUG) {
                            Log.e("counting failed hit api", String.valueOf(count));
                        }
                        if (mPengajuanListMvpView != null) {
                            mPengajuanListMvpView.onFailedLoadPengajuan(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }
                }
            }
        });
    }
}
