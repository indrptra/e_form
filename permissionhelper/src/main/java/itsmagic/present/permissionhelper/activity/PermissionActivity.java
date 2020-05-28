//package itsmagic.present.permissionhelper.activity;
//
//import android.os.Build;
//import android.support.annotation.CallSuper;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//
//import itsmagic.present.permissionhelper.util.RuntimePermissionHelper;
//
///**
// * Created by Alvin Rusli on 9/15/2016.
// * <p/>
// * A permission helper activity to handle initial app permissions.
// * Usually only initialized once (during splash screen).
// */
//@Deprecated
//public abstract class PermissionActivity extends AppCompatActivity {
//
//    /** Additional condition to check which method to call after the initial permission check */
//    public static boolean isAllPermissionGranted = false;
//
//    /** The {@link RuntimePermissionHelper} object to handle runtime permissions */
//    private RuntimePermissionHelper permissionHelper = null;
//
//    /** The {@link OnPermissionCheckedListener} interface */
//    private OnPermissionCheckedListener permissionListener = null;
//
//    /** The request code for permission requests */
//    private final int permissionRequestCode = 4;
//
//    /** The additonal condition for permission result */
//    private boolean isInitialPermission = false;
//
//    /** Request all the specified permissions */
//    public final void requestAllPermissions(OnPermissionCheckedListener listener) {
//        permissionListener = listener;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // Android M and above has runtime permissions
//            initPermissions();
//            requestPermission();
//        } else {
//            // Lower APIs will always have the permissions
//            callOnAllPermissionGranted();
//        }
//    }
//
//    /** Initializes Android M and above runtime permissions */
//    private void initPermissions() {
//        isInitialPermission = true;
//        permissionHelper = setPermissionHelper();
//    }
//
//    /**
//     * Display the required runtime permissions.
//     * <p/>
//     * The runtime permission requests will only be shown on Android M and above.
//     */
//    private void requestPermission() {
//        if (!permissionHelper.isAllPermissionAllowed()) {
//            permissionHelper.requestAllPermission(permissionRequestCode);
//        } else {
//            callOnAllPermissionGranted();
//        }
//    }
//
//    /** Checks if app has been granted all permissions */
//    private void checkAllPermissions() {
//        if (permissionHelper.isAllPermissionAllowed()) {
//            // All permission allowed
//            callOnAllPermissionGranted();
//        } else {
//            // Some permission is still not allowed
//            callOnSomePermissionsDenied();
//        }
//    }
//
//    /** Call the listener to determine if all permission is granted */
//    private void callOnAllPermissionGranted() {
//        isAllPermissionGranted = true;
//        if (permissionListener != null) permissionListener.onAllPermissionGranted();
//    }
//
//    /** Call the listener to determine if some permission is denied */
//    private void callOnSomePermissionsDenied() {
//        isAllPermissionGranted = false;
//        if (permissionListener != null) permissionListener.onSomePermissionDenied();
//    }
//
//    /**
//     * Called when a permission is changed at runtime.
//     * @param requestCode the request code
//     * @param permissions the list of permissions
//     * @param grantResults the obtained result the permissions
//     */
//    @Override
//    @CallSuper
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == permissionRequestCode && isInitialPermission) {
//            checkAllPermissions();
//        }
//    }
//
//    /** The interface for permission checks */
//    public interface OnPermissionCheckedListener {
//        /** Called when all permissions are granted */
//        void onAllPermissionGranted();
//
//        /** Called when some permissions are denied */
//        void onSomePermissionDenied();
//    }
//
//    public abstract RuntimePermissionHelper setPermissionHelper();
//}
