package itsmagic.present.simplecamera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alvin Rusli on 9/28/2016.
 * <p/>
 * A simple helper class for Android camera usage.
 */
public class SimpleCameraUtil {

    /** Check if this device has a camera */
    public static boolean isDeviceHasCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Create an image file on the external pictures directory.
     * @return The newly created file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createImageFile(File storageDir, String filename) {
        try {
//            File storageDir = initImagePickerFolder();
            File file = new File(storageDir, filename);

            // Create the directories if needed
            if (!storageDir.exists()) storageDir.mkdirs();
            if (file.exists()) file.delete();

            // Create the temporary file
            file.createNewFile();
            return file;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Send a view to the back of the Z-axis.
     * @param child the view to be moved.
     */
    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (parent != null) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }
}
