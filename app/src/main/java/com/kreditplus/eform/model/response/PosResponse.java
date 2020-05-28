package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Counterpart;
import com.kreditplus.eform.model.response.objecthelper.POS;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class PosResponse {

    @SerializedName("POSMaster")
    private List<POS> branchPos;

    public List<POS> getBranchPos() {
        return branchPos;
    }

    public void setBranchPos(List<POS> branchPos) {
        this.branchPos = branchPos;
    }
}
