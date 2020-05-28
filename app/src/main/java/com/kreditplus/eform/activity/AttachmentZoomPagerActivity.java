package com.kreditplus.eform.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.PengajuanDetailFragment;
import com.kreditplus.eform.helper.HackyViewPager;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.bus.SetDataProfile;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Iwan Nurdesa on 22/11/16.
 */

public class AttachmentZoomPagerActivity extends BaseActivity {

    private Bundle bundle;
    private List<String> attachmentList = new ArrayList<>();
    private int currentPosition;
    private String imageUrl;
    private File imageFile;
    private DownloadAsync downloadAsync;
    private ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        attachmentList = bundle.getStringArrayList("attachments");
        currentPosition = bundle.getInt("position");
        mViewPager.setAdapter(new SamplePagerAdapter(this, attachmentList));
        mViewPager.setCurrentItem(currentPosition);
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_attachment_zoom_pager;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_pengajuan, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_download:
                imageUrl = attachmentList.get(mViewPager.getCurrentItem());
                if (imageUrl != null && !"".equalsIgnoreCase(imageUrl)) {
                    downloadAsync = new DownloadAsync();
                    downloadAsync.execute();
                } else {
                    Toast.makeText(this, "Tidak ada image", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
    public class DownloadAsync extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        public DownloadAsync() {
            progressDialog = new ProgressDialog(AttachmentZoomPagerActivity.this);
            progressDialog.setMessage("Downloading...");
            progressDialog.setTitle("");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri fileUri = FileProvider.getUriForFile(AttachmentZoomPagerActivity.this, getPackageName() + ".eformprovider", imageFile);
            intent.setDataAndType(fileUri, "image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        @Override
        protected Void doInBackground(Void... params) {
            File createFile = new File(Environment.getExternalStorageDirectory(), "EFORM");
            if (!createFile.exists())
                createFile.mkdirs();
            imageFile = new File(createFile.getPath(), "eform_image" + String.valueOf(System.currentTimeMillis() + ".jpg"));
            try {
                FileUtils.copyURLToFile(new URL(imageUrl), imageFile);
            } catch (IOException e) {
                if (BuildConfig.DEBUG)Log.e("Asset Pengajuan", String.valueOf(e));
                Toast.makeText(AttachmentZoomPagerActivity.this, R.string.warning_copt_url_to_file, Toast.LENGTH_SHORT).show();
            }
            MediaScannerConnection.scanFile(AttachmentZoomPagerActivity.this, new String[]{imageFile.getPath()}, new String[]{"image/jpeg"}, null);
            return null;
        }
    }
    static class SamplePagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<String> mAttachmentList;
        public SamplePagerAdapter(Context context, List<String> attachmentList) {
            mContext = context;
            mAttachmentList = attachmentList;
        }
        @Override
        public int getCount() {
            return mAttachmentList.size();
        }
        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(mContext).load(mAttachmentList.get(position)).into(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            return photoView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
