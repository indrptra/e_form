package com.kreditplus.eform.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.SwitchCamera;
import com.kreditplus.eform.model.bus.SwitchCameraOther;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import itsmagic.present.simplecamera.SimpleCamera;
import itsmagic.present.simplecamera.SimpleCameraPreview;
import itsmagic.present.simplecamera.callback.SimpleCameraByteCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SuppressLint("All")
public class CameraOtherActivity extends BaseActivity {

    public final static String TAG = "CameraActivity";
    public final static String FILE = "file";
    public final static String COMPRESSED_FILE = "compressed_file";
    public final static String FRAME = "frame";
    public final static String IS_OCR = "is_ocr";
    public final static int FRAME_KTP = 1;
    public final static int FRAME_KTP_FACE = 2;
    public final static int FRAME_OTHER = 3;
    private boolean isFront = true;
    private boolean isOther = false;
    public final static String ISFRONT = "isfront";
    public final static String USESWITCH = "useswitch";
    public final static String URIFILE = "urifile";
    public Uri uriFile;

    @BindView(R.id.camera_preview)
    SimpleCameraPreview cameraPreview;
    @BindView(R.id.iv_frame_ktp)
    ImageView ivFrameKTP;
    @BindView(R.id.iv_frame_ktp_face)
    ImageView ivFrameKTPFace;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;
    @BindView(R.id.layout_info)
    LinearLayout layoutInfo;

    /**
     * The camera object for camera preview
     */
    private SimpleCamera simpleCamera;

    /**
     * The camera callback
     */
    private SimpleCameraByteCallback simpleCameraCallback;

    /**
     * Determines if an image from camera has been taken
     */
    private boolean isCameraImageTaken = false;

    /**
     * Determines if the camera is currently taking a picture
     */
    private boolean isTakingCameraImage = false;

    /**
     * Determines if camera has been initialized
     */
    private boolean isCameraInitialized = false;

    private Bundle bundle;
    private AsyncTask<Void, Void, File> asyncTaskTakePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();
        if (bundle == null) bundle = new Bundle();
        isOther = true;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera_selfie);
        ButterKnife.bind(this);
        if (bundle.getBoolean(USESWITCH, true)) {
            ivSwitch.setVisibility(View.VISIBLE);
        } else {
            ivSwitch.setVisibility(View.GONE);
        }


        if (bundle.getBoolean(IS_OCR, false)) {
            layoutInfo.setVisibility(View.VISIBLE);
        } else {
            layoutInfo.setVisibility(View.GONE);
        }

        if (bundle.getBoolean(ISFRONT, true)) {
            isFront = true;
        } else {
            isFront = false;
        }

        initComponents();
    }

    @Override
    protected int getContentView() {
        bundle = getIntent().getExtras();
        if (bundle == null) bundle = new Bundle();

        if (bundle.getInt(FRAME) == FRAME_KTP) {
            return R.layout.activity_camera_ktp_frame;
        } else {
            return R.layout.activity_camera_selfie;
        }
    }

    private void initComponents() {

    }

    @OnClick(R.id.fab_take_photo)
    public void onClickTakePhoto() {
        if (isTakingCameraImage) {
            return;
        } else if (!isCameraImageTaken) {
            isTakingCameraImage = true;
//            if (cameraPreview.isFrontFacingCameraSelected()) {
//                simpleCameraCallback.enableMirroring();
//            } else simpleCameraCallback.disableMirroring();
            cameraPreview.takePicture(simpleCameraCallback);
//            showProgress(getString(R.string.loading));
        }
    }

    @OnClick(R.id.iv_switch)
    public void onClickSwitch() {
        if (cameraPreview != null) {
//            cameraPreview.switchCamera();
            if (isFront) {
                isFront = false;
            } else {
                isFront = true;
            }
            EventBus.getDefault().post(new SwitchCameraOther(isFront));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isCameraImageTaken = false;
                startPreviewCamera();
            }
        }, 500);
    }

    @Override
    protected void onPause() {
        if (cameraPreview != null) {
            cameraPreview.removeCamera();
            isCameraInitialized = false;
        }
        super.onPause();

    }

    void startPreviewCamera() {
        try {
            initCamera();
            cameraPreview.startPreview();
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.putExtra(TAG, bundle.getString(TAG));
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

        isCameraImageTaken = false;
        isTakingCameraImage = false;
    }

    /**
     * Initialize the camera view
     */
    private void initCamera() {
        if (isCameraInitialized) return;
        if (isFront) {
            simpleCamera = new SimpleCamera(this, SimpleCamera.CameraFace.FRONT);
        } else {
            simpleCamera = new SimpleCamera(this, SimpleCamera.CameraFace.BACK);
        }
//        simpleCamera = new SimpleCamera(this, SimpleCamera.CameraFace.BACK);

        simpleCameraCallback = new SimpleCameraByteCallback(this, new SimpleCameraByteCallback.OnCameraCaptureListener() {
            @Override
            public void onCameraByteCaptureSuccess(final byte[] capturedBytes) {
                if (isTakingCameraImage) {
                    cameraPreview.stopPreview();
                    if (asyncTaskTakePhoto != null && asyncTaskTakePhoto.getStatus() == AsyncTask.Status.RUNNING) {
                        asyncTaskTakePhoto.cancel(true);
                    }
                    asyncTaskTakePhoto = new AsyncTask<Void, Void, File>() {
                        @Override
                        protected File doInBackground(Void... voids) {
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            File storageDir = new File(getExternalCacheDir(), "KreditPlus");
                            if (!storageDir.exists()) {
                                storageDir.mkdirs();
                            }
                            File file = new File(
                                    storageDir.getPath(),
                                    "IMG_" + timeStamp + ".jpg"
                            );
                            FileOutputStream outputStream = null;
                            try {
                                outputStream = new FileOutputStream(file);
                                outputStream.write(capturedBytes);
                                outputStream.close();
                            } catch (Exception e) {
                            } finally {
                                if (outputStream != null)
                                    try {
                                        outputStream.close();
                                    } catch (Exception e) {
                                    }
                            }
                            return file;
                        }

                        @Override
                        protected void onPostExecute(File file) {
                            super.onPostExecute(file);
                            File compressedFile = null;
                            try {
                                compressedFile = new Compressor(CameraOtherActivity.this)
                                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                        .setDestinationDirectoryPath(getExternalCacheDir() + "/KreditPlus/Compressed")
                                        .setQuality(75)
                                        .setMaxHeight(1080)
                                        .setMaxWidth(1080)
                                        .compressToFile(file);
                            } catch (Exception e) {
                            }
//                            dismissProgress();

                            uriFile = Uri.fromFile(compressedFile);
                            Intent intent = new Intent();
                            intent.putExtra(TAG, bundle.getString(TAG));
                            intent.putExtra(URIFILE, uriFile);
//                            intent.putExtra(FILE, file);
//                            intent.putExtra(COMPRESSED_FILE, compressedFile);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    };
                    asyncTaskTakePhoto.execute();
                }
                isCameraImageTaken = true;
                isTakingCameraImage = false;
            }
        });

        cameraPreview.setCamera(simpleCamera);
        cameraPreview.setCameraCropped(true);
        cameraPreview.setCaptureOriginalImage(true);

        List<Camera.Size> sizes = cameraPreview.getSupportedPictureSizes();
        if (sizes != null) {
            cameraPreview.setPictureSize(sizes.get(Util.settingCamera(sizes)));
        }

        isCameraInitialized = true;
    }

    public void scanFile(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        intent.setData(contentUri);
        sendBroadcast(intent);
    }


}