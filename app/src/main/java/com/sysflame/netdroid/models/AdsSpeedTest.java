package com.sysflame.netdroid.models;

import static com.sysflame.netdroid.utils.LogUtils.LOGI;
import static com.sysflame.netdroid.utils.LogUtils.makeLogTag;

import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.sysflame.netdroid.R;

import java.util.ArrayList;
import java.util.List;


public class AdsSpeedTest {

	private static final String TAG = makeLogTag (AdsSpeedTest.class);
	private Activity activity;
	private AdView adView;
	private InterstitialAd interstitialAd;
	private List<String> testDevices = new ArrayList<> ();



	public AdsSpeedTest () {
	}

	public AdsSpeedTest (Activity activity) {
		this.activity = activity;
		testDevices.add (AdRequest.DEVICE_ID_EMULATOR);
		MobileAds.initialize (activity.getApplicationContext (), initializationStatus -> {
		});
		RequestConfiguration requestConfiguration = new RequestConfiguration.Builder ()
				.setTestDeviceIds (testDevices)
				.build ();
		MobileAds.setRequestConfiguration (requestConfiguration);
	}



	/**
	 * Init interstitial.
	 */
	public void initInterstitial () {
		interstitialAd = new InterstitialAd (activity.getApplicationContext ());
		interstitialAd.setAdUnitId (activity.getResources ().getString (R.string.admob_app_interstitial));
		interstitialAd.loadAd (new AdRequest.Builder ().build ());
		interstitialAd.setAdListener (new AdListener () {
			@Override
			public void onAdLoaded () {
				LOGI (TAG, "onAdLoaded()");
			}

			@Override
			public void onAdFailedToLoad (int errorCode) {
				LOGI (TAG, "onAdFailedToLoad() with error code: " + errorCode);
			}

			@Override
			public void onAdOpened () {
				LOGI (TAG, "onAdOpened()");
			}

			@Override
			public void onAdClicked () {
				LOGI (TAG, "onAdClicked()");

			}

			@Override
			public void onAdLeftApplication () {
				LOGI (TAG, "onAdLeftApplication()");
			}

			@Override
			public void onAdClosed () {
				LOGI (TAG, "onAdClosed()");
				if (!interstitialAd.isLoading () && !interstitialAd.isLoaded ()) {
					interstitialAd.loadAd (new AdRequest.Builder ().build ());
				}
			}
		});
	}


	public void showInterstitial () {
		if (interstitialAd != null && interstitialAd.isLoaded ()) {
			interstitialAd.show ();
		} else {
			LOGI (TAG, "ad did not load");
		}
	}

	public InterstitialAd getInterstitialAd () {
		return interstitialAd;
	}

	public void setInterstitialAd (InterstitialAd interstitialAd) {
		this.interstitialAd = interstitialAd;
	}
}
