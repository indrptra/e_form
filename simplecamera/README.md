# Simple Camera
A library made to simplify usage of Android's Camera API (v1).

## Features:
* Simplify usage to display the camera preview and capture process
* Display preview with the correct aspect ratio on any orientation and according to the selected picture size

## To-Do:
* Add simple tap for auto-focus function
* Add function to capture video

## Usage:
* Simply create a `SimpleCamera` object, and set it to the `SimpleCameraPreview` widget.
* The activity example:

        public class MyCameraActivity extends Activity {

            private SimpleCamera simpleCamera;
            private SimpleCameraPreview cameraPreview;

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                initView();
                initCamera();
            }

            private void initView() {
                cameraPreview = (SimpleCameraPreview) findViewById(R.id.my_camera_preview);
            }

            private void initCamera() {
                simpleCamera = new SimpleCamera(this);
                simpleCamera.enableLogging(); // Add this to enable debugging logs
                
                // Optional, set the gravity for the preview
                cameraPreview.setCamera(mSimpleCamera, (Gravity.TOP | Gravity.CENTER_HORIZONTAL));
            }
        }

* The layout XML example:

        <?xml version="1.0" encoding="utf-8"?>
        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            xmlns:tools="http://schemas.android.com/tools">

            <itsmagic.present.simplecamera.SimpleCameraPreview
                android:id="@+id/my_camera_preview"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

        </RelativeLayout>

* To start/stop the camera preview, simply call `#startPreview()` or `#stopPreview()` on the camera preview widget:

        cameraPreview.startPreview();
        cameraPreview.stopPreview();

## Taking a picture
* To capture a picture, simply call `#takePicture()` on the camera preview widget, while passing a `SimpleCameraPhotoCallback` object. For example:

        cameraFileCallback = new SimpleCameraFileCallback(this, new SimpleCameraFileCallback.OnCameraCaptureListener() {
            @Override
            public void onCameraCaptureSuccess(Uri capturedFile) {
                // Do something
            }

            @Override
            public void onCameraCaptureFailed(Exception e) {
                // Do something
            }
        });
        cameraFileCallback.enableLogging(); // Add this to enable debugging logs
        cameraFileCallback.setFilename("MY_FILENAME");
        cameraFileCallback.setWithTimestamp(true);

        cameraPreview.takePicture(cameraFileCallback);

* For customized picture size, you can call `#setPictureSize()` on the simple camera object. The changes on the preview has been handled automatically by this library, so you won't need to worry about not getting WYSIWYG when capturing the picture. An example:

        List<Size> sizeList = cameraPreview.getSupportedPictureSizes();
        cameraPreview.setPictureSize(sizeList.get(0)); // Make sure you use a Size from the available picture sizes of the camera, invalid values may lead to errors

## Preview customization
* You can place other Views above the preview layout, and by specifying `app:scaleWithPreview="true|false"`, the view that you place will be automatically scaled to have the same width and height with the actual preview. Example usage:

        <itsmagic.present.simplecamera.SimpleCameraPreview
            android:id="@+id/preview_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <RelativeLayout
                android:id="@+id/overlay"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:scaleWithPreview="true"> <!-- Set this to true if needed -->
                
                <!-- This image view will always be placed on the bottom-left of the preview -->
                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_alignParentBottom="true"
                    android:layout_alignParentLeft="true"
                    android:src="@drawable/my_image" />

            </RelativeLayout>

        </itsmagic.present.simplecamera.SimpleCameraPreview>