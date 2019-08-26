package com.songy.drawdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

/**
 * Description:检测是否是模拟器
 *
 * @author by song on 2019-08-13.
 * email：bjay20080613@qq.com
 */
public class AntiEmulatorHelper {
    //检测设备IDS 是不是"000000000000000"

    public static boolean checkDevicesIDS(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return false;
        }
//        String deviceID = telephonyManager.getImei();
        return  false;
    }



}
