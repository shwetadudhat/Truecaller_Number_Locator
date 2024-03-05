package com.yi.truecaller.number.locator.name.finder.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.ads.control.ads.AperoAd;
import com.ads.control.ads.nativeAds.AperoNativeAdView;
import com.ads.control.config.AperoAdConfig;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.yi.truecaller.number.locator.name.finder.BuildConfig;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.Constant;

public class SettingActivity extends AppCompatActivity {

    ImageView imgBack, imgSettingVersion, imgSettingContactUs, imgSettingRateApp, imgSettingShareApp, imgSettingMoreApp;
    private AperoNativeAdView aperoNativeAdView;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        configMediationProvider();

        imgBack = findViewById(R.id.imgBack);
        imgSettingVersion = findViewById(R.id.imgSettingVersion);
        imgSettingContactUs = findViewById(R.id.imgSettingContactUs);
        imgSettingRateApp = findViewById(R.id.imgSettingRateApp);
        imgSettingShareApp = findViewById(R.id.imgSettingShareApp);
        imgSettingMoreApp = findViewById(R.id.imgSettingMoreApp);
        aperoNativeAdView = findViewById(R.id.aperoNativeAds);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgSettingVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgSettingContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        imgSettingRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.rateApp(SettingActivity.this);
            }
        });

        imgSettingShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.shareApp(SettingActivity.this);
            }
        });

        imgSettingMoreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.more_app != null && !Constant.more_app.equals("")) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Constant.more_app));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constant.more_app)));
                    }
                }
            }
        });

        loadNative();
    }

    public void loadNative() {

        aperoNativeAdView.loadNativeAd(this, idNative,layoutNativeCustom,R.layout.loading_native_medium);

//        AperoAd.getInstance().loadNativeAd(requireActivity(), getString(R.string.admod_native_id), com.ads.control.R.layout.custom_native_admob_free_size, flPlaceHolder, shimmerFrameLayout);

//        MobileAds.initialize(this);
//        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.setting_screen_native))
//                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//                    @Override
//                    public void onNativeAdLoaded(NativeAd nativeAd) {
//                        NativeTemplateStyle styles = new
//                                NativeTemplateStyle.Builder().build();
//                        TemplateView my_template_small = findViewById(R.id.my_template_small);
//                        my_template_small.setStyles(styles);
//                        my_template_small.setNativeAd(nativeAd);
//                    }
//                })
//                .build();
//
//        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void configMediationProvider() {
        if (AperoAd.getInstance().getMediationProvider() == AperoAdConfig.PROVIDER_ADMOB) {

            idBanner = getString(R.string.category_screen_banner);
            idNative = getString(R.string.check_balance_native);
            idInter = getString(R.string.interstitialADID);

            layoutNativeCustom = com.ads.control.R.layout.custom_native_admod_medium_rate;
        }
    }
}