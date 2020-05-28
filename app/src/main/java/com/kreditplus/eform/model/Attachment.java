package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;

/**
 * Created by Iwan Nurdesa on 10/08/16.
 */
@DatabaseTable(tableName = "attachment")
public class Attachment {
    @DatabaseField(columnName = "id", generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;
    @DatabaseField(columnName = "nama_attachment")
    private String namaAttachment;
    @DatabaseField(columnName = "path")
    private String path;
    @DatabaseField(columnName = "path2")
    private String path2;
    @DatabaseField(columnName = "key")
    private int key;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MasterFormPengajuan getMasterFormPengajuan() {
        return masterFormPengajuan;
    }

    public void setMasterFormPengajuan(MasterFormPengajuan masterFormPengajuan) {
        this.masterFormPengajuan = masterFormPengajuan;
    }

    public String getNamaAttachment() {
        return namaAttachment;
    }

    public void setNamaAttachment(String namaAttachment) {
        this.namaAttachment = namaAttachment;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
