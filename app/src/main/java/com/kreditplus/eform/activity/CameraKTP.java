package com.kreditplus.eform.activity;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.view.CameraPreview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraKTP extends BaseActivity {
    private static final String TAG = "CAMERA_KTP" ;
    @BindView(R.id.preview)
    FrameLayout mPreview;

    @BindView(R.id.takePicture)
    ImageView takePicture;

    SurfaceHolder mHolder;
    Camera mCamera;
    CameraPreview cameraPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        mCamera = Camera.open();
        cameraPreview = new CameraPreview(this,mCamera);
        mPreview.addView(cameraPreview);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(autoFocusCallback);
            }
        });

    }
    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            //			 Log.d(TAG, "onShutter'd");
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            //			 Log.d(TAG, "onPictureTaken - raw");
        }
    };
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            File pictureFile = new File(root+"/EFORM_IMAGES");
            if (!pictureFile.exists()){
                pictureFile.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File tmpFile = new File(pictureFile, fileName);
            if (tmpFile.exists()) tmpFile.delete();

            Intent parsing = new Intent();
            Bitmap imges = ByteArrayToBitmap(data);
            try {
                FileOutputStream fos = new FileOutputStream(tmpFile);
                imges.compress(Bitmap.CompressFormat.JPEG, 100,fos);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onPictureTaken - jpeg");
            Uri imageResult = Uri.fromFile(tmpFile);
            parsing.setData(imageResult);
            setResult(RESULT_OK,parsing);
            finish();
        }
    };
    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
                camera.takePicture(null,null,jpegCallback);

        }
    };
    public Bitmap ByteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream,null,options);
        return bitmap;
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_camera_ktp;
    }


}
