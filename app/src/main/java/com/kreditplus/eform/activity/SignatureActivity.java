package com.kreditplus.eform.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.SignatureEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class SignatureActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SignaturePad signaturePad;
    private int tipeTtd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SIGNATURE");

        Bundle bundle = getIntent().getExtras();
        tipeTtd = bundle.getInt("tipe_ttd");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_signature;
    }

    @OnClick(R.id.btn_clear)
    public void clearSignature() {
        signaturePad.clear();
    }

    @OnClick(R.id.btn_save)
    public void saveSignature() {
        EventBus.getDefault().post(new SignatureEvent(tipeTtd, signaturePad.getTransparentSignatureBitmap(true)));
        finish();
    }
}

