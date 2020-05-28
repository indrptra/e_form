package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.ProductOfferingResponse;
import com.kreditplus.eform.presenter.mvpview.SearchProductOfferingMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductOfferingPresenter implements BasePresenter<SearchProductOfferingMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SearchProductOfferingMvpView searchProductOfferingMvpView;
    private Call<BaseResponse<ProductOfferingResponse>> call;
    private int count;

    public SearchProductOfferingPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SearchProductOfferingMvpView view) {
        searchProductOfferingMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        searchProductOfferingMvpView = null;
    }

    public void getSearchProductOffering(String token,
                                         String value,
                                         String assetType,
                                         String supplierId,
                                         String branchId,
                                         String custType,
                                         String appType,
                                         String AOSalesStatus) {
        count = 0;
        searchProductOfferingMvpView.onPreSearchProductOffering();
        call = apiService.searchProductOfferingMaster(token, "ProductOffMaster", value, assetType, supplierId, branchId,custType, appType, AOSalesStatus,"2");
        call.enqueue(new Callback<BaseResponse<ProductOfferingResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProductOfferingResponse>> call, Response<BaseResponse<ProductOfferingResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    searchProductOfferingMvpView.onSuccessSearchProductOffering(response.body().getData());
                } else {
                    searchProductOfferingMvpView.onFailedSearchProductOffering(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductOfferingResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (searchProductOfferingMvpView != null) {
                            searchProductOfferingMvpView.onFailedSearchProductOffering(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
