package com.kreditplus.eform.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.adapter.ListDraftAndSinkronisasiNonElcAdapter;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;
import com.kreditplus.eform.presenter.DeleteDraftPresenter;
import com.kreditplus.eform.presenter.PengajuanListPresenter;
import com.kreditplus.eform.presenter.mvpview.DeleteDraftMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanListMvpView;
import com.paginate.Paginate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDraftAndSinkronisasiNonElcFragment extends BaseFragment implements Paginate.Callbacks,
        PengajuanListMvpView, DeleteDraftMvpView {

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    PengajuanListPresenter mPengajuanListPresenter;

    private DeleteDraftPresenter mDeleteDraftPresenter;

    @BindView(R.id.tv_list_kosong)
    TextView tvListKosong;
    @BindView(R.id.rv_pengajuan_unsync)
    RecyclerView rvPengajuanUnsync;
    @BindView(R.id.va)
    RelativeLayout va;
    @BindView(R.id.pbLoadingDraftSync)
    ProgressBar pbLoadingDraftSync;

    private String mToken;
    private ListDraftAndSinkronisasiNonElcAdapter adapter;
    private List<Pengajuan> mPengajuanList = new ArrayList<>();
    private List<String> appIdList = new ArrayList<>();
    HashMap<String, String> mapId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_draft_and_sinkronisasi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");

        initConstuctorPresenter();
        initAttachPresenter();

        hitListDraftAndSync();
    }

    private void hitListDraftAndSync() {
        mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        mPengajuanListPresenter.pengajuanList(mToken, 0, 50, "all", 0, "", "", mapId, "", "true");

    }

    private void initAttachPresenter() {
        mPengajuanListPresenter.attachView(this);
        mDeleteDraftPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPengajuanListPresenter.detachView();
        mDeleteDraftPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    private void initConstuctorPresenter() {
        mPengajuanListPresenter = new PengajuanListPresenter();
        mDeleteDraftPresenter = new DeleteDraftPresenter();
    }

    @Override
    public void onPreLoadPengajuan() {
        preLoading();
    }

    @Override
    public void onSuccessLoadPengajuan(int totalData, List<Pengajuan> pengajuanList) {
        if (!pengajuanList.isEmpty()) tvListKosong.setVisibility(View.GONE);
        else tvListKosong.setVisibility(View.VISIBLE);

        RecyclerView.LayoutManager llRv = new LinearLayoutManager(getContext());
        rvPengajuanUnsync.setLayoutManager(llRv);
        rvPengajuanUnsync.setItemAnimator(new DefaultItemAnimator());
        adapter = new ListDraftAndSinkronisasiNonElcAdapter(mToken, getContext(), pengajuanList, this);
        rvPengajuanUnsync.setAdapter(adapter);
        successAndFailedLoading();
    }

    @Override
    public void onFailedLoadPengajuan(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onTokenExpired() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }

    private void preLoading() {
        tvListKosong.setVisibility(View.GONE);
        pbLoadingDraftSync.setVisibility(View.VISIBLE);
        rvPengajuanUnsync.setVisibility(View.GONE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void messageFailedApi(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void successAndFailedLoading() {
        rvPengajuanUnsync.setVisibility(View.VISIBLE);
        pbLoadingDraftSync.setVisibility(View.GONE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSavePengajuan(RefreshPengajuanUnsyncEvent e) {
        hitListDraftAndSync();
    }

    public void fragDeleteDraft(String id) {
        mDeleteDraftPresenter.DeleteDraft(mToken,id);
    }

    @Override
    public void onPreDeleteDraft() {
        preLoading();
    }

    @Override
    public void onSuccessDeleteDraft() {
        successAndFailedLoading();
        hitListDraftAndSync();
    }

    @Override
    public void onFailedDeleteDraft(String message) {
        messageFailedApi(message);
    }
}
