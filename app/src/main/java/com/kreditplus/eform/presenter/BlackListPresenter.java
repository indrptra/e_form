package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.BlackListResponse;
import com.kreditplus.eform.model.response.objecthelper.ApplicationBlacklist;
import com.kreditplus.eform.presenter.mvpview.BlackListMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 04/10/16.
 */

public class BlackListPresenter implements BasePresenter<BlackListMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private BlackListMvpView mBlackListMvpView;
    private Call<BaseResponse<BlackListResponse>> call;
    private int count;

    public BlackListPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(BlackListMvpView view) {
        mBlackListMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mBlackListMvpView = null;
    }

    public void blackList(String token, String dateBirth, String noKtp, String handphone, String offeringType, String branchUser, String namaLegal, String namaIbu, String isEdit) {
        mBlackListMvpView.onPreBlackList();
        count = 0;
//        if(BuildConfig.FLAVOR.equals("staging")){
            call = apiService.blackListNew(token, dateBirth, noKtp, handphone, offeringType, branchUser, namaLegal, namaIbu, isEdit);
        /*}else{
            call = apiService.blackList(token, dateBirth, noKtp, handphone, offeringType, branchUser);
        }*/
        call.enqueue(new Callback<BaseResponse<BlackListResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<BlackListResponse>> call, Response<BaseResponse<BlackListResponse>> response) {
                if (mBlackListMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        mBlackListMvpView.onSuccessBlackList(response.body().getData(),
                                response.body().getData().getFullNames(),
                                response.body().getData().getApplicationBlacklists());
                    } else if (response.code() == 403) {
                        mBlackListMvpView.onTokenBlackListExpired();
                    } else {
                        mBlackListMvpView.onFailedBlackList(errorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BlackListResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        if (mBlackListMvpView != null){
                            mBlackListMvpView.onFailedBlackList(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }
                }
            }
        });

    }
}
