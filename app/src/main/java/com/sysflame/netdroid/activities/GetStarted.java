package com.sysflame.netdroid.activities;

import static com.sysflame.netdroid.utils.LogUtils.LOGI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.sysflame.netdroid.R;
import com.sysflame.netdroid.models.AdsSpeedTest;

public class GetStarted extends AppCompatActivity {

    private static final String TAG = "GetStarted";
    // private InterstitialAd interstitialAd;
    private AdsSpeedTest adsSpeedTest;


    Button getStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        init();
        MobileAds.initialize(this.getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });


        getStart = findViewById(R.id.get_started);
        getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adsSpeedTest.getInterstitialAd() != null && adsSpeedTest.getInterstitialAd().isLoaded()) {
                    adsSpeedTest.getInterstitialAd().show();
                } else {
                    Intent intent = new Intent(GetStarted.this, HomeActivity.class);
                    startActivity(intent);
                }

                /*Intent intent = new Intent(GetStarted.this, HomeActivity.class);
                startActivity(intent);*/
            }
        });

    }
    public void init() {
        adsSpeedTest = new AdsSpeedTest(this);
        adsSpeedTest.initInterstitial();
        adsSpeedTest.getInterstitialAd().setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (!adsSpeedTest.getInterstitialAd().isLoading() && !adsSpeedTest.getInterstitialAd().isLoaded()) {
                    adsSpeedTest.initInterstitial();
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Intent intent = new Intent(GetStarted.this, HomeActivity.class);
                startActivity(intent);
                /* if (!adsSpeedTest.getInterstitialAd().isLoading() && !adsSpeedTest.getInterstitialAd().isLoaded()) {
                    adsSpeedTest.initInterstitial();
                }
                if (testing) {
                    testSpeed();
                }*/
            }

            @Override
            public void onAdOpened() {
                LOGI(TAG, "onAdOpened()");
            }

            @Override
            public void onAdClicked() {
                LOGI(TAG, "onAdClicked()");
            }

            @Override
            public void onAdLeftApplication() {
                LOGI(TAG, "onAdLeftApplication()");
            }
            @Override
            public void onAdClosed() {
                LOGI(TAG, "onAdClosed()");
                Intent intent = new Intent(GetStarted.this, HomeActivity.class);
                startActivity(intent);
               /* if (!adsSpeedTest.getInterstitialAd().isLoading() && !adsSpeedTest.getInterstitialAd().isLoaded()) {
                    adsSpeedTest.initInterstitial();
                }*/
            }
        });
    }
}