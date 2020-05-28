package com.kreditplus.eform.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.activity.SignatureActivity;
import com.kreditplus.eform.activity.ViewKreditmuActivity;
import com.kreditplus.eform.adapter.KreditmuAdapter;
import com.kreditplus.eform.dialog.CodePersetujuanDialog;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.bus.ConfirmCodeSignature;
import com.kreditplus.eform.model.bus.ContextKreditmuFragment;
import com.kreditplus.eform.model.bus.ResendCodeSignatureEvent;
import com.kreditplus.eform.model.bus.SendDataKreditmu;
import com.kreditplus.eform.model.bus.SignatureEvent;
import com.kreditplus.eform.model.response.KreditmuResponse;
import com.kreditplus.eform.model.response.objecthelper.AssetToken;
import com.kreditplus.eform.model.response.objecthelper.AttachmentObjt;
import com.kreditplus.eform.model.response.objecthelper.BodyToken;
import com.kreditplus.eform.model.response.objecthelper.KreditmuEdu;
import com.kreditplus.eform.model.response.objecthelper.KreditmuHome;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobPosition;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobType;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKecamatan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKelurahan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuMarital;
import com.kreditplus.eform.model.response.objecthelper.KreditmuObjt;
import com.kreditplus.eform.presenter.CodeSignaturePresenter;
import com.kreditplus.eform.presenter.KreditmuPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.CodeSignatureMvpView;
import com.kreditplus.eform.presenter.mvpview.KreditmuMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.mobsandgeeks.saripaar.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import itsmagic.present.permissionhelper.util.PermissionHelper;

/**
 * Created by apc-lap012 on 12/07/17.
 */

public class KreditmuFragment extends BaseFragment implements KreditmuMvpView, RefreshTokenMvpView, CodeSignatureMvpView {

    private static final int SIGNATURE_PEMOHON = 501;
    private static final int SIGNATURE_PASANGAN = 502;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor editor;

    @BindView(R.id.rv_kreditmu)
    RecyclerView rvKreditmu;
    @BindView(R.id.va_kreditmu)
    ViewAnimator vaKreditmu;

    @BindArray(R.array.kreditmu_field_name_personal)
    String[] arrayKreditmuFieldNamePersonal;
    @BindArray(R.array.kreditmu_field_type_personal)
    String[] arrayKreditmuFieldTypePersonal;
    @BindArray(R.array.kreditmu_field_name_alamat)
    String[] arrayKreditmuFieldNameAlamat;
    @BindArray(R.array.kreditmu_field_type_alamat)
    String[] arrayKreditmuFieldTypeAlamat;
    @BindArray(R.array.kreditmu_field_name_pekerjaan)
    String[] arrayKreditmuFieldNamePekerjaan;
    @BindArray(R.array.kreditmu_field_type_pekerjaan)
    String[] arrayKreditmuFieldTypePekerjaan;
    @BindArray(R.array.kreditmu_field_name_button)
    String[] arrayKreditmuFieldNameButton;
    @BindArray(R.array.kreditmu_field_type_button)
    String[] arrayKreditmuFieldTypeButton;
    @BindArray(R.array.kreditmu_first_data)
    String[] arrayKreditmuFirstData;
    @BindArray(R.array.validationKreditmu)
    String[] arrayValidationKreditmu;
    @BindArray(R.array.kreditmu_field_name_picture)
    String[] arrayKreditmuFieldNamePicture;
    @BindArray(R.array.kreditmu_field_type_picture)
    String[] arrayKreditmuFieldTypePicture;

    private KreditmuAdapter adapter;
    private ViewAnimator va;
    private LinearLayoutManager linearLayoutManager;
    private KreditmuPresenter mKreditmuPresenter;
    private RefreshTokenPresenter mRefreshTokenPresenter;
    private String token;
    private List<KreditmuObjt> kreditmulist;
    private Map<String, String> mapJob;
    private Map<String, String> mapForSubmit;
    private Map<String, String> mapSpinner;
    private Map<String, String> mapValueSPinner;
    private Map<Integer, File> mapAttachment;
    private List<AttachmentObjt> attachmentList;
    private List<AttachmentObjt> signatureList;
    private String downloadType;
    private ProgressDialog progressDialog;
    private Validator validator;

