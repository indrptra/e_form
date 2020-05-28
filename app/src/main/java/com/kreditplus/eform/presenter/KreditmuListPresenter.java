package com.kreditplus.eform.presenter;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.KreditmuListResponse;
import com.kreditplus.eform.presenter.mvpview.KreditmuListMvpView;
import com.kreditplus.eform.service.ApiService;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 23/08/17.
 */

public class KreditmuListPresenter implements BasePresenter<KreditmuListMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private KreditmuListMvpView mKreditmuListMvpView;
    private Call<BaseResponse<KreditmuListResponse>> call;
    private int count;

    public KreditmuListPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(KreditmuListMvpView view) {
        mKreditmuListMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) {
            call.cancel();
        }
        mKreditmuListMvpView = null;
    }

    public void dataKreditmu(String token) {
        mKreditmuListMvpView.onPreLoadKreditmuList();
        Map<String, RequestBody> map = new HashMap<>();
        count = 0;

        map.put("startDate", Util.toTextRequestBody("01/01/1970"));
        map.put("endDate", Util.toTextRequestBody(Util.kreditmuListTimeFormat()));
        call = apiService.dataKreditmu(token,map);
        call.enqueue(new Callback<BaseResponse<KreditmuListResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<KreditmuListResponse>> call, Response<BaseResponse<KreditmuListResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mKreditmuListMvpView.onSuccessLoadKreditmuList(response.body().getData().getKreditmuListList());
                } else if (response.code() == 403) {
                    mKreditmuListMvpView.onTokenKreditmuListExpired();
                } else {
                    mKreditmuListMvpView.onFailedLoadKreditmuList(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<KreditmuListResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (mKreditmuListMvpView != null) {
                            mKreditmuListMvpView.onFailedLoadKreditmuList(errorRetrofitUtil.responseFailedError(t));
                            Crashlytics.logException(t);
                            count = 0;
                        }
                    }
                }
            }
        });
    }
}
