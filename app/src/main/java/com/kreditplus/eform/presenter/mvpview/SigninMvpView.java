package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.LoginResponse;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface SigninMvpView extends BaseMvpView {
    void onPreSignin();
    void onSuccessSignin(LoginResponse loginResponse);
    void onFailedSignin(String message);
}
