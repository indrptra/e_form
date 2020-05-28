package com.kreditplus.eform.activity;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.CropPhotoEvent;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropPhotoActivity extends BaseActivity {

    @BindView(R.id.iv_crop_image_view)
    CropImageView ivCropImageView;
    @BindView(R.id.btn_crop_image)
    Button btnCropImage;

    private Bundle extras;
    private Uri myUri;
    private int typePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        unbinder = ButterKnife.bind(this);

        typePhoto = getIntent().getExtras().getInt("requestCode");
        myUri = getIntent().getData();

        ivCropImageView.setImageUriAsync(myUri);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_crop_image;
    }

    @OnClick(R.id.btn_crop_image)
    public void onViewClicked() {
        Bitmap cropped = ivCropImageView.getCroppedImage(2000, 3500);
        EventBus.getDefault().post(new CropPhotoEvent(typePhoto, cropped));
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (myUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ivCropImageView.setImageUriAsync(myUri);
        } else {
            Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }
}
