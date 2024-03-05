package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.bannerAds.AperoBannerAdView;
import com.ads.control.ads.nativeAds.AperoNativeAdView;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.config.AperoAdConfig;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.BuildConfig;
import com.yi.truecaller.number.locator.name.finder.R;

public class CategoryActivity extends BaseActivity {

    private AdView mAdView;
    private ApInterstitialAd mInterstitialAd;

    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        configMediationProvider();


       /* mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        AperoBannerAdView bannerAdView = findViewById(R.id.bannerView);
        bannerAdView.loadBanner(this, idBanner);

//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//                super.onAdClicked();
//            }
//
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                mAdView.loadAd(adRequest);
//            }
//
//            @Override
//            public void onAdImpression() {
//                super.onAdImpression();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//        });

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.imgSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.callerLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(CallerIdActivity.class);
            }
        });

        findViewById(R.id.deviceLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(CategoryActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                gotoNextActivity(DeviceDetailsActivity.class);
                                mInterstitialAd = null;
                                loadAds();
                            }

                        }, true);
                    }

                }

            }
        });

        findViewById(R.id.simLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(SimInfoActivity.class);
            }
        });

        findViewById(R.id.trafficLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(CategoryActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                gotoNextActivity(TrafficFinderActivity.class);
                                mInterstitialAd = null;
                                loadAds();
                            }

                        }, true);
                    }

                }
            }
        });

        findViewById(R.id.bankInfoLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(CategoryActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                gotoNextActivity(BankInfoActivity.class);
                                mInterstitialAd = null;
                                loadAds();
                            }

                        }, true);
                    }

                }
            }
        });

        findViewById(R.id.nearByLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(CategoryActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                gotoNextActivity(NearByActivity.class);
                                mInterstitialAd = null;
                                loadAds();
                            }

                        }, true);
                    }
                }
            }
        });

        loadAds();
    }

    private void configMediationProvider() {
        if (AperoAd.getInstance().getMediationProvider() == AperoAdConfig.PROVIDER_ADMOB) {

            idBanner = getString(R.string.category_screen_banner);
            idNative = getString(R.string.check_balance_native);
            idInter = getString(R.string.interstitialADID);

            layoutNativeCustom = com.ads.control.R.layout.custom_native_admod_medium_rate;
        }
    }

    public void loadAds() {
        mInterstitialAd = AperoAd.getInstance().getInterstitialAds(this, idInter);

    }

    private void gotoNextActivity(Class actObj)
    {
//        InterstrialUtils.getSharedInstance().showInterstrialAd(CategoryActivity.this, false, new InterstrialUtils.AdCloseListener() {
//            @Override
//            public void onAdClosed() {
//
//            }
//        });
        Intent intent = new Intent(CategoryActivity.this,actObj);
        startActivity(intent);
    }
}
