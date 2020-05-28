package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.DetailPerhitunganWhiteGoodsResponse;
import com.kreditplus.eform.presenter.mvpview.PerhitunganWhiteGoodsMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerhitunganWhiteGoodsPresenter implements BasePresenter<PerhitunganWhiteGoodsMvpView>{
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private PerhitunganWhiteGoodsMvpView mvpView;
    private Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> call;
    private int count;

    public PerhitunganWhiteGoodsPresenter(){
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(PerhitunganWhiteGoodsMvpView view) {
        mvpView = view;
    }

    @Override
    public void detachView() {
        if (call!=null)
            call.cancel();
        mvpView = null;
    }

    public void getPerhitunganWGPremiOtomatis(String token,
                                 long otr_price,
                                 int tenor,
                                 long down_payment,
                                 long discount,
                                 long admin_fee,
                                 float intrest,
                                 String type, String bebasBunga,
                                              int dpPercentage){
        count = 0;
        mvpView.onPrePerhitunganWhiteGoods();
        call = apiService.getPerhitunganWGPremiOtomatis(token,otr_price,tenor,down_payment,discount,admin_fee,intrest,type,bebasBunga,dpPercentage);
        call.enqueue(new Callback<BaseResponse<DetailPerhitunganWhiteGoodsResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> call, Response<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mvpView.onSuccessGetPerhitunganWhiteGoods(response.body().getData());
                } else {
                    mvpView.onFailedGetPerhitunganWhiteGoods(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (mvpView != null) {
                            mvpView.onFailedGetPerhitunganWhiteGoods(errorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }
    public void getPerhitunganWGPremiManual(String token,
                                              long otr_price,
                                              int tenor,
                                              long down_payment,
                                              long discount,
                                              long admin_fee,
                                              float intrest,
                                              String type,
                                              int asuransi,
                                            String bebasBunga,
                                            int dpPercentage){
        count = 0;
        mvpView.onPrePerhitunganWhiteGoods();
        call = apiService.getPerhitunganWGPremiManual(token,otr_price,tenor,down_payment,discount,admin_fee,intrest,type,asuransi,bebasBunga,dpPercentage);
        call.enqueue(new Callback<BaseResponse<DetailPerhitunganWhiteGoodsResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> call, Response<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mvpView.onSuccessGetPerhitunganWhiteGoods(response.body().getData());
                } else {
                    mvpView.onFailedGetPerhitunganWhiteGoods(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (mvpView != null) {
                            mvpView.onFailedGetPerhitunganWhiteGoods(errorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }
}
