package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.nativeAds.AperoNativeAdView;
import com.ads.control.config.AperoAdConfig;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;

public class SimInfoActivity extends BaseActivity
{
    private AperoNativeAdView aperoNativeAdView;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_siminfo);
        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("Sim information");
        configMediationProvider();


        aperoNativeAdView = findViewById(R.id.aperoNativeAds);
        loadNative();

        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //
//        showToolBar(true,"SIM Info");

        findViewById(R.id.ly_vi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext(0);
            }
        });

        findViewById(R.id.ly_jio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext(1);
            }
        });

        findViewById(R.id.ly_airtel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext(2);
            }
        });

        findViewById(R.id.ly_bsnl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext(3);
            }
        });
    }

    private void gotoNext(int position)
    {
        Intent intent = new Intent(SimInfoActivity.this, SimCardActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