    private List<KreditmuEdu> eduList;
    private List<KreditmuMarital> maritalList;
    private List<KreditmuHome> homeList;
    private List<KreditmuKecamatan> kecamatanList;
    private List<KreditmuKelurahan> kelurahanList;
    private List<KreditmuJobType> jobtypeList;
    private List<KreditmuJobPosition> jobPosList;
    private List<String> validaitonList;
    private int count;
    private KreditmuResponse dataKredimuList;

    private KreditmuAdapter.VHKota holder;
    private int kecamatanGroup;
    private int kelurahanGroup;
    private int zipGroup;
    private Boolean onRefreshWsipe;
    private String handphone;

    private CodeSignaturePresenter mCodeSignaturePresenter;

    private PermissionHelper mPermissionHelper;
    private String pid;
    private int countTokenSms;

    private String usedOTP;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);

        mKreditmuPresenter = new KreditmuPresenter();
        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mCodeSignaturePresenter = new CodeSignaturePresenter();

        mKreditmuPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        mCodeSignaturePresenter.attachView(this);

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        pid = Util.RandomDateNumber();
        countTokenSms = 0;

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kreditmu, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("KREDITMU");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvKreditmu.setLayoutManager(linearLayoutManager);
        firstData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mKreditmuPresenter.detachView();
        mRefreshTokenPresenter.detachView();
        mCodeSignaturePresenter.detachView();
        EventBus.getDefault().unregister(this);
    }


    private void firstData() {
        dataKredimuList = new KreditmuResponse();
        downloadType = "firstdata";
        mKreditmuPresenter.spinnerKreditmu(token, arrayKreditmuFirstData[0]);
    }

    private void dataField() {

        kreditmulist = new ArrayList<>();
        validaitonList = new ArrayList<>();

        for (int i = 0; i < arrayKreditmuFieldNamePersonal.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNamePersonal[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypePersonal[i]);
            kreditmuObjt.setGroup(1);
            kreditmulist.add(kreditmuObjt);
        }

        for (int i = 0; i < arrayKreditmuFieldNameAlamat.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNameAlamat[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypeAlamat[i]);
            kreditmuObjt.setGroup(2);
            kreditmulist.add(kreditmuObjt);
        }

        for (int i = 0; i < arrayKreditmuFieldNamePekerjaan.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNamePekerjaan[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypePekerjaan[i]);
            kreditmuObjt.setGroup(3);
            kreditmulist.add(kreditmuObjt);
        }

        for (int i = 0; i < arrayKreditmuFieldNamePicture.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNamePicture[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypePicture[i]);
            kreditmuObjt.setGroup(5);
            kreditmulist.add(kreditmuObjt);
        }

        for (int i = 0; i < arrayKreditmuFieldNameButton.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNameButton[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypeButton[i]);
            kreditmuObjt.setGroup(4);
            kreditmulist.add(kreditmuObjt);
        }

        for (int i = 0; i < arrayValidationKreditmu.length; i++) {
            validaitonList.add(arrayValidationKreditmu[i]);
        }

        adapter = new KreditmuAdapter(getActivity(), kreditmulist, dataKredimuList, validaitonList, this);
        rvKreditmu.setAdapter(adapter);
    }

    public void getKecamatan(int kecamatanGroup, String city) {
        this.kecamatanGroup = kecamatanGroup;
        downloadType = "kecamatan";
        mKreditmuPresenter.kecamatanKreditmu(token, city);
    }

    public void getKelurahan(int kelurahanGroup, String kecamatan, String city) {
        this.kelurahanGroup = kelurahanGroup;
        downloadType = "kelurahan";
        mKreditmuPresenter.kelurahanKreditmu(token, city, kecamatan);
    }

    public void getZipCode(String city, String kecamatan, String kelurahan, int group) {
        this.zipGroup = group;
        downloadType = "zipcode";
        mKreditmuPresenter.zipCodeKreditmu(token, city, kecamatan, kelurahan);
    }

    public void getJobType(String professionId) {
        downloadType = "jobtype";
        mKreditmuPresenter.jobTypeKreditmu(token, professionId);
    }

    public void getJobPos(String profId, String jobTypeId) {
        downloadType = "jobposition";
        mKreditmuPresenter.jobPosition(token, profId, jobTypeId);
    }

    public void viewDataKreditmu(Map<String, String> map, List<KreditmuObjt> kreditmuList, Map<String, String> mapJob,
                                 List<AttachmentObjt> attachmentList, List<AttachmentObjt> signatureList, Map<Integer, File> mapAttachment) {
        this.mapForSubmit = map;
        this.mapJob = mapJob;
        this.attachmentList = attachmentList;
        this.signatureList = signatureList;
        this.mapAttachment = mapAttachment;
        prepareDataSpinner();
        cekAlamatKtp(mapForSubmit);
//        submitData(mapForSubmit,mapJob,mapSpinner);
        Intent intent = new Intent(getActivity(), ViewKreditmuActivity.class);
        intent.putExtra("mapForSubmit", (Serializable) mapForSubmit);
        intent.putExtra("mapJob", (Serializable) mapJob);
        intent.putExtra("mapSpinner", (Serializable) mapValueSPinner);
        intent.putExtra("mapAttachment", (Serializable) mapAttachment);
        startActivity(intent);
    }


    public void prepareDataSpinner() {
        mapSpinner = new HashMap<>();
        mapValueSPinner = new HashMap<>();

        mapSpinner.put("Pendidikan", dataKredimuList.getEduList().get(Integer.parseInt(mapForSubmit.get("Pendidikan"))).getEducationId());
        mapSpinner.put("Status pernikahan", dataKredimuList.getMaritalList().get(Integer.parseInt(mapForSubmit.get("Status pernikahan"))).getMaritalId());
        mapSpinner.put("Status kepemilikan rumah", dataKredimuList.getHomeList().get(Integer.parseInt(mapForSubmit.get("Status kepemilikan rumah"))).getHomeId());

        mapValueSPinner.put("Pendidikan", dataKredimuList.getEduList().get(Integer.parseInt(mapForSubmit.get("Pendidikan"))).getEducationName());
        mapValueSPinner.put("Status pernikahan", dataKredimuList.getMaritalList().get(Integer.parseInt(mapForSubmit.get("Status pernikahan"))).getMaritalName());
        mapValueSPinner.put("Status kepemilikan rumah", dataKredimuList.getHomeList().get(Integer.parseInt(mapForSubmit.get("Status kepemilikan rumah"))).getHomeName());
    }

    private void cekAlamatKtp(Map<String, String> mapForSubmit) {
        String alamat = "";
        String kota = "";
        String kecamatan = "";
        String kelurahan = "";
        String zip = "";

        if ("true".equalsIgnoreCase(mapForSubmit.get("Sesuai Alamat Domisili"))) {
            alamat = mapForSubmit.get("Alamat tinggal domisili");
            mapForSubmit.put("Alamat sesuai ktp", alamat);

            for (int i = 0; i < kreditmulist.size(); i++) {
                if (kreditmulist.get(i).getGroup() == 1) {
                    if ("kota".equalsIgnoreCase(kreditmulist.get(i).getType())) {
                        kota = mapForSubmit.get(kreditmulist.get(i).getName());
                        mapForSubmit.put("Kota ktp", kota);
                    } else if ("kecamatanDomisili".equalsIgnoreCase(kreditmulist.get(i).getType())) {
                        kecamatan = mapForSubmit.get(kreditmulist.get(i).getName());
                        mapForSubmit.put("Kecamatan ktp", kecamatan);
                    } else if ("kelurahanDomisili".equalsIgnoreCase(kreditmulist.get(i).getType())) {
                        kelurahan = mapForSubmit.get(kreditmulist.get(i).getName());
                        mapForSubmit.put("Kelurahan ktp", kelurahan);
                    } else if ("zip".equalsIgnoreCase(kreditmulist.get(i).getType())) {
                        zip = mapForSubmit.get(kreditmulist.get(i).getName());
                        mapForSubmit.put("Kode pos ktp", zip);
                    }
                }
            }
        }
    }

    @Subscribe
    public void submitData(SendDataKreditmu e) {
        downloadType = "Submit";
        mKreditmuPresenter.submit(token, mapForSubmit, mapJob, mapSpinner, attachmentList, signatureList);
    }


    @Override
    public void onPreLoadKreditmu() {
        if ("firstdata".equalsIgnoreCase(downloadType)) {
            vaKreditmu.setDisplayedChild(0);
        } else if ("Submit".equalsIgnoreCase(downloadType)) {
            progressDialog.show();
            progressDialog.setMessage("Proccess");
        }

    }

    @Override
    public void onSuccessLoadKreditmu(KreditmuResponse kreditmuResponse) {
        if ("firstdata".equalsIgnoreCase(downloadType)) {
            if (!kreditmuResponse.getEduList().isEmpty()) {
                dataKredimuList.setEduList(kreditmuResponse.getEduList());
            } else if (!kreditmuResponse.getHomeList().isEmpty()) {
                dataKredimuList.setHomeList(kreditmuResponse.getHomeList());
            } else if (!kreditmuResponse.getMaritalList().isEmpty()) {
                dataKredimuList.setMaritalList(kreditmuResponse.getMaritalList());
            } else if (!kreditmuResponse.getCityList().isEmpty()) {
                dataKredimuList.setCityList(kreditmuResponse.getCityList());
            } else if (!kreditmuResponse.getProfessionList().isEmpty()) {
                dataKredimuList.setProfessionList(kreditmuResponse.getProfessionList());
            }


            if (count < arrayKreditmuFirstData.length - 1) {
                count += 1;
                mKreditmuPresenter.spinnerKreditmu(token, arrayKreditmuFirstData[count]);
            } else {
                count = 0;
                vaKreditmu.setDisplayedChild(1);
                dataField();
            }
        } else if ("kecamatan".equalsIgnoreCase(downloadType)) {
            kecamatanList = new ArrayList<>();
            if (!kreditmuResponse.getKecamatanList().isEmpty()) {
                kecamatanList = kreditmuResponse.getKecamatanList();
                adapter.addKecamatan(kecamatanList, kecamatanGroup);
            }
        } else if ("kelurahan".equalsIgnoreCase(downloadType)) {
            kelurahanList = new ArrayList<>();
            if (!kreditmuResponse.getKelurahanList().isEmpty()) {
                kelurahanList = kreditmuResponse.getKelurahanList();
                adapter.addKelurahan(kelurahanList, kelurahanGroup);
            }
        } else if ("zipcode".equalsIgnoreCase(downloadType)) {
            if (!kreditmuResponse.getZipCode().isEmpty()) {
                String branchName = kreditmuResponse.getZipCode().get(0).getBranchName();
                String zipCode = kreditmuResponse.getZipCode().get(0).getZipCode();
                adapter.addZipCode(branchName, zipCode, zipGroup);
            }
        } else if ("jobtype".equalsIgnoreCase(downloadType)) {
            if (!kreditmuResponse.getJobTypeList().isEmpty()) {
                jobtypeList = kreditmuResponse.getJobTypeList();
                adapter.addJobType(jobtypeList);
            }
        } else if ("jobposition".equalsIgnoreCase(downloadType)) {
            if (!kreditmuResponse.getJobPositionList().isEmpty()) {
                jobPosList = kreditmuResponse.getJobPositionList();
                adapter.addJobPosition(jobPosList);
            }
        } else if ("Submit".equalsIgnoreCase(downloadType)) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Berhasil Input Data", Toast.LENGTH_SHORT).show();
            countTokenSms = 0;
            dataField();
        }

    }

    @Override
    public void onFailedLoadKreditmu(String message) {
        vaKreditmu.setDisplayedChild(1);
        if ("firstdata".equalsIgnoreCase(downloadType)) {
            dataField();
            Toast.makeText(getContext(), "Data Tidak Berhasil Terdownload", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onTokenKreditmuExpired() {
        String checkToken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checkToken);
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.commit();
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreSendCodeSignature() {
    }

    @Override
    public void onSuccessSendCodeSignature(int count) {
        countTokenSms += 1;
    }

    @Override
    public void onFailedSendCodeSignature(String message) {
        if (BuildConfig.DEBUG)
            Log.e("Failed request sms", message);
    }

    @Override
    public void onTokenSendCodeSignatureExpired() {
        String cekToken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
    }

    @Override
    public void onPreSendConfirmSignature() {
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }

    @Override
    public void onSuccessConfirmCodeSignature(int status, String message, String usedOTP) {
        progressDialog.dismiss();
        if (status == 1) {
            adapter.SetIsUserValid(true);
            this.usedOTP = usedOTP;
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            adapter.SetIsUserValid(false);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailedConfirmCodeSignature(String message) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenConfirmCodeSignatureExpired() {
        String cekToken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
    }

    // method

    private void showCamera(int requestCode) {
    }

    public void ShowSignature() {
        Intent intent = new Intent(getContext(), SignatureActivity.class);
        intent.putExtra("tipe_ttd", SIGNATURE_PEMOHON);
        startActivity(intent);
    }

    public void SendCode(String handphone) {
        this.handphone = handphone;
        BodyToken bodyToken = new BodyToken();
        List<AssetToken> list = new ArrayList<>();
        AssetToken assetToken = new AssetToken();

        assetToken.setAssetKode("");
        assetToken.setType("");
        assetToken.setAssetKode("");
        list.add(assetToken);

        bodyToken.setMobileSubmision("q");
        bodyToken.setType("kreditmu_signature");
        bodyToken.setHandphone(handphone);
        bodyToken.setFullName("");
        bodyToken.setApplicationPid(pid);
        bodyToken.setInstallmentAmount("1");
        bodyToken.setAssetTokenList(list);

        mCodeSignaturePresenter.sendCodeSignature(token, bodyToken);
        showTokenDialog(handphone);
    }

    private void showTokenDialog(String handphone) {
        DialogFragment dialog = new CodePersetujuanDialog();
        Bundle bundle = new Bundle();
        bundle.putString("NOMER_HANDPHONE", handphone);
        bundle.putInt("COUNT", countTokenSms);
        dialog.setArguments(bundle);
        dialog.show(getActivity().getSupportFragmentManager(), "CodePersetujuanDialog");
    }

    public void addToImageAdapter(String path, int requestCode) {
        adapter.InsertToImageBox(path, requestCode);
    }

    // Event Bus

    @Subscribe
    public void setSignatureEvent(SignatureEvent e) {
        adapter.SetSignature(e.getBitmap());
    }

    @Subscribe
    public void confirmCodeSignature(ConfirmCodeSignature e) {
        mCodeSignaturePresenter.confirmCodeSignature(token, "kreditmu_signature", e.getCode(), "q", pid);
    }

    @Subscribe
    public void onSendCodeSignatureEvent(ResendCodeSignatureEvent e) {
        BodyToken bodyToken = new BodyToken();
        List<AssetToken> list = new ArrayList<>();
        AssetToken assetToken = new AssetToken();

        assetToken.setAssetKode("");
        assetToken.setType("");
        assetToken.setAssetKode("");
        list.add(assetToken);

        bodyToken.setMobileSubmision("q");
        bodyToken.setType("kreditmu_signature");
        bodyToken.setHandphone(handphone);
        bodyToken.setFullName("");
        bodyToken.setApplicationPid(pid);
        bodyToken.setInstallmentAmount("1");
        bodyToken.setAssetTokenList(list);

        mCodeSignaturePresenter.sendCodeSignature(token, bodyToken);
        showTokenDialog(handphone);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void permissionCamera(final int requestCode) {
        mPermissionHelper = new PermissionHelper.Builder(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA)
                .withPermissionInfos(
                        "Camera Permission Needed")
                .withListener(new PermissionHelper.OnPermissionCheckedListener() {
                    @Override
                    public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
                        showCamera(requestCode);
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(getContext(), R.string.txt_tidak_memiliki_permissions_camera_storage, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        mPermissionHelper.requestPermission();
    }

}
