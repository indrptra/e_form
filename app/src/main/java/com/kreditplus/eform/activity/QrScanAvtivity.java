package com.kreditplus.eform.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.QrScanner;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScanAvtivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    @BindView(R.id.fl_qr_scanner)
    FrameLayout flQrScanner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ZXingScannerView mScannerView;
    private Bundle bundle;
    private String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        bundle = getIntent().getExtras();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QR Scanner");

        mScannerView = new ZXingScannerView(this);
        flQrScanner.addView(mScannerView);

        appId = bundle.getString("appId");

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_qr_scan_avtivity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        if (BuildConfig.DEBUG)
            Log.w("Result QR", result.getText());
        if (appId != null) {
            EventBus.getDefault().post(new QrScanner(result.getText(), appId));
        }else{
            Toast.makeText(this, getResources().getString(R.string.warning_id_qr), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }


}
