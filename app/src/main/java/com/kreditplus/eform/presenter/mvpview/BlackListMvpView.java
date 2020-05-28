package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.BlackListResponse;
import com.kreditplus.eform.model.response.objecthelper.Application;
import com.kreditplus.eform.model.response.objecthelper.ApplicationBlacklist;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface BlackListMvpView extends BaseMvpView {
    void onPreBlackList();
    void onSuccessBlackList(BlackListResponse blackListResponse,
                            List<String> fullNames,
                            List<ApplicationBlacklist> applicationBlacklist);
    void onFailedBlackList(String message);
    void onTokenBlackListExpired();
}
