package com.kreditplus.eform.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.presenter.ChangePasswordPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.ChangePasswordMVPView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordMVPView, RefreshTokenMvpView {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ChangePasswordPresenter mChangePasswordPresenter;

    @Inject
    RefreshTokenPresenter mRefreshTokenPresenter;

    @BindView(R.id.tilUbahPasswordPasswordLama)
    TextInputLayout tilUbahPasswordPasswordLama;
    @BindView(R.id.tilUbahPasswordPasswordBaru)
    TextInputLayout tilUbahPasswordPasswordBaru;
    @BindView(R.id.tilUbahPasswordKonfirmasiPasswordBaru)
    TextInputLayout tilUbahPasswordKonfirmasiPasswordBaru;
    @BindView(R.id.tvUbahPassword)
    TextView tvUbahPassword;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ProgressDialog progressDialog;
    private String token;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ubah_password;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void init() {

        App.appComponent().inject(this);
        mChangePasswordPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("UBAH PASSWORD");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("");
        progressDialog.setCanceledOnTouchOutside(false);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
    }

    @OnClick(R.id.tvUbahPassword)
    public void onClick() {
        newPassword = tilUbahPasswordPasswordBaru.getEditText().getText().toString();
        confirmNewPassword = tilUbahPasswordKonfirmasiPasswordBaru.getEditText().getText().toString();

        if (mChangePasswordPresenter.validateNewPAssword(newPassword, confirmNewPassword)) {
            oldPassword = tilUbahPasswordPasswordLama.getEditText().getText().toString();
            mChangePasswordPresenter.changePassword(token, oldPassword, newPassword);
        } else {
            Toast.makeText(this, getResources().getString(R.string.text_new_pass_confirm_must_match), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPreChangePassword() {
        progressDialog.show();
    }

    @Override
    public void onSuccessChangePassword(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailedChangePassword(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this.getContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChangePasswordPresenter.detachView();
        mRefreshTokenPresenter.detachView();
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        mChangePasswordPresenter.changePassword(token, oldPassword, newPassword);
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenExpired() {
        mRefreshTokenPresenter.refreshToken(token);
    }
}
