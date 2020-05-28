package com.kreditplus.eform.helper;

import android.content.res.Resources;
import android.util.Log;
import android.util.MalformedJsonException;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.model.response.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Iwan Nurdesa on 21/06/16.
 */
public class ErrorRetrofitUtil {

    @Inject
    Retrofit retrofit;

    public ErrorRetrofitUtil() {
        App.appComponent().inject(this);
    }

    public String parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter = retrofit.
                responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        ErrorResponse errorResponse = null;
        try {
            errorResponse = converter.convert(response.errorBody());
        } catch (IOException e) {
            if (BuildConfig.DEBUG){
                Log.e("exception errorresponse", String.valueOf(e));
                Crashlytics.log(String.valueOf(e));
            }
        }
        if (errorResponse == null || response.code() == 500) {
            return "Terjadi kesalahan pada server";
        }
        return errorResponse.getMeta().getMessage();
    }

    public String responseFailedError(Throwable e) {
        if (e != null) {
            if (BuildConfig.DEBUG) {
                Log.e("Response Failed", String.valueOf(e));
            }
            if (e instanceof UnknownHostException) {
                return "Tidak Ada Koneksi Internet";
            }else if (e instanceof SocketTimeoutException){
                return "Server sedang sibuk, Mohon coba beberapa saat lagi";
            }else if (e instanceof NumberFormatException){
                Crashlytics.log(2,"Number format Exception", e.getMessage());
                return "Terjadi kesalahan pada server";
            }else if (e instanceof InvalidParameterException){
                Crashlytics.log(2,"Invalid Parameter Exception", e.getMessage());
                return "Terjadi kesalahan pada server";
            }
        }
        return "Terjadi kesalahan koneksi";
    }
}
