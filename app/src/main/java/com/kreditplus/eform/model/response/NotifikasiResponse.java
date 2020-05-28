package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.Notifikasi;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 19/07/16.
 */
public class NotifikasiResponse {

    @SerializedName("notifications")
    private List<Notifikasi> notifikasiList;

    public List<Notifikasi> getNotifikasiList() {
        return notifikasiList;
    }

    public void setNotifikasiList(List<Notifikasi> notifikasiList) {
        this.notifikasiList = notifikasiList;
    }
}
