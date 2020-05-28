package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.CheckLocationMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckLocationPresenter implements BasePresenter<CheckLocationMvpView> {
    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private CheckLocationMvpView mvpView;
    private Call<BaseResponse> call;
    private int count;

    public CheckLocationPresenter()  {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(CheckLocationMvpView view) {
        mvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mvpView = null;
    }

    public void checkUserLocation(String token, double longitude, double latitude){
        mvpView.onPreCheckLocation();
        count = 0;
        call = mApiService.userCheckLocation(token,longitude,latitude);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                count = 0;
                if (response.isSuccessful()){
                    mvpView.onSuccessCheckLocation(response.body());
                }
                else {
                    mvpView.onFailedCheckLocation(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mvpView != null) {
                            mvpView.onFailedCheckLocation(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
        }
    }

