package com.kreditplus.eform.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.adapter.ListJanjiBertemuAdapter;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;
import com.kreditplus.eform.presenter.PengajuanListPresenter;
import com.kreditplus.eform.presenter.mvpview.PengajuanListMvpView;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 10/05/17.
 */

public class ListJanjiBertemuFragment extends BaseFragment implements PengajuanListMvpView, Paginate.Callbacks, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.rv_janji_bertemu)
    RecyclerView rvJanjiBertemu;
    @BindView(R.id.txt_error_api)
    TextView txtErrorApi;

    private ViewAnimator vaJanji;
    private List<Pengajuan> mJanjiList = new ArrayList<>();
    private ListJanjiBertemuAdapter adapter;
    private String token;
    private PengajuanListPresenter mPengajuanListPresenter;
    private List<String> appIdList = new ArrayList<>();
    private int limit = 10;
    private String filter = "all";
    private int filterValue;
    private Paginate paginate;
    private boolean loading;
    private boolean mHasLoadedAllItems;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Boolean onRefreshWsipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            appIdList = bundle.getStringArrayList("app_id_list");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_janji_bertemu, container, false);
        ButterKnife.bind(this, view);

        App.appComponent().inject(this);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");

        mPengajuanListPresenter = new PengajuanListPresenter();
        mPengajuanListPresenter.attachView(this);

        vaJanji = (ViewAnimator) view.findViewById(R.id.va_janji);
        adapter = new ListJanjiBertemuAdapter(getActivity(), mJanjiList);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_janji_kpm);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefreshWsipe = false;

        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("LIST JANJI BERTEMU");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvJanjiBertemu.setLayoutManager(linearLayoutManager);
        rvJanjiBertemu.setAdapter(adapter);

        LoadPengajuan();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPengajuanListPresenter.detachView();
    }

    @OnClick(R.id.btn_retry)
    public void retry() {
        mJanjiList.clear();
        mHasLoadedAllItems = true;
        LoadPengajuan();
    }

    private void setupPaginate() {
        if (paginate != null)
            paginate.unbind();

        loading = false;
        mHasLoadedAllItems = false;

        paginate = Paginate.with(rvJanjiBertemu, this)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();
    }

    private void LoadPengajuan() {
        mJanjiList.clear();
        adapter.notifyDataSetChanged();
        Map<String, String> mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        mPengajuanListPresenter.pengajuanList(token, mJanjiList.size(), limit, filter, filterValue, "", "", mapId, "kpm-list-reminder", "false");
    }


    @Override
    public void onPreLoadPengajuan() {
        vaJanji.setDisplayedChild(2);
    }

    @Override
    public void onSuccessLoadPengajuan(int totalData, List<Pengajuan> pengajuanList) {
        if (onRefreshWsipe) {
            swipeRefreshLayout.setRefreshing(false);
            onRefreshWsipe = false;
        }

        loading = false;
        if (pengajuanList.size() <= 0 && mJanjiList.size() <= 0) {
            vaJanji.setDisplayedChild(0);
        } else {
            vaJanji.setDisplayedChild(3);
            if (pengajuanList.size() > 0) {
                mHasLoadedAllItems = false;
                mJanjiList.addAll(pengajuanList);
                adapter.notifyItemRangeInserted(mJanjiList.size(), pengajuanList.size());
                setupPaginate();
            } else {
                mHasLoadedAllItems = true;
                paginate.setHasMoreDataToLoad(false);
            }
        }
    }

    @Override
    public void onFailedLoadPengajuan(String message) {
        if (onRefreshWsipe) {
            swipeRefreshLayout.setRefreshing(false);
            onRefreshWsipe = false;
        }

        loading = false;
        if (mJanjiList.isEmpty()) {
            vaJanji.setDisplayedChild(0);
            txtErrorApi.setText(message);
        } else {
            vaJanji.setDisplayedChild(1);
        }
    }

    @Override
    public void onTokenExpired() {

    }

    @Override
    public void onLoadMore() {
        loading = true;
        Map<String, String> mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        mPengajuanListPresenter.pengajuanList(token, mJanjiList.size(), limit, filter, filterValue, "","", mapId, "kpm-list-reminder","false");
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return mHasLoadedAllItems;
    }

    // onrefreshSwipe
    @Override
    public void onRefresh() {
        mJanjiList.clear();
        mHasLoadedAllItems = true;
        LoadPengajuan();
        onRefreshWsipe = true;
    }
}
