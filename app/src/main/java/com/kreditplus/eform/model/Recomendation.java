package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by nurirppan on 09-Mar-18.
 */

@DatabaseTable(tableName = "tblRecomendation")
public class Recomendation {

    @DatabaseField(columnName = "pengajuan_id",foreign = true, foreignAutoRefresh = true)
    private PengajuanBaru pengajuanBaru;

    @DatabaseField(columnName = "romendationId")
    private String romendationId;

    @DatabaseField(columnName = "recomendations")
    private String recomendation;

    public String getRomendationId() {
        return romendationId;
    }

    public void setRomendationId(String romendationId) {
        this.romendationId = romendationId;
    }

    public String getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(String recomendation) {
        this.recomendation = recomendation;
    }

    public PengajuanBaru getPengajuanBaru() {
        return pengajuanBaru;
    }

    public void setPengajuanBaru(PengajuanBaru pengajuanBaru) {
        this.pengajuanBaru = pengajuanBaru;
    }
}
