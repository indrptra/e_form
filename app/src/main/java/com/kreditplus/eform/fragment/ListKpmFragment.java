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
import com.kreditplus.eform.adapter.ListKpmAdapter;
import com.kreditplus.eform.model.bus.RefreshListKpm;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;
import com.kreditplus.eform.presenter.PengajuanListPresenter;
import com.kreditplus.eform.presenter.mvpview.PengajuanListMvpView;
import com.paginate.Paginate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 17/04/17.
 */

public class ListKpmFragment extends BaseFragment implements PengajuanListMvpView, Paginate.Callbacks, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_list_kpm)
    RecyclerView rvListKpm;
    @BindView(R.id.txt_error_kpm)
    TextView txtError;

    @Inject
    SharedPreferences sharedPreferences;

    private ViewAnimator vaKpm;
    private PengajuanListPresenter mPengajuanListPresenter;
    private String mToken;
    private ListKpmAdapter adapter;
    private List<Pengajuan> mPengajuanList = new ArrayList<>();
    private Paginate paginate;
    private boolean loading;
    private boolean mHasLoadedAllItems;
    private LinearLayoutManager linearLayoutManager;
    private int limit = 10;
    private String filter = "all";
    private int filterValue;
    private List<String> appIdList = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_list_kpm, container, false);
        ButterKnife.bind(this, view);
        vaKpm = (ViewAnimator) view.findViewById(R.id.va_list_kpm);
        App.appComponent().inject(this);

        EventBus.getDefault().register(this);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_kpm);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefreshWsipe = false;

        mPengajuanListPresenter = new PengajuanListPresenter();
        mPengajuanListPresenter.attachView(this);
        mToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");

        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("PENGAJUAN KPM");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListKpm.setLayoutManager(linearLayoutManager);

        adapter = new ListKpmAdapter(getActivity(), mPengajuanList);
        rvListKpm.setAdapter(adapter);

        setupPaginate();
    }

    private void setupPaginate() {
        if (paginate != null)
            paginate.unbind();

        loading = false;
        mHasLoadedAllItems = false;

        paginate = Paginate.with(rvListKpm, this)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPengajuanListPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        mPengajuanList.clear();
        mHasLoadedAllItems = true; // set load all item true agar tidak memanggil onreload setelah hit api
        LoadPengajuan();
        onRefreshWsipe = true;

    }

    private void LoadPengajuan() {
        mPengajuanList.clear();
        adapter.notifyDataSetChanged();
        Map<String, String> mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        mPengajuanListPresenter.pengajuanList(mToken, mPengajuanList.size(), limit, filter, filterValue, "", "", mapId, "kpm","false");
    }

    @OnClick(R.id.btn_retry)
    public void retryKpmList() {
        mHasLoadedAllItems = true; // set load all item true agar tidak memanggil onreload setelah hit api
        LoadPengajuan();
    }

    @Override
    public void onPreLoadPengajuan() {
        if (mPengajuanList.isEmpty()) {
            vaKpm.setDisplayedChild(2);
        }
    }

    @Override
    public void onSuccessLoadPengajuan(int totalData, List<Pengajuan> pengajuanList) {
        if (onRefreshWsipe) {
            swipeRefreshLayout.setRefreshing(false);
            onRefreshWsipe = false;
        }

        loading = false;
        if (pengajuanList.size() <= 0 && mPengajuanList.size() <= 0) {
            vaKpm.setDisplayedChild(0);
        } else {
            vaKpm.setDisplayedChild(3);
            if (pengajuanList.size() > 0) {
                mHasLoadedAllItems = false;
                mPengajuanList.addAll(pengajuanList);
                adapter.notifyItemRangeInserted(mPengajuanList.size(), pengajuanList.size());
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
        if (mPengajuanList.isEmpty()) {
            vaKpm.setDisplayedChild(1);
            txtError.setText(message);
        }
    }

    @Override
    public void onTokenExpired() {

    }

    @Subscribe
    public void refreshListKpm(RefreshListKpm e) {
        mHasLoadedAllItems = true; // set load all item true agar tidak memanggil onreload setelah hit api
        LoadPengajuan();
    }

    @Override
    public void onLoadMore() {
        loading = true;
        Map<String, String> mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        mPengajuanListPresenter.pengajuanList(mToken, mPengajuanList.size(), limit, filter, filterValue, "","", mapId, "kpm","false");
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return mHasLoadedAllItems;
    }


}
