package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Propinsi;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 20/06/16.
 */
public class PropinsiResponse {

    @SerializedName("Province")
    private List<Propinsi> propinsiList;

    public List<Propinsi> getPropinsiList() {
        return propinsiList;
    }

    public void setPropinsiList(List<Propinsi> propinsiList) {
        this.propinsiList = propinsiList;
    }
}
