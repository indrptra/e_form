package com.kreditplus.eform.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.activity.ChangePasswordActivity;
import com.kreditplus.eform.activity.EditProfileActivity;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.bus.SetDataProfile;
import com.kreditplus.eform.model.bus.SetEditDataProfile;
import com.kreditplus.eform.presenter.ProfilPresenter;
import com.kreditplus.eform.presenter.mvpview.ProfilMvpView;
import com.kreditplus.eform.service.DatabaseService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Iwan Nurdesa on 26/05/16.
 */
public class ProfilFragment extends BaseFragment implements ProfilMvpView {

    @Inject
    ProfilPresenter profilPresenter;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    DatabaseService databaseService;

    @BindView(R.id.img_user_photo)
    CircleImageView imgUserPhoto;
    @BindView(R.id.til_profile_name)
    TextInputLayout tilProfileName;
    @BindView(R.id.til_profile_email)
    TextInputLayout tilProfileEmail;
    @BindView(R.id.til_profile_alamat)
    TextInputLayout tilProfileAlamat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(false);
        init(view);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("PROFIL");
        ButterKnife.bind(this, view);

        setProfile();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profilPresenter.detachView();
    }

    /**
     * Initialization
     *
     * @param view
     */
    private void init(View view) {
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);
        profilPresenter.attachView(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPreLoadProfil() {

    }

    @Override
    public void onSuccessLoadProfil(String message) {

    }

    @Override
    public void onFailedLoadProfil(String massage) {

    }

    @Override
    public void onTokenProfilExpired() {

    }

    private void setProfile() {
        String urlPhoto = sharedPreferences.getString(getResources().getString(R.string.sharedpref_url_photo), "R.drawable.ic_default_photo");
        String name = sharedPreferences.getString(getResources().getString(R.string.sharedpref_firstname), "-") + " " +
                sharedPreferences.getString(getResources().getString(R.string.sharedpref_lastname), "");
        String email = sharedPreferences.getString(getResources().getString(R.string.sharedpref_email), "-");
        String alamat = sharedPreferences.getString(getResources().getString(R.string.sharedpref_alamat),"");

        tilProfileName.getEditText().setText(name);
        tilProfileEmail.getEditText().setText(email);
        tilProfileAlamat.getEditText().setText(alamat);
        Glide.with(this).load(urlPhoto).centerCrop().
                placeholder(R.drawable.ic_default_photo).error(R.drawable.ic_default_photo).into(imgUserPhoto);
    }

    @OnClick(R.id.txt_edit_profile)
    public void onClick() {
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void SetProfileBus(SetEditDataProfile e){
        setProfile();
    }
}
