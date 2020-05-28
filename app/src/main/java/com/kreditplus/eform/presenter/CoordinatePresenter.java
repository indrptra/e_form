package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.CodePersetujuanResponse;
import com.kreditplus.eform.model.response.CoordinateResponse;
import com.kreditplus.eform.model.response.ProfilResponse;
import com.kreditplus.eform.model.response.SendCodeResponse;
import com.kreditplus.eform.model.response.objecthelper.AssetToken;
import com.kreditplus.eform.model.response.objecthelper.BodyToken;
import com.kreditplus.eform.presenter.mvpview.CodeSignatureMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.ProfilMvpView;
import com.kreditplus.eform.service.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nurirppan on 12/27/2017.
 */

public class CoordinatePresenter implements BasePresenter<CoordinateMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private CoordinateMvpView mCoordinateMvpView;
    private Call<BaseResponse<CoordinateResponse>> callCoordinate;
    private int count;

    public CoordinatePresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(CoordinateMvpView view) {
        mCoordinateMvpView = view;
    }

    @Override
    public void detachView() {
        mCoordinateMvpView = null;
    }

    public void coordinate(String token, double latitude, double longitude, String action) {
        Map<String, RequestBody> map = new HashMap<>();
        mCoordinateMvpView.onPreCoordinate();
        count = 0;

        map.put("latitude", Util.toTextRequestBody(String.valueOf(latitude)));
        map.put("longitude", Util.toTextRequestBody(String.valueOf(longitude)));
        map.put("action", Util.toTextRequestBody(String.valueOf(action)));

        callCoordinate = mApiService.coordinate(token, map);
        callCoordinate.enqueue(new Callback<BaseResponse<CoordinateResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<CoordinateResponse>> call, Response<BaseResponse<CoordinateResponse>> response) {
                if (mCoordinateMvpView != null) {
                    if (response.isSuccessful()) {
                    } else if (response.code() == 403) {
                        mCoordinateMvpView.onTokenCoordinateExpired();
                    } else if (response.code() == 422) {
                        mCoordinateMvpView.onFailedCoordinate(mErrorRetrofitUtil.parseError(response));
                    } else {
                        mCoordinateMvpView.onFailedCoordinate(mErrorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CoordinateResponse>> call, Throwable e) {
                if (!callCoordinate.isCanceled()) {
                    if (count < 3) {
                        callCoordinate.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mCoordinateMvpView != null)
                            mCoordinateMvpView.onFailedCoordinate(mErrorRetrofitUtil.responseFailedError(e));
                    }
                }
            }
        });
    }
}
