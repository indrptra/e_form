package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 30/03/17.
 */

@DatabaseTable(tableName = "photoProfile")
public class ImgPhotoProfile {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "nama_photo")
    private String namaPhoto;

    @DatabaseField(columnName = "path1")
    private String path1;

    @DatabaseField(columnName = "path2")
    private String path2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPhoto() {
        return namaPhoto;
    }

    public void setNamaPhoto(String namaPhoto) {
        this.namaPhoto = namaPhoto;
    }

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }
}
