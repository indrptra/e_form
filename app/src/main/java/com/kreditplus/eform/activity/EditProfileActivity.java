package com.kreditplus.eform.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.ProfilFragment;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.bus.SetDataProfile;
import com.kreditplus.eform.model.bus.SetDataProfileNav;
import com.kreditplus.eform.model.bus.SetEditDataProfile;
import com.kreditplus.eform.presenter.ProfilPresenter;
import com.kreditplus.eform.presenter.mvpview.ProfilMvpView;
import com.kreditplus.eform.service.DatabaseService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends BaseActivity implements Validator.ValidationListener, ProfilMvpView {

    @BindView(R.id.img_user_photo_edit)
    CircleImageView imgUserPhotoEdit;

    @NotEmpty
    @BindView(R.id.edt_first_name)
    EditText edtFirstname;
    @NotEmpty
    @BindView(R.id.edt_last_name)
    EditText edtLastName;
    @NotEmpty
    @BindView(R.id.edt_alamat_profile)
    EditText edtAlamatProfile;
    @BindView(R.id.edt_email_profile)
    EditText edtEmailProfile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor editor;

    private ProgressDialog progressDialog;
    private Validator validator;
    private int id;
    private String token;
    private String firstName;
    private String lastName;
    private String alamat;
    private String email;
    private String pathString;
    private File path1;
    private File path2;
    private File file;


    private List<ImgPhotoProfile> imgPhotoProfileList;
    private DatabaseService databaseService;
    private HashMap<Integer, File> mHashMapAttachmentFiles = new HashMap<>();
    private List<File> mHashMapFiles;

    private ProfilPresenter mProfilPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        databaseService = new DatabaseService(this);

        mProfilPresenter = new ProfilPresenter();
        mProfilPresenter.attachView(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("EDIT PROFILE");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setTitle("");
        progressDialog.setCanceledOnTouchOutside(false);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");

        firstinit();
    }

    @Override
    protected void onDestroy() {
        mProfilPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                CekIfNotSave();
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }


    @OnClick(R.id.btn_save_profile)
    public void ValidateProfile() {
        validator.validate();
    }

    public void firstinit() {

//        // init string dari sheredpreferance
        firstName = sharedPreferences.getString(getResources().getString(R.string.sharedpref_firstname), "");
        lastName = sharedPreferences.getString(getResources().getString(R.string.sharedpref_lastname), "");
        alamat = sharedPreferences.getString(getResources().getString(R.string.sharedpref_alamat), "");
        email = sharedPreferences.getString(getContext().getResources().getString(R.string.sharedpref_email), "");

        //set text
        edtFirstname.setText(firstName);
        edtLastName.setText(lastName);
        edtAlamatProfile.setText(alamat);
        edtEmailProfile.setText(email);

        //set photo profile
        try {
            imgPhotoProfileList = databaseService.getImgPhotoProfileDao().queryForAll();
            for (int i = 0; i < imgPhotoProfileList.size(); i++) {
                Glide.with(getContext()).load(imgPhotoProfileList.get(i).getPath1()).centerCrop().into(imgUserPhotoEdit);
                path1 = FileUtils.getFile(imgPhotoProfileList.get(i).getPath1());
                path2 = FileUtils.getFile(imgPhotoProfileList.get(i).getPath2());
                pathString = imgPhotoProfileList.get(i).getPath1();
                break;
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG)
                Log.e("Query photo profile", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    public void SaveData() {
        editor.putString(getContext().getResources().getString(R.string.sharedpref_firstname), edtFirstname.getText().toString());
        editor.putString(getContext().getResources().getString(R.string.sharedpref_lastname), edtLastName.getText().toString());
        editor.putString(getContext().getResources().getString(R.string.sharedpref_alamat), edtAlamatProfile.getText().toString());
        editor.putString(getContext().getResources().getString(R.string.sharedpref_email), edtEmailProfile.getText().toString());
        editor.commit();

        //delete photo ke 2
        try {
            List<ImgPhotoProfile> list = databaseService.getImgPhotoProfileDao().queryForAll();
            if (list.size() > 1) {
                for (int i = 0; i < list.size(); i++) {
                    int a = imgPhotoProfileList.get(i).getId();
                    DeleteBuilder<ImgPhotoProfile, String> delete = databaseService.getImgPhotoProfileDao().deleteBuilder();
                    delete.where().eq("id", a);
                    delete.delete();
                    break;
                }
            }

        } catch (SQLException e) {
            if (BuildConfig.DEBUG)
                Log.e("Delete photo profile", String.valueOf(e));
            Crashlytics.logException(e);
        }


        //set photo profile, first name, last name, email pada navigation drawer
        EventBus.getDefault().post(new SetDataProfileNav());
        EventBus.getDefault().post(new SetEditDataProfile());

        progressDialog.dismiss();
        finish();
    }

    public void SendData() {
        mHashMapFiles = new ArrayList<>();

        //set data string
        String fsname = edtFirstname.getText().toString();
        String lsname = edtLastName.getText().toString();
        String add = edtAlamatProfile.getText().toString();
        String mail = edtEmailProfile.getText().toString();

        //set image path 1 dan 2 di dapat saat awal activity

        mProfilPresenter.updateProfile(token, fsname, lsname, add, mail, path1, path2);
    }

    public void SetPhotoProfile() {
        try {
            imgPhotoProfileList = databaseService.getImgPhotoProfileDao().queryForAll();
            for (int i = 0; i < imgPhotoProfileList.size(); i++) {
                Glide.with(getContext()).load(imgPhotoProfileList.get(i).getPath1()).centerCrop().into(imgUserPhotoEdit);
                path1 = FileUtils.getFile(imgPhotoProfileList.get(i).getPath1());
                path2 = FileUtils.getFile(imgPhotoProfileList.get(i).getPath2());
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG)
                Log.e("Query photo profile", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    private void CekIfNotSave() {

        try {
            List<ImgPhotoProfile> list = databaseService.getImgPhotoProfileDao().queryForAll();
            if (list.size() > 1) {
                for (int i = 0; i < list.size(); i++) {
                    if (i + 1 == list.size()) {
                        int a = list.get(i).getId();
                        DeleteBuilder<ImgPhotoProfile, String> delete = databaseService.getImgPhotoProfileDao().deleteBuilder();
                        delete.where().eq("id", a);
                        delete.delete();
                    }
                }
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG)
                Log.e("Delete photo profile", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    @OnClick(R.id.txt_edit_password)
    public void onClick() {
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.img_user_photo_edit)
    public void imgClick() {
        Intent intent = new Intent(getContext(), AttachmentZoomPagerActivity.class);
        intent.putExtra("File", pathString);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        SendData();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onPreLoadProfil() {
        progressDialog.show();
    }

    @Override
    public void onSuccessLoadProfil(String message) {
        SaveData();
    }

    @Override
    public void onFailedLoadProfil(String massage) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenProfilExpired() {

    }

    @Subscribe
    public void setProfile(SetDataProfile e) {
        SetPhotoProfile();
    }
}
