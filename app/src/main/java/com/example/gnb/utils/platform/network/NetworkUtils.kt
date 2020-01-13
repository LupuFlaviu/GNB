package com.example.gnb.utils.platform.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.gnb.BuildConfig
import java.net.InetAddress

/**
 * Helper class for networking operations
 */
class NetworkUtils {

    companion object {
        /**
         * Check whether the device has internet connectivity
         * @param context the application context
         * @return true if it has, false otherwise
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
    }
}