package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.ProductOffTenorResponse;
import com.kreditplus.eform.presenter.mvpview.ProductOffTenorMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOffTenorPresenter implements BasePresenter<ProductOffTenorMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private ProductOffTenorMvpView productOffTenorMvpView;
    private Call<BaseResponse<ProductOffTenorResponse>> call;
    private int count;

    public ProductOffTenorPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(ProductOffTenorMvpView view) {
        productOffTenorMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        productOffTenorMvpView = null;
    }

    public void getProductOffTenor(String token,
                                   String prodcutOffId,
                                   String branchId) {
        count = 0;
        productOffTenorMvpView.onPreProductOffTenor();
        call = apiService.getProductOfferingTenor(token, prodcutOffId, "ProductOffTenor", branchId);
        call.enqueue(new Callback<BaseResponse<ProductOffTenorResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProductOffTenorResponse>> call, Response<BaseResponse<ProductOffTenorResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    productOffTenorMvpView.onSuccessProductOffTenor(response.body().getData());
                } else {
                    productOffTenorMvpView.onFailedProductOffTenor(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductOffTenorResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (productOffTenorMvpView != null) {
                            productOffTenorMvpView.onFailedProductOffTenor(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
