package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 08/06/17.
 */

@DatabaseTable(tableName = "SyncDumpDate")
public class SyncDateDump {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "DateSyncDump")
    private String dateSyncDump;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateSyncDump() {
        return dateSyncDump;
    }

    public void setDateSyncDump(String dateSyncDump) {
        this.dateSyncDump = dateSyncDump;
    }
}
