package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblFirebase")
public class TblFirebase {
   @DatabaseField(columnName = "id", generatedId = true)
   private int id;

   @DatabaseField(columnName = "TokenFCM")
   private String TokenFCM;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTokenFCM() {
      return TokenFCM;
   }

   public void setTokenFCM(String tokenFCM) {
      TokenFCM = tokenFCM;
   }
}
