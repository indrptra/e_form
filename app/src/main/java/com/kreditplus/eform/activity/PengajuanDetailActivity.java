package com.kreditplus.eform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.PengajuanDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsmagic.present.permissionhelper.util.PermissionHelper;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengajuanDetailActivity extends BaseActivity {

   @BindView(R.id.toolbar)
   Toolbar toolbar;

   private PermissionHelper mPermissionHelper;
   private String strAppId;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      unbinder = ButterKnife.bind(this);

      Bundle bundle = new Bundle();
      bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "pengajuan_detail");
      mFirebaseAnalytics.logEvent("pengajuan_detail_open", bundle);

      setSupportActionBar(toolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      String strAppId = getIntent().getStringExtra("app_id");

      Fragment fragment = new PengajuanDetailFragment();

      Bundle bundleCons = new Bundle();
      bundleCons.putString("app_id", strAppId);
      fragment.setArguments(bundleCons);

      getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, "PengajuanDetailFragment").commit();
   }

   @Override
   protected int getContentView() {
      return R.layout.activity_pengajuan_detail;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         // Respond to the action bar's Up/Home button
         case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);

      if (mPermissionHelper != null)
         mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
   }
}
