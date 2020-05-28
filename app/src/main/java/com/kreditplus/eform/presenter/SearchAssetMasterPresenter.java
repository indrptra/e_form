package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.AssetMasterResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.MasterSyncResponse;
import com.kreditplus.eform.presenter.mvpview.MasterSyncMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchAssetMasterMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;
import com.kreditplus.eform.view.IndonesianCurrencyEditText;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAssetMasterPresenter implements BasePresenter<SearchAssetMasterMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SearchAssetMasterMvpView mSearchAssetMasterMvpView;
    private Call<BaseResponse<AssetMasterResponse>> callmaster;
    private int count;

    public SearchAssetMasterPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(SearchAssetMasterMvpView view) {
        mSearchAssetMasterMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        mSearchAssetMasterMvpView = null;
    }

    public void GetSearchAssetMaster(String token, String search, final NiceAutoCompleteTextView actNamaBarangAsset, final IndonesianCurrencyEditText edtPrice) {
        count = 0;
        mSearchAssetMasterMvpView.onPreSearchAssetMaster();
        callmaster = apiService.assetMaster(token, search);
        callmaster.enqueue(new Callback<BaseResponse<AssetMasterResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AssetMasterResponse>> call, Response<BaseResponse<AssetMasterResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mSearchAssetMasterMvpView.onSuccessSearchAssetMaster(response.body().getData(), actNamaBarangAsset, edtPrice);
                } else {
                    mSearchAssetMasterMvpView.onFailedSearchAssetMaster(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AssetMasterResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (mSearchAssetMasterMvpView != null) {
                            mSearchAssetMasterMvpView.onFailedSearchAssetMaster(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });

    }

    public void getSearchDataMaster(String token,String branchId, String productOffId,String option, String search,String asetType, final NiceAutoCompleteTextView actNamaBarangAsset, final IndonesianCurrencyEditText edtPriceAsset) {
        // Option List ['AssetMaster','SupplierMaster','ProductOffMaster','SupplierEmp','ProductOffTenor']
        count = 0;
        mSearchAssetMasterMvpView.onPreSearchAssetMaster();
        callmaster = apiService.searchDataMaster(token, option, search,asetType,branchId,productOffId);
        callmaster.enqueue(new Callback<BaseResponse<AssetMasterResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AssetMasterResponse>> call, Response<BaseResponse<AssetMasterResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mSearchAssetMasterMvpView.onSuccessSearchAssetMaster(response.body().getData(), actNamaBarangAsset, edtPriceAsset);
                } else {
                    mSearchAssetMasterMvpView.onFailedSearchAssetMaster(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AssetMasterResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (mSearchAssetMasterMvpView != null) {
                            mSearchAssetMasterMvpView.onFailedSearchAssetMaster(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }
}
