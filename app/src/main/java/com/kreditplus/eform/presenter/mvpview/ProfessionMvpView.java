package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.backup.Profession;

import java.util.List;

/**
 * Created by apc-lap012 on 19/02/17.
 */

public interface ProfessionMvpView extends BaseMvpView {
    void onPreLoadProfession();
    void onSuccessLoadProfession(List<Profession> professionList);
    void onFailedLoadProfession(String message);
    void onTokenPengajuanExpired();
}
