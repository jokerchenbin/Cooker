/*
 * function common method 
 */
package com.mastercooker.tools;

import android.app.Activity;
import android.content.Context;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.mastercooker.AppApplication;
import com.mastercooker.R;
import com.mastercooker.view.AppLoadingDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class FunctionUtils {

    private static AppLoadingDialog loadingDialog;



    /*
     * get mobile udid
     */
    public static String getUdId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * get app version name and version code
     */
    public static String getAppVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            // if (versionName == null || versionName.length() <= 0) {
            // return "";
            // }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        // AppConfig.versionCode = versionCode;
        return String.valueOf(versionCode);
    }

    public static String getapiVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            // if (versionName == null || versionName.length() <= 0) {
            // return "";
            // }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        // AppConfig.versionCode = versionCode;
        return versionName;
    }

    /**
     * get mobile model
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * get mobile system version
     */
    public static String getOs() {
        return "android" + Build.VERSION.RELEASE;
    }



    /**
     * exit the app and finish all the activity
     *
     * @param context
     */
    public static void exitApp(Context context) {
        AppApplication appApplication = (AppApplication) context.getApplicationContext();
        List<Activity> list = appApplication.getActivities();
        for (Activity ac : list) {
            ac.finish();
        }
        list.clear();
        appApplication.onTerminate();
    }


    public static Date convertStringToDate(String date) {
        try {
            return DEFAULT_SDF.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DEFAULT_PATTERN);

    public static AppLoadingDialog getLoadingDialog(Activity activity) {
        if (loadingDialog != null) {
            loadingDialog = null;
        }
        loadingDialog = new AppLoadingDialog(activity, R.style.alert_dialog);
        return loadingDialog;

    }

    public static void showLoadingDialog(Activity activity) {
        AppLoadingDialog dialog = getLoadingDialog(activity);
        dialog.show();
    }

    public static void dissmisLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

}
