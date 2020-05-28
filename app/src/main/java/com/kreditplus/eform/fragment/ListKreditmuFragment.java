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
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.adapter.ListKreditmuAdapter;
import com.kreditplus.eform.model.response.objecthelper.KreditmuList;
import com.kreditplus.eform.presenter.KreditmuListPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.KreditmuListMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by apc-lap012 on 23/08/17.
 */

public class ListKreditmuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, KreditmuListMvpView,
        RefreshTokenMvpView{

    @BindView(R.id.txt_tidak_ada_list_kreditmu)
    TextView txtTidakAdaList;
    @BindView(R.id.txt_error_kreditmu_list)
    TextView txtErrorList;
    @BindView(R.id.rv_list_kreditmu)
    RecyclerView rvListKreditmu;
    @BindView(R.id.srl_list_kreditmu)
    SwipeRefreshLayout srlKreditmu;

    @Inject
    SharedPreferences sharedPreferences;

    private ViewAnimator vaKreditmu;
    private SwipeRefreshLayout swipeRefresh;
    private boolean onRefreshSwipe;
    private String token;
    private LinearLayoutManager linearLayout;
    private List<KreditmuList> kreditmulist;
    private ListKreditmuAdapter adapter;
    private KreditmuListPresenter mKreditmuListPresenter;
    private RefreshTokenPresenter mRefreshTokenPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_kreditmu,container,false);
        ButterKnife.bind(this,view);
        vaKreditmu = (ViewAnimator) view.findViewById(R.id.va_list_kreditmu);
        App.appComponent().inject(this);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_kreditmu);
        swipeRefresh.setOnRefreshListener(this);
        onRefreshSwipe = false;

        mKreditmuListPresenter = new KreditmuListPresenter();
        mRefreshTokenPresenter = new RefreshTokenPresenter();
        kreditmulist = new ArrayList<>();
        mKreditmuListPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        token = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");


        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("LIST KREDITMU");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvListKreditmu.setLayoutManager(linearLayout);
        adapter = new ListKreditmuAdapter(getActivity(),kreditmulist);
        rvListKreditmu.setAdapter(adapter);
        srlKreditmu.setOnRefreshListener(this);

        loadData();
    }

    private void loadData() {
        kreditmulist.clear();
        adapter.notifyDataSetChanged();
        mKreditmuListPresenter.dataKreditmu(token);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mKreditmuListPresenter.detachView();
        mRefreshTokenPresenter.detachView();
    }


    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onPreLoadKreditmuList() {
        vaKreditmu.setDisplayedChild(2);
    }

    @Override
    public void onSuccessLoadKreditmuList(List<KreditmuList> dataKreditmus) {
        if (dataKreditmus.isEmpty()){
            vaKreditmu.setDisplayedChild(0);
        }else{
            swipeRefresh.setRefreshing(false);
            kreditmulist.clear();
            kreditmulist.addAll(dataKreditmus);
            vaKreditmu.setDisplayedChild(3);
            adapter.setItemList(kreditmulist);
        }

    }

    @Override
    public void onFailedLoadKreditmuList(String message) {
        vaKreditmu.setDisplayedChild(2);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onTokenKreditmuListExpired() {
        String mToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        mKreditmuListPresenter.dataKreditmu(token);
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
