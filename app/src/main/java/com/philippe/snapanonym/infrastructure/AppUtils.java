package com.philippe.snapanonym.infrastructure;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

public class AppUtils {

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    public static void requestPermissions(Activity activity, String[] permissions, int permissionRequestCode) {
        ActivityCompat.requestPermissions(activity,
                permissions,
                permissionRequestCode);

    }
}
