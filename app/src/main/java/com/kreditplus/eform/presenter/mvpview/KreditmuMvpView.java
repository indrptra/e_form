package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.KreditmuResponse;
import com.kreditplus.eform.model.response.objecthelper.KreditmuCity;
import com.kreditplus.eform.model.response.objecthelper.KreditmuEdu;
import com.kreditplus.eform.model.response.objecthelper.KreditmuHome;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobPosition;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobType;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKecamatan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKelurahan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuMarital;
import com.kreditplus.eform.model.response.objecthelper.KreditmuProfession;
import com.kreditplus.eform.model.response.objecthelper.KreditmuZipCode;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public interface KreditmuMvpView extends BaseMvpView {
    void onPreLoadKreditmu();
    void onSuccessLoadKreditmu(KreditmuResponse kreditmuResponse);
    void onFailedLoadKreditmu(String message);
    void onTokenKreditmuExpired();
}
