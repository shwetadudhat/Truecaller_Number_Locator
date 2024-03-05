package com.yi.truecaller.number.locator.name.finder.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.nativeAds.AperoNativeAdView;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.config.AperoAdConfig;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;

public class CheckBankBalanceActivity extends BaseActivity
{
    ApInterstitialAd mInterstitialAd;
    private AperoNativeAdView aperoNativeAdView;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.checkbankbalance_activity);



        TextView textView = findViewById(R.id.text);
        textView.setText(getIntent().getStringExtra("bankName"));

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {

                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(CheckBankBalanceActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                onBackPressed();
                                mInterstitialAd = null;
                                loadAds();
                            }

                        }, true);
                    }
                }
//                onBackPressed();
            }
        });

        String enQuiry = getIntent().getStringExtra("enquiry");
        String customerCare = getIntent().getStringExtra("customer");

        TextView enquiryCall = findViewById(R.id.balanceNumber);
        TextView customerCall = findViewById(R.id.customerNumber);

        enquiryCall.setText(enQuiry);
        customerCall.setText(customerCare);
        configMediationProvider();


        aperoNativeAdView = findViewById(R.id.aperoNativeAds);
        loadNative();
        loadAds();
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void loadAds() {
        mInterstitialAd = AperoAd.getInstance().getInterstitialAds(this, idInter);

    }


}
