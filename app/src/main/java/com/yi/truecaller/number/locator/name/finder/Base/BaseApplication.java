package com.yi.truecaller.number.locator.name.finder.Base;

import android.app.Application;

//import com.facebook.ads.AdSettings;
//import com.facebook.ads.AudienceNetworkAds;
import com.ads.control.admob.Admob;
import com.ads.control.ads.AperoAd;
import com.ads.control.application.AdsMultiDexApplication;
import com.ads.control.config.AdjustConfig;
import com.ads.control.config.AperoAdConfig;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.yi.truecaller.number.locator.name.finder.BuildConfig;


public class BaseApplication extends AdsMultiDexApplication {

    Boolean env_dev=true;
    @Override
    public void onCreate() {
        super.onCreate();

        initAds();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    private void initAds() {
        String environment = env_dev ? AperoAdConfig.ENVIRONMENT_DEVELOP : AperoAdConfig.ENVIRONMENT_PRODUCTION;
        aperoAdConfig = new AperoAdConfig(this, AperoAdConfig.PROVIDER_ADMOB, environment);

        // Optional: setup Adjust event
//        AdjustConfig adjustConfig = new AdjustConfig(true,ADJUST_TOKEN);
//        adjustConfig.setEventAdImpression(EVENT_AD_IMPRESSION_ADJUST);
//        adjustConfig.setEventNamePurchase(EVENT_PURCHASE_ADJUST);
//        aperoAdConfig.setAdjustConfig(adjustConfig);

        // Optional: setup Appsflyer event
//        AppsflyerConfig appsflyerConfig = new AppsflyerConfig(true,APPSFLYER_TOKEN);
//        aperoAdConfig.setAppsflyerConfig(appsflyerConfig);

        // Optional: enable ads resume
//        aperoAdConfig.setIdAdResume(BuildConfig.ads_open_app);

        // Optional: setup list device test - recommended to use
        listTestDevice.add("EC25F576DA9B6CE74778B268CB87E431");
        aperoAdConfig.setListDeviceTest(listTestDevice);

        AperoAd.getInstance().init(this, aperoAdConfig, false);

        // Auto disable ad resume after user click ads and back to app
        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        // If true -> onNextAction() is called right after Ad Interstitial showed
        Admob.getInstance().setOpenActivityAfterShowInterAds(false);

    }


}

