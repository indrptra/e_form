package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Counterpart;
import com.kreditplus.eform.model.response.objecthelper.Industri;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class CounterpartResponse {

    @SerializedName("counterparts")
    private List<Counterpart> counterparts;

    public List<Counterpart> getCounterparts() {
        return counterparts;
    }

    public void setCounterparts(List<Counterpart> counterparts) {
        this.counterparts = counterparts;
    }
}
