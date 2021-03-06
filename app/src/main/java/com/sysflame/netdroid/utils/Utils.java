package com.sysflame.netdroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class Utils {
	private Utils () {
		throw new IllegalStateException ("Utility class");
	}


	public static void EnableNotification (Context context, String key,
	                                       boolean value) {
		SharedPreferences preferences = context.getSharedPreferences (
				Constant.SHARED_PREFS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit ();
		editor.putBoolean (key, value);
		editor.commit ();
	}

	public static boolean isEnabledNotification (Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences (
				Constant.SHARED_PREFS, Context.MODE_PRIVATE);
		return preferences.getBoolean (key, true);
	}
	public static String getWifiName(Context context) {
		WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		if (manager.isWifiEnabled()) {
			WifiInfo wifiInfo = manager.getConnectionInfo();
			if (wifiInfo != null) {
				NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
				if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
					return wifiInfo.getSSID();
				}
			}
		}
		return null;
	}
}
