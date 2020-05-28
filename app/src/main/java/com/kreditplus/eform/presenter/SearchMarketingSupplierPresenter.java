package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.MarketingSupplierResponse;
import com.kreditplus.eform.model.response.objecthelper.MarketingSupplierObjt;
import com.kreditplus.eform.presenter.mvpview.SearchMarketingSupplierMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMarketingSupplierPresenter implements BasePresenter<SearchMarketingSupplierMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SearchMarketingSupplierMvpView searchMarketingSupplierMvpView;
    private Call<BaseResponse<MarketingSupplierResponse>> call;
    private int count;

    public SearchMarketingSupplierPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SearchMarketingSupplierMvpView view) {
        searchMarketingSupplierMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        searchMarketingSupplierMvpView = null;
    }

    public void getSearchMarketingSupplier(String token,
                                           String supplierId,
                                           String branchId,
                                           String assetType) {
        count = 0;
        searchMarketingSupplierMvpView.onPreSearchMarketingSupplier();
        call = apiService.searchMarketingSupplierMaster(token, "SupplierEmp", branchId, supplierId, assetType);
        call.enqueue(new Callback<BaseResponse<MarketingSupplierResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MarketingSupplierResponse>> call, Response<BaseResponse<MarketingSupplierResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    searchMarketingSupplierMvpView.onSuccessSearchMarketingSupplier(response.body().getData());
                } else {
                    searchMarketingSupplierMvpView.onFailedMarketingSupplier(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MarketingSupplierResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (searchMarketingSupplierMvpView != null) {
                            searchMarketingSupplierMvpView.onFailedMarketingSupplier(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
