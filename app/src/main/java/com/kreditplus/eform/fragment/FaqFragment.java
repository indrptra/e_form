package com.kreditplus.eform.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.model.response.objecthelper.FaqObjt;
import com.kreditplus.eform.model.response.objecthelper.Syarat;
import com.kreditplus.eform.model.response.objecthelper.TidakCancel;
import com.kreditplus.eform.presenter.SyaratDanKetentuanPresenter;
import com.kreditplus.eform.presenter.mvpview.SyaratDanKetentuanMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 03/03/17.
 */

public class FaqFragment extends BaseFragment implements SyaratDanKetentuanMvpView {

    @BindView(R.id.tv_isi_faq)
    TextView tvIsiFaq;

    @Inject
    SharedPreferences sharedPreferences;

    private ViewAnimator vaFaq;
    private String token;
    private SyaratDanKetentuanPresenter mSyaratDanKetentuanPresenter;
    private String faqString;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        init(view);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("BANTUAN");
        ButterKnife.bind(this,view);
        App.appComponent().inject(this);
        token = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        vaFaq = (ViewAnimator) view.findViewById(R.id.va_faq);
        mSyaratDanKetentuanPresenter = new SyaratDanKetentuanPresenter();
        mSyaratDanKetentuanPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSyaratDanKetentuanPresenter.syaratLoad(token);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSyaratDanKetentuanPresenter.detachView();
    }

    private void init(View view) {
    }

    @Override
    public void onPreLoadSyarat() {
        vaFaq.setDisplayedChild(1);
    }

    @Override
    public void onSuccessSyarat(Syarat syarats, TidakCancel tidakCancels, FaqObjt faq) {
        vaFaq.setDisplayedChild(2);
        tvIsiFaq.setText(Html.fromHtml(faq.getDescription()));
    }

    @Override
    public void onFailedLoadSyarat(String message) {
        vaFaq.setDisplayedChild(0);
    }

    @Override
    public void onSyaratTokenExpired() {

    }

    @OnClick(R.id.btn_retry_faq)
    public void Resend(){
        mSyaratDanKetentuanPresenter.syaratLoad(token);
    };
}
