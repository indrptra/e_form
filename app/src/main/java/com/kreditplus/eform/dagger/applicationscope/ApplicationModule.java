package com.kreditplus.eform.dagger.applicationscope;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.SigninActivity;
import com.kreditplus.eform.helper.JniUtil;
import com.kreditplus.eform.presenter.AttachmentPresenter;
import com.kreditplus.eform.presenter.BidPresenter;
import com.kreditplus.eform.presenter.BlackListPresenter;
import com.kreditplus.eform.presenter.ChangePasswordPresenter;
import com.kreditplus.eform.presenter.CodeSignaturePresenter;
import com.kreditplus.eform.presenter.NotifikasiListPresenter;
import com.kreditplus.eform.presenter.PengajuanAddPresenter;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.PengajuanDraftPresenter;
import com.kreditplus.eform.presenter.PengajuanListPresenter;
import com.kreditplus.eform.presenter.PengajuanUnsyncPresenter;
import com.kreditplus.eform.presenter.ProfilPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.SendSaveDraftPresenter;
import com.kreditplus.eform.presenter.SigninPresenter;
import com.kreditplus.eform.presenter.SignoutPresenter;
import com.kreditplus.eform.presenter.SinkronisasiPresenter;
import com.kreditplus.eform.presenter.SyaratDanKetentuanPresenter;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Iwan Nurdesa on 23/06/16.
 */
@Module
public class ApplicationModule {

    private final App mApp;
    private JniUtil jniUtil = new JniUtil();

    public ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return mApp;
    }

    @Provides
    @ApplicationScope
    HttpUrl provideHttpUrl(Application app) {
        return HttpUrl.parse(jniUtil.apiUrl());
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(HttpUrl httpUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(new ChuckInterceptor(mApp))
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        return client;
    }

    @Provides
    @ApplicationScope
    Gson provideGson() {
        Gson gson = new GsonBuilder()
                .create();
        return gson;
    }

    @Provides
    @ApplicationScope
    ErrorRetrofitUtil provideErrorUtil() {
        return new ErrorRetrofitUtil();
    }

    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreference(Application app) {
        return app.getSharedPreferences(app.getResources().getString(R.string.sharedprefname), Context.MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    SharedPreferences.Editor provideSharedPreferenceEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    @Provides
    @ApplicationScope
    DatabaseService provideDatabaseService(Application app) {
        return new DatabaseService(app);
    }

    @Provides
    @ApplicationScope
    SigninActivity provideSigninActivity() {
        return new SigninActivity();
    }

    @Provides
    @ApplicationScope
    SigninPresenter provideSigninPresenter() {
        return new SigninPresenter();
    }

    @Provides
    @ApplicationScope
    ProfilPresenter provideProfilPresenter() {
        return new ProfilPresenter();
    }

    @Provides
    @ApplicationScope
    PengajuanAddPresenter providePengajuanBaruPresenter() {
        return new PengajuanAddPresenter();
    }

    @Provides
    @ApplicationScope
    PengajuanDetailPresenter providePengajuanEditLoadPresenter() {
        return new PengajuanDetailPresenter();
    }

    @Provides
    @ApplicationScope
    PengajuanListPresenter providePengajuanListPresenter() {
        return new PengajuanListPresenter();
    }

    @Provides
    @ApplicationScope
    SignoutPresenter provideSignoutPresenter() {
        return new SignoutPresenter();
    }

    @Provides
    @ApplicationScope
    NotifikasiListPresenter provideNotifikasiListPresenter() {
        return new NotifikasiListPresenter();
    }

    @Provides
    @ApplicationScope
    BidPresenter provideBidPresenter() {
        return new BidPresenter();
    }


    @Provides
    @ApplicationScope
    PengajuanUnsyncPresenter providePengajuanUnsyncPresenter() {
        return new PengajuanUnsyncPresenter();
    }

    @Provides
    @ApplicationScope
    AttachmentPresenter provideAttachmentPresenter() {
        return new AttachmentPresenter();
    }


    @Provides
    @ApplicationScope
    ChangePasswordPresenter provideChangePasswordPresenter() {
        return new ChangePasswordPresenter();
    }

    @Provides
    @ApplicationScope
    RefreshTokenPresenter provideRefreshTokenPresenter() {
        return new RefreshTokenPresenter();
    }

    @Provides
    @ApplicationScope
    BlackListPresenter provideBlackListPresenter() {
        return new BlackListPresenter();
    }

    @Provides
    @ApplicationScope
    CodeSignaturePresenter provideCodeSignaturePresenter() {
        return new CodeSignaturePresenter();
    }

    @Provides
    @ApplicationScope
    PengajuanDraftPresenter providePengajuanDraftPresenter() {
        return new PengajuanDraftPresenter();
    }

    @Provides
    @ApplicationScope
    SendSaveDraftPresenter provideSendSaveDraftPresenter() {
        return new SendSaveDraftPresenter();
    }

    @Provides
    @ApplicationScope
    SyaratDanKetentuanPresenter syaratDanKetentuanPresenter() {
        return new SyaratDanKetentuanPresenter();
    }

    @Provides
    @ApplicationScope
    SinkronisasiPresenter sinkronisasiPresenter() {
        return new SinkronisasiPresenter();
    }
}
