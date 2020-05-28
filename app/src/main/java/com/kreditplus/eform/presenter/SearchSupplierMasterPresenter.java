package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.SupplierResponse;
import com.kreditplus.eform.presenter.mvpview.SearchSupplierMasterMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSupplierMasterPresenter implements BasePresenter<SearchSupplierMasterMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SearchSupplierMasterMvpView searchSupplierMasterMvpView;
    private Call<BaseResponse<SupplierResponse>> call;
    private int count;

    public SearchSupplierMasterPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SearchSupplierMasterMvpView view) {
        searchSupplierMasterMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        searchSupplierMasterMvpView = null;
    }

    public void getSearchSupplierMaster(String token,
                                        String value,
                                        String assetType,
                                        String appType,
                                        String aoBranch) {
        count = 0;
        searchSupplierMasterMvpView.onPreSearchSupplierMaster();
        call = apiService.searchSuplierMaster(token, "SupplierMaster", value,assetType,aoBranch, appType);
        call.enqueue(new Callback<BaseResponse<SupplierResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<SupplierResponse>> call, Response<BaseResponse<SupplierResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    searchSupplierMasterMvpView.onSuccessSearchSupplierMaster(response.body().getData());
                } else {
                    searchSupplierMasterMvpView.onFailedSearchSupplierMaster(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SupplierResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (searchSupplierMasterMvpView != null) {
                            searchSupplierMasterMvpView.onFailedSearchSupplierMaster(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
