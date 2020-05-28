package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.ProfilResponse;
import com.kreditplus.eform.model.response.objecthelper.BodyProfile;
import com.kreditplus.eform.presenter.mvpview.ProfilMvpView;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class ProfilPresenter implements BasePresenter<ProfilMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    DatabaseService databaseService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private ProfilMvpView mProfilMvpView;
    private Call<BaseResponse<ProfilResponse>> callProfil;
    private File compres;
    private int count;

    public ProfilPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(ProfilMvpView view) {
        mProfilMvpView = view;
    }

    @Override
    public void detachView() {
        mProfilMvpView = null;
    }

    public void updateProfile(String token, String firstName, String lastName, String address, String email, File path1, File path2) {
        mProfilMvpView.onPreLoadProfil();
        count = 0;
        Map<String, RequestBody> map = new HashMap<>();

        map.put("email", Util.toTextRequestBody(email));
        map.put("username", Util.toTextRequestBody("djubaedah"));
        map.put("first_name", Util.toTextRequestBody(firstName));
        map.put("last_name", Util.toTextRequestBody(lastName));
        map.put("address", Util.toTextRequestBody(address));


        try {
            compres = new Compressor(mProfilMvpView.getContext())
                    .setMaxHeight(720)
                    .setMaxWidth(1080)
                    .setQuality(25)
                    .compressToFile(path1 == null ? path2 : path1);
        } catch (IOException e) {
            if (BuildConfig.DEBUG)
                Log.e("Gagal Kompress", e.getMessage());
            Crashlytics.logException(e);
            mProfilMvpView.onFailedLoadProfil(mProfilMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
            return;
        }
        MultipartBody.Part photoPart = prepareFile("avatar", compres);

        callProfil = mApiService.updateProfile(token, map, photoPart);
        callProfil.enqueue(new Callback<BaseResponse<ProfilResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProfilResponse>> call, Response<BaseResponse<ProfilResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mProfilMvpView.onSuccessLoadProfil(response.body().getMeta().getMessage());
                } else if (response.code() == 403) {
                    mProfilMvpView.onTokenProfilExpired();
                } else if (response.code() == 422) {
                    mProfilMvpView.onFailedLoadProfil(errorRetrofitUtil.parseError(response));
                } else {
                    mProfilMvpView.onFailedLoadProfil(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProfilResponse>> call, Throwable e) {
                if (!callProfil.isCanceled()) {
                    if (count < 3) {
                        callProfil.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mProfilMvpView != null)
                            mProfilMvpView.onFailedLoadProfil(errorRetrofitUtil.responseFailedError(e));
                    }
                }
            }
        });

    }

    public void updatePassword(String token, String name, String newPassword) {
        HashMap<String, RequestBody> mapUpdate = new HashMap<>();
        count = 0;
        mProfilMvpView.onPreLoadProfil();

        mapUpdate.put("username", Util.toTextRequestBody(name));
        mapUpdate.put("password", Util.toTextRequestBody(newPassword));

        callProfil = mApiService.updatePassword(token, mapUpdate);
        callProfil.enqueue(new Callback<BaseResponse<ProfilResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProfilResponse>> call, Response<BaseResponse<ProfilResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mProfilMvpView.onSuccessLoadProfil(response.body().getMeta().getMessage());
                } else if (response.code() == 403) {
                    mProfilMvpView.onTokenProfilExpired();
                } else if (response.code() == 422) {
                    mProfilMvpView.onFailedLoadProfil(errorRetrofitUtil.parseError(response));
                } else {
                    mProfilMvpView.onFailedLoadProfil(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProfilResponse>> call, Throwable t) {
                if (!callProfil.isCanceled()) {
                    if (count < 3) {
                        callProfil.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mProfilMvpView != null)
                            mProfilMvpView.onFailedLoadProfil(errorRetrofitUtil.responseFailedError(t));
                    }
                }
            }
        });
    }

    @Nonnull
    private MultipartBody.Part prepareFile(String partName, File file) {
        RequestBody requestBody = Util.toImageRequestBody(file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}
