//package com.kreditplus.eform.backup;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.crashlytics.android.Crashlytics;
//import com.j256.ormlite.stmt.UpdateBuilder;
//import com.j256.ormlite.support.ConnectionSource;
//import com.kreditplus.eform.App;
//import com.kreditplus.eform.BuildConfig;
//import com.kreditplus.eform.helper.Constant;
//import com.kreditplus.eform.helper.ErrorRetrofitUtil;
//import com.kreditplus.eform.helper.Util;
//import com.kreditplus.eform.model.Aobranch;
//import com.kreditplus.eform.model.BranchMaster;
//import com.kreditplus.eform.model.DataFinansial;
//import com.kreditplus.eform.model.PosMaster;
//import com.kreditplus.eform.model.ProductOfTenor;
//import com.kreditplus.eform.model.ProductOfferingMaster;
//import com.kreditplus.eform.model.ResultAobranch;
//import com.kreditplus.eform.model.ResultKelurahan;
//import com.kreditplus.eform.model.SupplierEmp;
//import com.kreditplus.eform.model.SupplierMasterArrayList;
//import com.kreditplus.eform.model.response.BaseResponse;
//import com.kreditplus.eform.model.response.MasterSyncResponse;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AoBranchMasterSync;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.DataFinansialSync;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.PosMaterSync;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOfTenorSync;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProductOfferingMaterSync;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierEmpSync;
//import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.SupplierMasterSync;
//import com.kreditplus.eform.presenter.BasePresenter;
//import com.kreditplus.eform.presenter.mvpview.MasterSyncMvpView;
//import com.kreditplus.eform.service.ApiService;
//import com.kreditplus.eform.service.DatabaseService;
//
//import org.joda.time.DateTime;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Callable;
//
//import javax.inject.Inject;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
///**
// * Created by apc-lap012 on 17/02/17.
// */
//
//public class MasterSyncPresenter implements BasePresenter<MasterSyncMvpView> {
//
//    @Inject
//    ApiService apiService;
//
//    @Inject
//    ErrorRetrofitUtil errorRetrofitUtil;
//
//    @Inject
//    DatabaseService databaseService;
//
//    private MasterSyncMvpView masterSyncMvpView;
//    private Call<BaseResponse<MasterSyncResponse>> callmaster;
//    private ConnectionSource connectionSource;
//    private int count;
//
//
//    public MasterSyncPresenter() {
//        App.appComponent().inject(this);
//    }
//
//
//    @Override
//    public void attachView(MasterSyncMvpView view) {
//        masterSyncMvpView = view;
//    }
//
//    @Override
//    public void detachView() {
//        if (callmaster != null) callmaster.cancel();
//        masterSyncMvpView = null;
//    }
//
//    public void MasterSync(String token, String dumpDate, String syncDate, String version, String type, String branchID) {
//        count = 0;
//        masterSyncMvpView.onPreLoadmasterSync();
//        callmaster = apiService.masterSync(token, dumpDate, syncDate, version, type, branchID);
//        callmaster.enqueue(new Callback<BaseResponse<MasterSyncResponse>>() {
//            @Override
//            public void onResponse(Call<BaseResponse<MasterSyncResponse>> call, Response<BaseResponse<MasterSyncResponse>> response) {
//                count = 0;
//                if (response.isSuccessful()) {
//                    new SyncQuery(response).execute();
//                } else if (response.code() == 403) {
//                    masterSyncMvpView.onTokenMasterSyncExpired();
//                } else {
//                    masterSyncMvpView.onFailedLoadLoadmasterSync(errorRetrofitUtil.parseError(response));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse<MasterSyncResponse>> call, Throwable t) {
//                if (!callmaster.isCanceled()) {
//                    if (count < 3) {
//                        count += 1;
//                        call.clone().enqueue(this);
//                    } else {
//                        if (masterSyncMvpView != null) {
//                            masterSyncMvpView.onFailedLoadLoadmasterSync(errorRetrofitUtil.responseFailedError(t));
//                        }
//                    }
//
//                }
//            }
//        });
//    }
//
//    private class SyncQuery extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        private List<ProductOfferingMaterSync> productOfferingList = new ArrayList<>();
//        private List<SupplierEmpSync> supplierEmpSyncList = new ArrayList<>();
//        private List<PosMaterSync> posList = new ArrayList<>();
//        private List<ProductOfTenorSync> productOfTenorSyncList = new ArrayList<>();
//        private List<SupplierMasterSync> supplierMasterSyncList = new ArrayList<>();
//        private DataFinansialSync dataFinansialSync = new DataFinansialSync();
//        private List<AoBranchMasterSync> aobranchSyncList = new ArrayList<>();
//        private String branchPrimary;
//
//        public SyncQuery(Response<BaseResponse<MasterSyncResponse>> response) {
//            this.productOfferingList = response.body().getData().getProductOfferingMaterSyncList();
//            this.supplierEmpSyncList = response.body().getData().getSupplierEmpSyncList();
//            this.posList = response.body().getData().getPosList();
//            this.productOfTenorSyncList = response.body().getData().getProductOfTenorSyncList();
//            this.supplierMasterSyncList = response.body().getData().getSupplierMasterSyncList();
//            this.dataFinansialSync = response.body().getData().getDatafinancial();
//            this.aobranchSyncList = response.body().getData().getAoBranch();
//            this.branchPrimary = response.body().getData().getBranchPrimary();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            if (!Constant.Flag.IS_DISABLE_MASTER_SYNC) {
////            AOBRANCH ====================================================================================
//                for (int a = 0; a < aobranchSyncList.size(); a++) {
//                    final Aobranch aobranch = new Aobranch();
//                    final ResultAobranch resultAobranch = new ResultAobranch();
//                    List<Aobranch> aobranchCekList;
//                    String Date = Util.TanggalHariIni(new DateTime());
//
//                    aobranch.setBranchIdAo(aobranchSyncList.get(a).getBranchId());
//                    aobranch.setIsActive(aobranchSyncList.get(a).getIsActive());
//                    aobranch.setBranchName(aobranchSyncList.get(a).getBranchFullName());
//                    if (aobranchSyncList.get(a).getBranchId().equalsIgnoreCase(branchPrimary)) {
//                        aobranch.setFirstSyncDate(Date); // aobranch yang sama dengan master branch field first data diberi nilai date dahulu agar tidak  sync ulang pada saat pengajuan baru
//                    } else {
//                        aobranch.setFirstSyncDate("-");
//                    }
//
//                    //result branch
//                    resultAobranch.setBranchId(aobranchSyncList.get(a).getBranchId());
//                    resultAobranch.setResultBranch(aobranchSyncList.get(a).getBranchId() + " - " + aobranchSyncList.get(a).getBranchFullName());
//                    resultAobranch.setIsActive(aobranchSyncList.get(a).getIsActive());
//                    try {
//                        aobranchCekList = databaseService.getAobranchDao().queryBuilder().where().eq("BranchIdAo", aobranchSyncList.get(a).getBranchId()).query();
//                        if (!aobranchCekList.isEmpty()) {
//                            UpdateBuilder<Aobranch, String> updateAobranch = databaseService.getAobranchDao().updateBuilder();
//                            updateAobranch.where().eq("BranchIdAo", aobranchSyncList.get(a).getBranchId());
//                            updateAobranch.updateColumnValue("BranchIdAo", aobranchSyncList.get(a).getBranchId());
//                            updateAobranch.updateColumnValue("IsActive", aobranchSyncList.get(a).getIsActive());
//                            updateAobranch.update();
//                            // result branch
//                            UpdateBuilder<ResultAobranch, String> updateResult = databaseService.getResultAobranchDao().updateBuilder();
//                            updateResult.where().eq("Branchid", aobranchSyncList.get(a).getBranchId());
//                            updateResult.updateColumnValue("ResultBranch", aobranchSyncList.get(a).getBranchId() + " - " + aobranchSyncList.get(a).getBranchFullName());
//                            updateResult.updateColumnValue("isActive", aobranchSyncList.get(a).getIsActive());
//                            updateResult.update();
//                        } else {
//                            databaseService.getAobranchDao().callBatchTasks(new Callable<Void>() {
//                                @Override
//                                public Void call() throws Exception {
//                                    databaseService.getAobranchDao().create(aobranch);
//                                    return null;
//                                }
//                            });
//                            databaseService.getResultAobranchDao().callBatchTasks(new Callable<Void>() {
//                                @Override
//                                public Void call() throws Exception {
//                                    databaseService.getResultAobranchDao().create(resultAobranch);
//                                    return null;
//                                }
//                            });
//                        }
//                    } catch (Exception e) {
//                        if (BuildConfig.DEBUG)
//                            Log.e("Aobranch", String.valueOf(e));
//                        Crashlytics.logException(e);
//                    }
//
//                }
////            BRANCH MASTER ====================================================================================
//                try {
//                    databaseService.getBranchMasterDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            final BranchMaster branchMaster = new BranchMaster();
//                            List<BranchMaster> branchMasterCekList = new ArrayList<>();
//                            branchMaster.setBranchPrimary(branchPrimary);
//
//                            try {
//                                branchMasterCekList = databaseService.getBranchMasterDao().queryBuilder().where().eq("BranchPrimary", branchPrimary).query();
//                                if (!branchMasterCekList.isEmpty()) {
//                                    UpdateBuilder<BranchMaster, String> updateBranchMaster = databaseService.getBranchMasterDao().updateBuilder();
//                                    updateBranchMaster.where().eq("BranchPrimary", branchPrimary);
//                                    updateBranchMaster.updateColumnValue("BranchPrimary", branchPrimary);
//                                    updateBranchMaster.update();
//                                } else {
//                                    databaseService.getBranchMasterDao().create(branchMaster);
//                                }
//                            } catch (SQLException e) {
//                                if (BuildConfig.DEBUG)
//                                    Log.e("Branch Master", String.valueOf(e));
//                                Crashlytics.logException(e);
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback Branch", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
////            PRODUCT OFFERING MASTER ====================================================================================
//                try {
//                    databaseService.getProductOfferingMasterDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            for (int c = 0; c < productOfferingList.size(); c++) {
//                                final ProductOfferingMaster productOfferingMaster = new ProductOfferingMaster();
//                                List<ProductOfferingMaster> offeringMasters = new ArrayList<>();
//
//                                productOfferingMaster.setBranchId(productOfferingList.get(c).getBranchId());
//                                productOfferingMaster.setProductId(productOfferingList.get(c).getProductId());
//                                productOfferingMaster.setProductOfferingId(productOfferingList.get(c).getProductOfferingId());
//                                productOfferingMaster.setDescription(productOfferingList.get(c).getDescription());
//                                productOfferingMaster.setAssetTypeId(productOfferingList.get(c).getAssetTypeId());
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
//                                productOfferingMaster.setEndDate(productOfferingList.get(c).getEndDate());
//
//                                try {
//                                    offeringMasters = databaseService.getProductOfferingMasterDao().queryBuilder().where()
//                                            .eq("ProductID", productOfferingList.get(c).getProductId()).and()
//                                            .eq("BranchId", productOfferingList.get(c).getBranchId()).and()
//                                            .eq("ProductOfferingID", productOfferingList.get(c).getProductOfferingId()).query();
//                                    if (!offeringMasters.isEmpty()) {
//                                        UpdateBuilder<ProductOfferingMaster, String> updoff = databaseService.getProductOfferingMasterDao().updateBuilder();
//                                        updoff.where().eq("ProductID", productOfferingList.get(c).getProductId())
//                                                .and().eq("ProductOfferingID", productOfferingList.get(c).getProductOfferingId());
//                                        updoff.updateColumnValue("BranchId", productOfferingList.get(c).getBranchId());
//                                        updoff.updateColumnValue("Description", productOfferingList.get(c).getDescription());
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
//                                        updoff.updateColumnValue("EndDate", productOfferingList.get(c).getEndDate());
//                                        updoff.update();
//                                    } else {
//                                        databaseService.getProductOfferingMasterDao().create(productOfferingMaster);
//                                    }
//                                } catch (SQLException e) {
//                                    if (BuildConfig.DEBUG)
//                                        Log.e("callback ProdMaster", String.valueOf(e));
//                                    Crashlytics.logException(e);
//                                }
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback POMaster", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
////            SUPPLIER EMP ====================================================================================
//                try {
//                    databaseService.getSupplierEmpDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            for (int d = 0; d < supplierEmpSyncList.size(); d++) {
//
//                                final SupplierEmp supplierEmp = new SupplierEmp();
//                                List<SupplierEmp> ceksupem = new ArrayList<>();
//
//                                supplierEmp.setBranchId(supplierEmpSyncList.get(d).getBranchId());
//                                supplierEmp.setSupplierID(supplierEmpSyncList.get(d).getSupplierID());
//                                supplierEmp.setSupplierEmployeeID(supplierEmpSyncList.get(d).getSupplierEmployeeID());
//                                supplierEmp.setSupplierEmployeeName(supplierEmpSyncList.get(d).getSupplierEmployeeName());
//                                supplierEmp.setSupplierEmployeePosition(supplierEmpSyncList.get(d).getSupplierEmployeePosition());
//                                supplierEmp.setIsActive(supplierEmpSyncList.get(d).getIsActive());
//
//                                try {
//                                    ceksupem = databaseService.getSupplierEmpDao().queryBuilder().where()
//                                            .eq("SupplierID", supplierEmpSyncList.get(d).getSupplierID()).and()
//                                            .eq("SupplierEmployeeID", supplierEmpSyncList.get(d).getSupplierEmployeeID()).and()
//                                            .eq("BranchId", supplierEmpSyncList.get(d).getBranchId()).query();
//                                    if (!ceksupem.isEmpty()) {
//                                        UpdateBuilder<SupplierEmp, String> updsupem = databaseService.getSupplierEmpDao().updateBuilder();
//                                        updsupem.where().eq("SupplierID", supplierEmpSyncList.get(d).getSupplierID());
//                                        updsupem.updateColumnValue("BranchId", supplierEmpSyncList.get(d).getBranchId());
//                                        updsupem.updateColumnValue("SupplierEmployeeID", supplierEmpSyncList.get(d).getSupplierEmployeeID());
//                                        updsupem.updateColumnValue("SupplierEmployeeName", supplierEmpSyncList.get(d).getSupplierEmployeeName());
//                                        updsupem.updateColumnValue("SupplierEmployeePosition", supplierEmpSyncList.get(d).getSupplierEmployeePosition());
//                                        updsupem.updateColumnValue("IsActive", supplierEmpSyncList.get(d).getIsActive());
//                                        updsupem.update();
//                                    } else {
//                                        databaseService.getSupplierEmpDao().create(supplierEmp);
//                                    }
//                                } catch (SQLException e) {
//                                    if (BuildConfig.DEBUG)
//                                        Log.e("Supplier Master", String.valueOf(e));
//                                    Crashlytics.logException(e);
//                                }
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback Supplier", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
////            POS ====================================================================================
//                try {
//                    databaseService.getPosMasterDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            for (int e = 0; e < posList.size(); e++) {
//                                final PosMaster posMaster = new PosMaster();
//                                List<PosMaster> cekpos = new ArrayList<>();
//
//                                posMaster.setPosId(posList.get(e).getId());
//                                posMaster.setPosName(posList.get(e).getName());
//                                posMaster.setBranchId(posList.get(e).getBranchId());
//
//                                try {
//                                    cekpos = databaseService.getPosMasterDao().queryBuilder().where()
//                                            .eq("POSID", posList.get(e).getId()).and()
//                                            .eq("BranchId", posList.get(e).getBranchId()).query();
//                                    if (!cekpos.isEmpty()) {
//                                        UpdateBuilder<PosMaster, String> updpos = databaseService.getPosMasterDao().updateBuilder();
//                                        updpos.where().eq("POSID", posList.get(e).getId());
//                                        updpos.updateColumnValue("POSName", posList.get(e).getName());
//                                        updpos.updateColumnValue("BranchId", posList.get(e).getBranchId());
//                                        updpos.update();
//                                    } else {
//                                        databaseService.getPosMasterDao().create(posMaster);
//                                    }
//                                } catch (SQLException e1) {
//                                    if (BuildConfig.DEBUG)
//                                        Log.e("Pos", String.valueOf(e1));
//                                    Crashlytics.logException(e1);
//                                }
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback Pos", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
////            PRODUCT OF TENOR ====================================================================================
//                try {
//                    databaseService.getProductOfTenorDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            for (int f = 0; f < productOfTenorSyncList.size(); f++) {
//                                final ProductOfTenor productOfTenor = new ProductOfTenor();
//                                List<ProductOfTenor> productOfTenors = new ArrayList<>();
//
//                                productOfTenor.setBranchId(productOfTenorSyncList.get(f).getBranchId());
//                                productOfTenor.setProductId(productOfTenorSyncList.get(f).getProductId());
//                                productOfTenor.setProductOfferingId(productOfTenorSyncList.get(f).getProductOfferingId());
//                                productOfTenor.setTenor(productOfTenorSyncList.get(f).getTenor());
//                                productOfTenor.setFlatRate(productOfTenorSyncList.get(f).getFlatRate());
//                                productOfTenor.setEffectiveRate(productOfTenorSyncList.get(f).getEffectiveRate());
//                                productOfTenor.setGrossYieldRate(productOfTenorSyncList.get(f).getGrossYieldRate());
//                                productOfTenor.setAdminFee(productOfTenorSyncList.get(f).getAdminFee());
//                                productOfTenor.setFiduciafee(productOfTenorSyncList.get(f).getFidusiaFee());
//                                productOfTenor.setWarningMinimumIncome(productOfTenorSyncList.get(f).getWarningMinimumIncome());
//                                productOfTenor.setFirstInstallment(productOfTenorSyncList.get(f).getFirstInstallment());
//                                productOfTenor.setInstallmentAmount(productOfTenorSyncList.get(f).getInstallmentAmount());
//                                productOfTenor.setNtf(productOfTenorSyncList.get(f).getNtf());
//                                productOfTenor.setAdminFeeFlag(productOfTenorSyncList.get(f).getAdminFeeFlag());
//                                productOfTenor.setAdminFeeForNTFPercentage(productOfTenorSyncList.get(f).getAdminFeeForNTFPercentage());
//                                productOfTenor.setDiscountInstallmentPercentage(productOfTenorSyncList.get(f).getDiscountInstallmentPercentage());
//                                productOfTenor.setDiscountOTRAmount(productOfTenorSyncList.get(f).getDiscountOTRAmount());
//                                productOfTenor.setDiscountOTRPercentage(productOfTenorSyncList.get(f).getDiscountOTRPercentage());
//                                productOfTenor.setIsActive(productOfTenorSyncList.get(f).getIsActive());
//
//
//                                try {
//                                    productOfTenors = databaseService.getProductOfTenorDao().queryBuilder().where()
//                                            .eq("ProductID", productOfTenorSyncList.get(f).getProductId()).and()
//                                            .eq("ProductOfferingId", productOfTenorSyncList.get(f).getProductOfferingId()).and()
//                                            .eq("BranchId", productOfTenorSyncList.get(f).getBranchId()).and()
//                                            .eq("Tenor", productOfTenorSyncList.get(f).getTenor()).query();
//                                    if (!productOfTenors.isEmpty()) {
//                                        UpdateBuilder<ProductOfTenor, String> updtenor = databaseService.getProductOfTenorDao().updateBuilder();
//                                        updtenor.where().eq("ProductID", productOfTenor.getProductId()).and().
//                                                eq("ProductOfferingId", productOfTenor.getProductOfferingId()).and()
//                                                .eq("Tenor", productOfTenor.getTenor());
//                                        updtenor.updateColumnValue("BranchId", productOfTenorSyncList.get(f).getBranchId());
//                                        updtenor.updateColumnValue("Tenor", productOfTenorSyncList.get(f).getTenor());
//                                        updtenor.updateColumnValue("FlatRate", productOfTenorSyncList.get(f).getFlatRate());
//                                        updtenor.updateColumnValue("EffectiveRate", productOfTenorSyncList.get(f).getEffectiveRate());
//                                        updtenor.updateColumnValue("GrossYieldRate", productOfTenorSyncList.get(f).getGrossYieldRate());
//                                        updtenor.updateColumnValue("AdminFee", productOfTenorSyncList.get(f).getAdminFee());
//                                        updtenor.updateColumnValue("FiduciaFee", productOfTenorSyncList.get(f).getFidusiaFee());
//                                        updtenor.updateColumnValue("WarningMinimumIncome", productOfTenorSyncList.get(f).getWarningMinimumIncome());
//                                        updtenor.updateColumnValue("FirstInstallment", productOfTenorSyncList.get(f).getFirstInstallment());
//                                        updtenor.updateColumnValue("InstallmentAmount", productOfTenorSyncList.get(f).getInstallmentAmount());
//                                        updtenor.updateColumnValue("NTF", productOfTenorSyncList.get(f).getNtf());
//                                        updtenor.updateColumnValue("AdminFeeFlag", productOfTenorSyncList.get(f).getAdminFeeFlag());
//                                        updtenor.updateColumnValue("AdminFeeForNTFPercentage", productOfTenorSyncList.get(f).getAdminFeeForNTFPercentage());
//                                        updtenor.updateColumnValue("DiscountInstallmentPercentage", productOfTenorSyncList.get(f).getDiscountInstallmentPercentage());
//                                        updtenor.updateColumnValue("DiscountOTRAmount", productOfTenorSyncList.get(f).getDiscountOTRAmount());
//                                        updtenor.updateColumnValue("DiscountOTRPercentage", productOfTenorSyncList.get(f).getDiscountOTRPercentage());
//                                        updtenor.updateColumnValue("IsActive", productOfTenorSyncList.get(f).getIsActive());
//                                        updtenor.update();
//                                    } else {
//                                        databaseService.getProductOfTenorDao().create(productOfTenor);
//                                    }
//                                } catch (SQLException e) {
//                                    if (BuildConfig.DEBUG)
//                                        Log.e("ProdTenor", String.valueOf(e));
//                                    Crashlytics.logException(e);
//                                }
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback ProdTenor", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
////            SUPPLIER MASTER ====================================================================================
//                try {
//                    databaseService.getSupplierMasterDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            for (int l = 0; l < supplierMasterSyncList.size(); l++) {
//                                final SupplierMasterArrayList supplierMaster = new SupplierMasterArrayList();
//                                List<SupplierMasterArrayList> supplierMasters = new ArrayList<>();
//
//                                supplierMaster.setBranchId(supplierMasterSyncList.get(l).getBranchId());
//                                supplierMaster.setSupplierID(supplierMasterSyncList.get(l).getSupplierID());
//                                supplierMaster.setSupplierName(supplierMasterSyncList.get(l).getSupplierName());
//                                supplierMaster.setVarietyOfAssetSell(supplierMasterSyncList.get(l).getVarietyOfAssetSell());
//                                supplierMaster.setSupplierAccID(supplierMasterSyncList.get(l).getSupplierAccID());
//                                supplierMaster.setSupplierAccountNo(supplierMasterSyncList.get(l).getSupplierAccountNo());
//                                supplierMaster.setSupplierAccountName(supplierMasterSyncList.get(l).getSupplierAccountName());
//                                supplierMaster.setIsActive(supplierMasterSyncList.get(l).getIsActive());
//
//                                try {
//                                    supplierMasters = databaseService.getSupplierMasterDao().queryBuilder().where()
//                                            .eq("SupplierID", supplierMasterSyncList.get(l).getSupplierID()).and()
//                                            .eq("BranchID", supplierMasterSyncList.get(l).getSupplierName()).query();
//                                    if (!supplierMasters.isEmpty()) {
//                                        UpdateBuilder<SupplierMasterArrayList, String> updsupp = databaseService.getSupplierMasterDao().updateBuilder();
//                                        updsupp.where().eq("SupplierID", supplierMasterSyncList.get(l).getBranchId());
//                                        updsupp.updateColumnValue("BranchID", supplierMasterSyncList.get(l).getSupplierName());
//                                        updsupp.updateColumnValue("SupplierName", supplierMasterSyncList.get(l).getSupplierName());
//                                        updsupp.updateColumnValue("VarietyOfAssetSell", supplierMasterSyncList.get(l).getVarietyOfAssetSell());
//                                        updsupp.updateColumnValue("SupplierAccID", supplierMasterSyncList.get(l).getSupplierAccID());
//                                        updsupp.updateColumnValue("SupplierAccountNo", supplierMasterSyncList.get(l).getSupplierAccountNo());
//                                        updsupp.updateColumnValue("SupplierAccountName", supplierMasterSyncList.get(l).getSupplierAccountName());
//                                        updsupp.updateColumnValue("IsActive", supplierMasterSyncList.get(l).getIsActive());
//                                        updsupp.update();
//                                    } else {
//                                        databaseService.getSupplierMasterDao().create(supplierMaster);
//                                    }
//                                } catch (SQLException e) {
//                                    if (BuildConfig.DEBUG)
//                                        Log.e("Supplier Master", String.valueOf(e));
//                                    Crashlytics.logException(e);
//                                }
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback JobType", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
////            DATA FINANSIAL ====================================================================================
//                try {
//                    databaseService.getDataFinansialDao().callBatchTasks(new Callable<Object>() {
//                        @Override
//                        public Object call() throws Exception {
//                            DataFinansial dataFinansial = new DataFinansial();
//                            List<DataFinansial> dataFinansials = new ArrayList<>();
//
//                            dataFinansial.setPeopleInsuranceNominal(dataFinansialSync.getPeopleInsuranceNominal());
//                            dataFinansial.setPeopleInsurancePersen(dataFinansialSync.getPeopleInsurancePersen());
//                            dataFinansial.setProductInsuranceNominal(dataFinansialSync.getProductInsuranceNominal());
//                            dataFinansial.setProductInsurancePersen(dataFinansialSync.getProductInsurancePersen());
//                            dataFinansial.setTotalInsuranceNominal(dataFinansialSync.getTotalInsuranceNominal());
//                            dataFinansial.setTotalInsurancePersen(dataFinansialSync.getTotalInsurancePersen());
//                            dataFinansial.setAdminFeeLainnya(dataFinansialSync.getAdminFeeLainnya());
//
//                            try {
//                                dataFinansials = databaseService.getDataFinansialDao().queryBuilder().where()
//                                        .eq("AdminFeeLainnya", dataFinansialSync.getAdminFeeLainnya()).or().eq("ProductInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
//                                        .or().eq("ProductInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("PeopleInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
//                                        .or().eq("PeopleInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("TotalInsurancePersen", dataFinansialSync.getTotalInsurancePersen())
//                                        .or().eq("TotalInsuranceNominal", dataFinansialSync.getTotalInsuranceNominal()).query();
//                                if (!dataFinansials.isEmpty()) {
//                                    UpdateBuilder<DataFinansial, String> updateFinansial = databaseService.getDataFinansialDao().updateBuilder();
//                                    updateFinansial.where().eq("AdminFeeLainnya", dataFinansialSync.getAdminFeeLainnya()).or().eq("ProductInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
//                                            .or().eq("ProductInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("PeopleInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
//                                            .or().eq("PeopleInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("TotalInsurancePersen", dataFinansialSync.getTotalInsurancePersen())
//                                            .or().eq("TotalInsuranceNominal", dataFinansialSync.getTotalInsuranceNominal());
//                                    updateFinansial.updateColumnValue("AdminFeeLainnya", dataFinansialSync.getAdminFeeLainnya());
//                                    updateFinansial.updateColumnValue("ProductInsurancePersen", dataFinansialSync.getPeopleInsurancePersen());
//                                    updateFinansial.updateColumnValue("ProductInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal());
//                                    updateFinansial.updateColumnValue("PeopleInsurancePersen", dataFinansialSync.getPeopleInsurancePersen());
//                                    updateFinansial.updateColumnValue("PeopleInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal());
//                                    updateFinansial.updateColumnValue("TotalInsurancePersen", dataFinansialSync.getTotalInsurancePersen());
//                                    updateFinansial.updateColumnValue("TotalInsuranceNominal", dataFinansialSync.getTotalInsuranceNominal());
//                                    updateFinansial.update();
//                                } else {
//                                    databaseService.getDataFinansialDao().create(dataFinansial);
//                                }
//                            } catch (SQLException e) {
//                                if (BuildConfig.DEBUG)
//                                    Log.e("Data Finansial", String.valueOf(e));
//                                Crashlytics.logException(e);
//                            }
//                            return null;
//                        }
//                    });
//                } catch (Exception e) {
//                    if (BuildConfig.DEBUG)
//                        Log.e("callback JobType", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            if (masterSyncMvpView != null)
//                masterSyncMvpView.onSuccessLoadmasterSync();
//        }
//    }
//
//    private void addResultKelurahan(int zipcode, String kelurahan, String kecamatan, String city, String isActive) {
//        ResultKelurahan resultKelurahan = new ResultKelurahan();
//
//        if ("1".equalsIgnoreCase(isActive) || "true".equalsIgnoreCase(isActive)) {
//            resultKelurahan.setKelurahan(kelurahan);
//            resultKelurahan.setResult(city + " ," + kecamatan + " , " + kelurahan + " , " + zipcode);
//            resultKelurahan.setZipcode(zipcode);
//            if (BuildConfig.DEBUG)
//                Log.e("Result-ADD", city + " ," + kecamatan + " , " + kelurahan + " , " + zipcode);
//            try {
//                databaseService.getResultKelurahanDao().createOrUpdate(resultKelurahan);
//            } catch (SQLException e) {
//                if (BuildConfig.DEBUG) {
//                    Log.e("Add Kelurahan", String.valueOf(e));
//                    Crashlytics.logException(e);
//                }
//            }
//        }
//    }
//
//    private void updateResultKelurahan(int zipcode, String kelurahan, String kecamatan, String city, String isActive) {
//        try {
//            UpdateBuilder<ResultKelurahan, String> updateBuilder = databaseService.getResultKelurahanDao().updateBuilder();
//            updateBuilder.where().eq("Kelurahan", kelurahan).and().eq("ZipCode", zipcode);
//            updateBuilder.updateColumnValue("Kelurahan", kelurahan);
//            updateBuilder.updateColumnValue("ZipCode", zipcode);
//            updateBuilder.updateColumnValue("result", city + " ," + kecamatan + " , " + kelurahan + " , " + zipcode);
//            updateBuilder.update();
//        } catch (SQLException e) {
//            if (BuildConfig.DEBUG)
//                Log.e("Update Kelurahan", String.valueOf(e));
//            Crashlytics.logException(e);
//        }
//        if (BuildConfig.DEBUG)
//            Log.e("Result-Update", city + " ," + kecamatan + " , " + kelurahan + " , " + zipcode);
//    }
//}
