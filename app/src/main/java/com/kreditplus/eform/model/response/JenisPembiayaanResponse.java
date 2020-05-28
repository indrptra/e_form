package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.JenisPembiayaanFilter;

import java.util.List;

public class JenisPembiayaanResponse {
    @SerializedName("JenisPembiayaanFilter")
    private List<JenisPembiayaanFilter> jenisPembiayaanFilters;

    public List<JenisPembiayaanFilter> getJenisPembiayaanFilters() {
        return jenisPembiayaanFilters;
    }

    public void setJenisPembiayaanFilters(List<JenisPembiayaanFilter> jenisPembiayaanFilters) {
        this.jenisPembiayaanFilters = jenisPembiayaanFilters;
    }
}
