package com.woof.gankist.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Woof on 3/20/2017.
 */

public class NetworkStateUtil {

    public static boolean isNetworkConnected(Context mContext){
        // 所有种类的网络链接是否正常
        if (mContext != null){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null){
                // 获取网络信息，判断是否是否有效
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    // 检查WiFi是否连接
    public static boolean wifiConnected(Context context){
        if (context != null){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null){
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                    return networkInfo.isAvailable();
            }
        }
        return false;
    }

    // 检查移动网络是否连接
    public static boolean mobileDataConnected(Context context){
        if (context != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null){
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                    return true;
            }
        }
        return false;
    }
}
