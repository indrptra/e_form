package com.kreditplus.eform.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.AssetMaster;
import com.kreditplus.eform.model.PosMaster;
import com.kreditplus.eform.model.ProductOfTenor;
import com.kreditplus.eform.model.ProductOfferingMaster;
import com.kreditplus.eform.model.SupplierEmp;
import com.kreditplus.eform.model.SupplierMaster;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.MasterSyncResponse;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.PosMaterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOfTenorSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOfferingMaterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierEmpSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierMasterSync;
import com.kreditplus.eform.presenter.mvpview.MasterSyncMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterSyncNewPresenter implements BasePresenter<MasterSyncMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private MasterSyncMvpView masterSyncMvpView;
    private Call<BaseResponse<MasterSyncResponse>> callmaster;
    private int count;
    private int countMasterSync;
    private MasterSyncResponse masterSyncResponse;
    private String assetTypeID;

    public MasterSyncNewPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(MasterSyncMvpView view) {
        masterSyncMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        masterSyncMvpView = null;
    }

    public void MasterSyncNew(String token, String dumpDate, String syncDate, String version, String branchID, String today, String assetTypeID) {
        this.assetTypeID = assetTypeID;
        count = 0;
        countMasterSync = 0;
        masterSyncMvpView.onPreLoadmasterSync();
        callmaster = apiService.masterSyncNew(token, dumpDate, syncDate, version, branchID, today, assetTypeID);
        callmaster.enqueue(new Callback<BaseResponse<MasterSyncResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MasterSyncResponse>> call, Response<BaseResponse<MasterSyncResponse>> response) {
                masterSyncResponse = response.body().getData();
                count = 0;
                if (response.isSuccessful()) {
                    masterSyncMvpView.onSuccessLoadmasterSync(masterSyncResponse);
//                    new SyncQuery(response).execute();
                } else if (response.code() == 403) {
                    masterSyncMvpView.onTokenMasterSyncExpired();
                } else {
                    masterSyncMvpView.onFailedLoadLoadmasterSync(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MasterSyncResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (masterSyncMvpView != null) {
                            masterSyncMvpView.onFailedLoadLoadmasterSync(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }

    /*private class SyncQuery extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

//        private List<SupplierMasterSync> supplierMasterSyncList = new ArrayList<>();
        private List<SupplierEmpSync> supplierEmpSyncList = new ArrayList<>();
        private List<ProductOfferingMaterSync> productOfferingList = new ArrayList<>();
        private List<PosMaterSync> posList = new ArrayList<>();
        private List<ProductOfTenorSync> productOfTenorSyncList = new ArrayList<>();
//        private List<AssetMasterSync> assetMasterSyncList = new ArrayList<>();

        public SyncQuery(Response<BaseResponse<MasterSyncResponse>> response) {
//            this.supplierMasterSyncList = response.body().getData().getSupplierMasterSyncList();
            this.supplierEmpSyncList = response.body().getData().getSupplierEmpSyncList();
            this.productOfferingList = response.body().getData().getProductOfferingMaterSyncList();
            this.posList = response.body().getData().getPosList();
            this.productOfTenorSyncList = response.body().getData().getProductOfTenorSyncList();
//            this.assetMasterSyncList = response.body().getData().getAssetMasterSyncList();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (!Constant.Flag.IS_DISABLE_MASTER_SYNC) {
//            SUPPLIER MASTER ====================================================================================
                *//*try {
                    databaseService.getSupplierMasterDao().callBatchTasks(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            for (int l = 0; l < supplierMasterSyncList.size(); l++) {
                                final SupplierMasterArrayList supplierMaster = new SupplierMasterArrayList();
                                List<SupplierMasterArrayList> supplierMasters = new ArrayList<>();

                                supplierMaster.setBranchID(supplierMasterSyncList.get(l).getBranchId());
                                supplierMaster.setSupplierID(supplierMasterSyncList.get(l).getSupplierID());
                                supplierMaster.setSupplierName(supplierMasterSyncList.get(l).getSupplierName());
//                    supplierMaster.setContactPersonHP(supplierMasterSyncList.get(l).getContactPersonHP());
//                    supplierMaster.setContactPersonEmail(supplierMasterSyncList.get(l).getContactPersonEmail());
//                    supplierMaster.setVarietyOfAssetSell(supplierMasterSyncList.get(l).getVarietyOfAssetSell());
//                    supplierMaster.setSupplierAccID(supplierMasterSyncList.get(l).getSupplierAccID());
                                supplierMaster.setSupplierAccountNo(supplierMasterSyncList.get(l).getSupplierAccountNo());
//                    supplierMaster.setSupplierAccountName(supplierMasterSyncList.get(l).getSupplierAccountName());
                                supplierMaster.setIsActive(supplierMasterSyncList.get(l).getIsActive());
                                supplierMaster.setDtmUpd(supplierMasterSyncList.get(l).getDtmUpd());
                                try {
                                    supplierMasters = databaseService.getSupplierMasterDao().queryBuilder().where().eq("SupplierID", supplierMasterSyncList.get(l).getSupplierID()).query();
                                    if (!supplierMasters.isEmpty()) {
                                        UpdateBuilder<SupplierMasterArrayList, String> updsupp = databaseService.getSupplierMasterDao().updateBuilder();
                                        updsupp.where().eq("SupplierID", supplierMasterSyncList.get(l).getSupplierID());
                                        updsupp.updateColumnValue("BranchID", supplierMasterSyncList.get(l).getBranchId());
                                        updsupp.updateColumnValue("SupplierID", supplierMasterSyncList.get(l).getSupplierID());
                                        updsupp.updateColumnValue("SupplierName", supplierMasterSyncList.get(l).getSupplierName());
//                                        updsupp.updateColumnValue("VarietyOfAssetSell", supplierMasterSyncList.get(l).getVarietyOfAssetSell());
//                                        updsupp.updateColumnValue("SupplierAccID", supplierMasterSyncList.get(l).getSupplierAccID());
                                        updsupp.updateColumnValue("SupplierAccountNo", supplierMasterSyncList.get(l).getSupplierAccountNo());
//                                        updsupp.updateColumnValue("SupplierAccountName", supplierMasterSyncList.get(l).getSupplierAccountName());
                                        updsupp.updateColumnValue("IsActive", supplierMasterSyncList.get(l).getIsActive());
                                        updsupp.updateColumnValue("DtmUpd", supplierMasterSyncList.get(l).getDtmUpd());
                                        updsupp.update();
                                        if (l == supplierMasterSyncList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    } else {
                                        databaseService.getSupplierMasterDao().create(supplierMaster);
                                        if (l == supplierMasterSyncList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    }
                                } catch (SQLException e) {
                                    if (BuildConfig.DEBUG)
                                        Log.e("Supplier Master", String.valueOf(e));
                                    Crashlytics.logException(e);
                                }
                            }
                            return null;
                        }
                    });
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.e("callback JobType", String.valueOf(e));
                    Crashlytics.logException(e);
                }*//*
//            SUPPLIER EMP ====================================================================================

                try {
                    databaseService.getSupplierEmpDao().callBatchTasks(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            for (int d = 0; d < supplierEmpSyncList.size(); d++) {
                                final SupplierEmp supplierEmp = new SupplierEmp();
                                List<SupplierEmp> ceksupem = new ArrayList<>();

                                supplierEmp.setBranchId(supplierEmpSyncList.get(d).getBranchId());
                                supplierEmp.setSupplierID(supplierEmpSyncList.get(d).getSupplierID());
                                supplierEmp.setSupplierEmployeeID(supplierEmpSyncList.get(d).getSupplierEmployeeID());
                                supplierEmp.setSupplierEmployeeName(supplierEmpSyncList.get(d).getSupplierEmployeeName());
//                                supplierEmp.setSupplierEmployeePosition(supplierEmpSyncList.get(d).getSupplierEmployeePosition());
                                supplierEmp.setIsActive(supplierEmpSyncList.get(d).getIsActive());

                                try {
                                    ceksupem = databaseService.getSupplierEmpDao().queryBuilder().where()
                                            .eq("SupplierID", supplierEmpSyncList.get(d).getSupplierID()).and()
                                            .eq("SupplierEmployeeID", supplierEmpSyncList.get(d).getSupplierEmployeeID()).query();
                                    if (!ceksupem.isEmpty()) {
                                        UpdateBuilder<SupplierEmp, String> updsupem = databaseService.getSupplierEmpDao().updateBuilder();
                                        updsupem.where().eq("SupplierID", supplierEmpSyncList.get(d).getSupplierID())
                                                .and().eq("SupplierEmployeeID", supplierEmpSyncList.get(d).getSupplierEmployeeID());
                                        updsupem.updateColumnValue("BranchId", supplierEmpSyncList.get(d).getBranchId());
                                        updsupem.updateColumnValue("SupplierEmployeeID", supplierEmpSyncList.get(d).getSupplierEmployeeID());
                                        updsupem.updateColumnValue("SupplierEmployeeName", supplierEmpSyncList.get(d).getSupplierEmployeeName());
//                                        updsupem.updateColumnValue("SupplierEmployeePosition", supplierEmpSyncList.get(d).getSupplierEmployeePosition());
                                        updsupem.updateColumnValue("IsActive", supplierEmpSyncList.get(d).getIsActive());
                                        updsupem.update();
                                        if (d == supplierEmpSyncList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    } else {
                                        databaseService.getSupplierEmpDao().create(supplierEmp);
                                        if (d == supplierEmpSyncList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    }
                                } catch (SQLException e) {
                                    if (BuildConfig.DEBUG) Log.e("SupplierEmpDao", String.valueOf(e));
                                    Crashlytics.logException(e);
                                }
                            }
                            return null;
                        }
                    });
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.e("callback Supplier", String.valueOf(e));
                    Crashlytics.logException(e);
                }
//            PRODUCT OFFERING MASTER ====================================================================================

                try {
                    databaseService.getProductOfferingMasterDao().callBatchTasks(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            for (int c = 0; c < productOfferingList.size(); c++) {
                                final ProductOfferingMaster productOfferingMaster = new ProductOfferingMaster();
                                List<ProductOfferingMaster> offeringMasters = new ArrayList<>();

                                productOfferingMaster.setBranchId(productOfferingList.get(c).getBranchId());
                                productOfferingMaster.setProductId(productOfferingList.get(c).getProductId());
                                productOfferingMaster.setProductOfferingId(productOfferingList.get(c).getProductOfferingId());
                                productOfferingMaster.setDescription(productOfferingList.get(c).getDescription());
                                productOfferingMaster.setAssetTypeId(productOfferingList.get(c).getAssetTypeId());
//                                productOfferingMaster.setUsedNewType(productOfferingList.get(c).getUsedNewType());
//                                productOfferingMaster.setProductType(productOfferingList.get(c).getProductType());
//                                productOfferingMaster.setFinanceType(productOfferingList.get(c).getFinanceType());
//                                productOfferingMaster.setInterestType(productOfferingList.get(c).getInterestType());
//                                productOfferingMaster.setInstallmentScheme(productOfferingList.get(c).getInstallmentScheme());
//                                productOfferingMaster.setAssetUsedNew(productOfferingList.get(c).getAssetUsedNew());
//                                productOfferingMaster.setRateType(productOfferingList.get(c).getRateType());
//                                productOfferingMaster.setPurposeofFinancingID(productOfferingList.get(c).getPurposeofFinancingID());
//                                productOfferingMaster.setWayofFinancingID(productOfferingList.get(c).getWayofFinancingID());
//                                productOfferingMaster.setStartDate(productOfferingList.get(c).getStartDate());
                                productOfferingMaster.setEndDate(productOfferingList.get(c).getEndDate());

                                try {
                                    offeringMasters = databaseService.getProductOfferingMasterDao().queryBuilder().where()
                                            .eq("ProductID", productOfferingList.get(c).getProductId()).and()
                                            .eq("BranchId", productOfferingList.get(c).getBranchId()).and()
                                            .eq("ProductOfferingID", productOfferingList.get(c).getProductOfferingId()).query();
                                    if (!offeringMasters.isEmpty()) {
                                        UpdateBuilder<ProductOfferingMaster, String> updoff = databaseService.getProductOfferingMasterDao().updateBuilder();
                                        updoff.where().eq("ProductID", productOfferingList.get(c).getProductId())
                                                .and().eq("BranchId", productOfferingList.get(c).getBranchId())
                                                .and().eq("ProductOfferingID", productOfferingList.get(c).getProductOfferingId());
                                        updoff.updateColumnValue("BranchId", productOfferingList.get(c).getBranchId());
                                        updoff.updateColumnValue("ProductID", productOfferingList.get(c).getProductId());
                                        updoff.updateColumnValue("ProductOfferingID", productOfferingList.get(c).getProductOfferingId());
                                        updoff.updateColumnValue("Description", productOfferingList.get(c).getDescription());
                                        updoff.updateColumnValue("AssetTypeID", productOfferingList.get(c).getAssetTypeId());
//                                        updoff.updateColumnValue("AssetTypeID", productOfferingList.get(c).getAssetTypeId());
//                                        updoff.updateColumnValue("UsedNewType", productOfferingList.get(c).getUsedNewType());
//                                        updoff.updateColumnValue("ProductType", productOfferingList.get(c).getProductType());
//                                        updoff.updateColumnValue("FinanceType", productOfferingList.get(c).getFinanceType());
//                                        updoff.updateColumnValue("InterestType", productOfferingList.get(c).getInterestType());
//                                        updoff.updateColumnValue("InstallmentScheme", productOfferingList.get(c).getInstallmentScheme());
//                                        updoff.updateColumnValue("AssetUsedNew", productOfferingList.get(c).getAssetUsedNew());
//                                        updoff.updateColumnValue("RateType", productOfferingList.get(c).getRateType());
//                                        updoff.updateColumnValue("PurposeofFinancingID", productOfferingList.get(c).getPurposeofFinancingID());
//                                        updoff.updateColumnValue("StartDate", productOfferingList.get(c).getStartDate());
                                        updoff.updateColumnValue("EndDate", productOfferingList.get(c).getEndDate());
                                        updoff.update();
                                        if (c == productOfferingList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    } else {
                                        databaseService.getProductOfferingMasterDao().create(productOfferingMaster);
                                        if (c == productOfferingList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    }
                                } catch (SQLException e) {
                                    if (BuildConfig.DEBUG)
                                        Log.e("callback ProdMaster", String.valueOf(e));
                                    Crashlytics.logException(e);
                                }
                            }
                            return null;
                        }
                    });
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.e("callback POMaster", String.valueOf(e));
                    Crashlytics.logException(e);
                }
//            POS ====================================================================================
                try {
                    databaseService.getPosMasterDao().callBatchTasks(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            for (int e = 0; e < posList.size(); e++) {
                                final PosMaster posMaster = new PosMaster();
                                List<PosMaster> cekpos = new ArrayList<>();

                                posMaster.setPosId(posList.get(e).getId());
                                posMaster.setPosName(posList.get(e).getName());
                                posMaster.setBranchId(posList.get(e).getBranchId());

                                try {
                                    cekpos = databaseService.getPosMasterDao().queryBuilder().where()
                                            .eq("POSID", posList.get(e).getId()).and()
                                            .eq("BranchId", posList.get(e).getBranchId()).query();
                                    if (!cekpos.isEmpty()) {
                                        UpdateBuilder<PosMaster, String> updpos = databaseService.getPosMasterDao().updateBuilder();
                                        updpos.where().eq("POSID", posList.get(e).getId())
                                                .and().eq("BranchId", productOfferingList.get(e).getBranchId());
                                        updpos.updateColumnValue("POSName", posList.get(e).getName());
                                        updpos.updateColumnValue("BranchId", posList.get(e).getBranchId());
                                        updpos.update();
                                        if (e == posList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    } else {
                                        databaseService.getPosMasterDao().create(posMaster);
                                        if (e == posList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    }
                                } catch (SQLException e1) {
                                    if (BuildConfig.DEBUG)
                                        Log.e("Pos", String.valueOf(e1));
                                    Crashlytics.logException(e1);
                                }
                            }
                            return null;
                        }
                    });
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.e("callback Pos", String.valueOf(e));
                    Crashlytics.logException(e);
                }
//                 ASSET MASTER ==========================================================================
                *//*try {
                    databaseService.getAssetMastersDao().callBatchTasks(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            for (int g = 0; g < assetMasterSyncList.size(); g++) {
                                final AssetMaster assetMaster = new AssetMaster();
                                List<AssetMaster> assetMasters = new ArrayList<>();

                                assetMaster.setAssetCode(assetMasterSyncList.get(g).getAssetCode());
                                assetMaster.setDescription(assetMasterSyncList.get(g).getDescription());
                                assetMaster.setCategoryId(assetMasterSyncList.get(g).getCategoryId());
                                assetMaster.setIsActive(assetMasterSyncList.get(g).getIsActive());
                                assetMaster.setAssetTypeId(assetMasterSyncList.get(g).getAssetTypeId());
                                assetMaster.setDtmUpd(assetMasterSyncList.get(g).getDtmUpd());

                                try {
                                    assetMasters = databaseService.getAssetMastersDao().queryBuilder().where()
                                            .eq("AssetCode", assetMasterSyncList.get(g).getAssetCode()).query();
                                    if (!assetMasters.isEmpty()) {
                                        UpdateBuilder<AssetMaster, String> updam = databaseService.getAssetMastersDao().updateBuilder();
                                        updam.where().eq("AssetCode", assetMasterSyncList.get(g).getAssetCode());
                                        updam.updateColumnValue("AssetCode", assetMasterSyncList.get(g).getAssetCode());
                                        updam.updateColumnValue("Description", assetMasterSyncList.get(g).getDescription());
                                        updam.updateColumnValue("CategoryID", assetMasterSyncList.get(g).getCategoryId());
                                        updam.updateColumnValue("IsActive", assetMasterSyncList.get(g).getIsActive());
                                        updam.updateColumnValue("AssetTypeId", assetMasterSyncList.get(g).getAssetTypeId());
                                        updam.updateColumnValue("DtmUpd", assetMasterSyncList.get(g).getDtmUpd());
                                        updam.update();
                                        if (g == assetMasterSyncList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    } else {
                                        databaseService.getAssetMastersDao().create(assetMaster);
                                        if (g == assetMasterSyncList.size() - 1) {
                                            countMasterSync++;
                                            masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                        }
                                    }
                                } catch (SQLException e) {
                                    if (BuildConfig.DEBUG)
                                        Log.e("AssetMaster", String.valueOf(e));
                                    Crashlytics.logException(e);
                                }
                            }
                            return null;
                        }
                    });
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.e("callback AssetMaster", String.valueOf(e));
                    Crashlytics.logException(e);
                }*//*
//            PRODUCT OF TENOR ====================================================================================
                if (assetTypeID.equals("11")){
                    try {
                        databaseService.getProductOfTenorDao().callBatchTasks(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                for (int f = 0; f < productOfTenorSyncList.size(); f++) {
                                    final ProductOfTenor productOfTenor = new ProductOfTenor();
                                    List<ProductOfTenor> productOfTenors = new ArrayList<>();

                                    productOfTenor.setBranchId(productOfTenorSyncList.get(f).getBranchId());
                                    productOfTenor.setProductId(productOfTenorSyncList.get(f).getProductId());
                                    productOfTenor.setProductOfferingId(productOfTenorSyncList.get(f).getProductOfferingId());
                                    productOfTenor.setTenor(productOfTenorSyncList.get(f).getTenor());
                                    productOfTenor.setFlatRate(productOfTenorSyncList.get(f).getFlatRate());
                                    productOfTenor.setEffectiveRate(productOfTenorSyncList.get(f).getEffectiveRate());
//                                productOfTenor.setGrossYieldRate(productOfTenorSyncList.get(f).getGrossYieldRate());
                                    productOfTenor.setAdminFee(productOfTenorSyncList.get(f).getAdminFee());
//                                productOfTenor.setFiduciafee(productOfTenorSyncList.get(f).getFidusiaFee());
//                                productOfTenor.setWarningMinimumIncome(productOfTenorSyncList.get(f).getWarningMinimumIncome());
                                    productOfTenor.setFirstInstallment(productOfTenorSyncList.get(f).getFirstInstallment());
//                                productOfTenor.setInstallmentAmount(productOfTenorSyncList.get(f).getInstallmentAmount());
                                    productOfTenor.setNtf(productOfTenorSyncList.get(f).getNtf());
//                                productOfTenor.setAdminFeeFlag(productOfTenorSyncList.get(f).getAdminFeeFlag());
//                                productOfTenor.setAdminFeeForNTFPercentage(productOfTenorSyncList.get(f).getAdminFeeForNTFPercentage());
//                                productOfTenor.setDiscountInstallmentPercentage(productOfTenorSyncList.get(f).getDiscountInstallmentPercentage());
//                                productOfTenor.setDiscountOTRAmount(productOfTenorSyncList.get(f).getDiscountOTRAmount());
//                                productOfTenor.setDiscountOTRPercentage(productOfTenorSyncList.get(f).getDiscountOTRPercentage());
                                    productOfTenor.setDiscountRateTimes(productOfTenorSyncList.get(f).getDiscountRateTimes());
                                    productOfTenor.setIsActive(productOfTenorSyncList.get(f).getIsActive());
                                    try {
                                        productOfTenors = databaseService.getProductOfTenorDao().queryBuilder().where()
                                                .eq("ProductID", productOfTenorSyncList.get(f).getProductId()).and()
                                                .eq("ProductOfferingId", productOfTenorSyncList.get(f).getProductOfferingId()).and()
                                                .eq("BranchId", productOfTenorSyncList.get(f).getBranchId()).and()
                                                .eq("Tenor", productOfTenorSyncList.get(f).getTenor()).query();
                                        if (!productOfTenors.isEmpty()) {
                                            UpdateBuilder<ProductOfTenor, String> updtenor = databaseService.getProductOfTenorDao().updateBuilder();
                                            updtenor.where().eq("ProductID", productOfTenor.getProductId()).and().
                                                    eq("ProductOfferingId", productOfTenor.getProductOfferingId()).and()
                                                    .eq("Tenor", productOfTenor.getTenor());
                                            updtenor.updateColumnValue("BranchId", productOfTenorSyncList.get(f).getBranchId());
                                            updtenor.updateColumnValue("ProductID", productOfTenorSyncList.get(f).getProductId());
                                            updtenor.updateColumnValue("ProductOfferingId", productOfTenorSyncList.get(f).getProductOfferingId());
                                            updtenor.updateColumnValue("Tenor", productOfTenorSyncList.get(f).getTenor());
                                            updtenor.updateColumnValue("FlatRate", productOfTenorSyncList.get(f).getFlatRate());
                                            updtenor.updateColumnValue("EffectiveRate", productOfTenorSyncList.get(f).getEffectiveRate());
//                                        updtenor.updateColumnValue("GrossYieldRate", productOfTenorSyncList.get(f).getGrossYieldRate());
                                            updtenor.updateColumnValue("AdminFee", productOfTenorSyncList.get(f).getAdminFee());
//                                        updtenor.updateColumnValue("FiduciaFee", productOfTenorSyncList.get(f).getFidusiaFee());
//                                        updtenor.updateColumnValue("WarningMinimumIncome", productOfTenorSyncList.get(f).getWarningMinimumIncome());
                                            updtenor.updateColumnValue("FirstInstallment", productOfTenorSyncList.get(f).getFirstInstallment());
//                                        updtenor.updateColumnValue("InstallmentAmount", productOfTenorSyncList.get(f).getInstallmentAmount());
                                            updtenor.updateColumnValue("NTF", productOfTenorSyncList.get(f).getNtf());
//                                        updtenor.updateColumnValue("AdminFeeFlag", productOfTenorSyncList.get(f).getAdminFeeFlag());
//                                        updtenor.updateColumnValue("AdminFeeForNTFPercentage", productOfTenorSyncList.get(f).getAdminFeeForNTFPercentage());
//                                        updtenor.updateColumnValue("DiscountInstallmentPercentage", productOfTenorSyncList.get(f).getDiscountInstallmentPercentage());
//                                        updtenor.updateColumnValue("DiscountOTRAmount", productOfTenorSyncList.get(f).getDiscountOTRAmount());
//                                        updtenor.updateColumnValue("DiscountOTRPercentage", productOfTenorSyncList.get(f).getDiscountOTRPercentage());
                                            updtenor.updateColumnValue("DiscountRateTimes", productOfTenorSyncList.get(f).getDiscountRateTimes());
                                            updtenor.updateColumnValue("IsActive", productOfTenorSyncList.get(f).getIsActive());
                                            updtenor.update();
                                            if (f == productOfTenorSyncList.size() - 1) {
                                                countMasterSync++;
                                                masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                            }
                                        } else {
                                            databaseService.getProductOfTenorDao().create(productOfTenor);
                                            if (f == productOfTenorSyncList.size() - 1) {
                                                countMasterSync++;
                                                masterSyncMvpView.onUpdateCountMasterSync(countMasterSync);
                                            }
                                        }
                                    } catch (SQLException e) {
                                        if (BuildConfig.DEBUG)
                                            Log.e("ProdTenor", String.valueOf(e));
                                        Crashlytics.logException(e);
                                    }
                                }
                                return null;
                            }
                        });
                    } catch (Exception e) {
                        if (BuildConfig.DEBUG)
                            Log.e("callback ProdTenor", String.valueOf(e));
                        Crashlytics.logException(e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (masterSyncMvpView != null) masterSyncMvpView.onSuccessLoadmasterSync(masterSyncResponse);
        }
    }*/
}
