package com.yi.truecaller.number.locator.name.finder.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.Constant;

public class ExitActivity extends AppCompatActivity {

    ImageView imgYes, imgNo, imgRate;

    private AperoNativeAdView aperoNativeAdView;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        configMediationProvider();

        imgYes = findViewById(R.id.imgYes);
        imgNo = findViewById(R.id.imgNo);
        imgRate = findViewById(R.id.imgRate);
        aperoNativeAdView = findViewById(R.id.aperoNativeAds);

        imgYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        imgNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.rateApp(ExitActivity.this);
            }
        });

        loadNative();
    }

    public void loadNative() {

        aperoNativeAdView.loadNativeAd(this, idNative,layoutNativeCustom,R.layout.loading_native_medium);

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