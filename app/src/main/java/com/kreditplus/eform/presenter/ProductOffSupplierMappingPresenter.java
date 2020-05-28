package com.kreditplus.eform.presenter;

import android.os.AsyncTask;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.Recomendation;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.ProdOfSuppMapping;
import com.kreditplus.eform.model.response.ProductOfSupplierMapping;
import com.kreditplus.eform.presenter.mvpview.ProductOffSupplierMappingMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOffSupplierMappingPresenter implements BasePresenter<ProductOffSupplierMappingMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private ProductOffSupplierMappingMvpView mProductOffSupplierMappingMvpView;
    private Call<BaseResponse<ProductOfSupplierMapping>> call;
    private int count;

    public ProductOffSupplierMappingPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(ProductOffSupplierMappingMvpView view) {
        mProductOffSupplierMappingMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mProductOffSupplierMappingMvpView = null;
    }

    public void ProdOfSuppMap(String token, String branchId, String supplierId) {
        mProductOffSupplierMappingMvpView.onPreProdOfSupplier();
        call = apiService.productOffSupplierMapping(token, branchId, supplierId);
        call.enqueue(new Callback<BaseResponse<ProductOfSupplierMapping>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProductOfSupplierMapping>> call, Response<BaseResponse<ProductOfSupplierMapping>> response) {
                if (response.isSuccessful()) {
                    new SyncQuery(response).execute();
                } else if (response.code() == 403) {
                    mProductOffSupplierMappingMvpView.onTokenProdOfSupplier();
                } else {
                    mProductOffSupplierMappingMvpView.onFailedProdOfSupplier(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductOfSupplierMapping>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else if (mProductOffSupplierMappingMvpView != null) {
                        mProductOffSupplierMappingMvpView.onFailedProdOfSupplier(errorRetrofitUtil.responseFailedError(t));
                        count = 0;
                    }
                }
            }
        });
    }

    private class SyncQuery extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private List<ProdOfSuppMapping> prodOfSuppMappings = new ArrayList<>();

        public SyncQuery(Response<BaseResponse<ProductOfSupplierMapping>> response) {
            this.prodOfSuppMappings = response.body().getData().getProdOfSuppMappings();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < prodOfSuppMappings.size(); i++) {
                final ProdOfSuppMapping prodOfSuppMapping = new ProdOfSuppMapping();
                List<ProdOfSuppMapping> mappings = new ArrayList<>();

                prodOfSuppMapping.setBranchID(prodOfSuppMappings.get(i).getBranchID());
                prodOfSuppMapping.setProductID(prodOfSuppMappings.get(i).getProductID());
                prodOfSuppMapping.setProductIDDescription(prodOfSuppMappings.get(i).getProductIDDescription());
                prodOfSuppMapping.setProductOfferingID(prodOfSuppMappings.get(i).getProductOfferingID());
                prodOfSuppMapping.setProductOfferingIDDescription(prodOfSuppMappings.get(i).getProductOfferingIDDescription());
                prodOfSuppMapping.setSupplierID(prodOfSuppMappings.get(i).getSupplierID());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mProductOffSupplierMappingMvpView != null) mProductOffSupplierMappingMvpView.onSuccessProdOfSupplier(prodOfSuppMappings);
        }
    }
}
