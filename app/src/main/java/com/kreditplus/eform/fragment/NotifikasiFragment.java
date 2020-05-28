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
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.adapter.NotificationAdapter;
import com.kreditplus.eform.model.bus.RefreshNotification;
import com.kreditplus.eform.model.response.objecthelper.Notifikasi;
import com.kreditplus.eform.presenter.NotifikasiListPresenter;
import com.kreditplus.eform.presenter.RecomendationPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.NotifikasiListMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iwan Nurdesa on 26/05/16.
 */
public class NotifikasiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        NotifikasiListMvpView, RefreshTokenMvpView {

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.rv_notifikasi)
    RecyclerView rvNotifikasi;
    @BindView(R.id.srl_notification)
    SwipeRefreshLayout srlNotification;

    @Inject
    NotifikasiListPresenter notifikasiListPresenter;

    @Inject
    RefreshTokenPresenter mRefreshTokenPresenter;

    @BindView(R.id.txt_error_api)
    TextView txtError;

    private ViewAnimator vaNotifikasi;
    private List<Notifikasi> mNotifikasiList = new ArrayList<>();
    private NotificationAdapter adapter;
    private String token;
    private String tipeCro;
    private boolean isrefresh;

    //    FORM REKOMENDASI
    private RecomendationPresenter mRecomendationPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        tipeCro = sharedPreferences.getString(getResources().getString(R.string.sharedpref_cro), "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifikasi, container, false);
        init(view);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("LIST NOTIFIKASI");
        EventBus.getDefault().register(this);
        srlNotification.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        notifikasiListPresenter.detachView();
        mRefreshTokenPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notifikasiListPresenter.notifikasiList(token);
    }

    @OnClick(R.id.btn_retry)
    public void retry() {
        notifikasiListPresenter.notifikasiList(token);
    }

    /**
     * Initialization
     *
     * @param view
     */
    private void init(View view) {
        ButterKnife.bind(this, view);
        notifikasiListPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        vaNotifikasi = (ViewAnimator) view.findViewById(R.id.va);
        adapter = new NotificationAdapter(getActivity(), mNotifikasiList, tipeCro);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotifikasi.setLayoutManager(linearLayoutManager);
        rvNotifikasi.setAdapter(adapter);
    }

    @Override
    public void onPreLoadNotifikasi() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mNotifikasiList.clear();
        if (isrefresh) {
            srlNotification.setRefreshing(true);
        } else {
            vaNotifikasi.setDisplayedChild(2);
        }
    }

    @Override
    public void onSuccessLoadNotifikasi(List<Notifikasi> notifikasiList) {
        mNotifikasiList = notifikasiList;

        if (mNotifikasiList.isEmpty()) {
            vaNotifikasi.setDisplayedChild(0);
        } else {
            vaNotifikasi.setDisplayedChild(3);
            adapter.notifyDataSetChanged();
            adapter.setNotifikasiList(mNotifikasiList);
        }
        if (isrefresh) {
            srlNotification.setRefreshing(false);
            isrefresh = false;
        }
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onFailedLoadNotifikasi(String message) {

        if (isrefresh) {
            srlNotification.setRefreshing(false);
            isrefresh = false;
        }
        vaNotifikasi.setDisplayedChild(1);
        txtError.setText(message);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onTokenExpired() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.apply();
        notifikasiListPresenter.notifikasiList(token);
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void refreshNotification(RefreshNotification e) {
        notifikasiListPresenter.notifikasiList(token);
    }

    @Override
    public void onRefresh() {
        isrefresh = true;
        notifikasiListPresenter.notifikasiList(token);
    }
}
