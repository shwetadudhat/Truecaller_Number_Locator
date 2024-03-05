package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.nativeAds.AperoNativeAdView;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.config.AperoAdConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;

public class CallerIdActivity extends BaseActivity {

//    private InterstitialAd mInterstitialAd;
    ApInterstitialAd mInterstitialAd;
    private AperoNativeAdView aperoNativeAdView;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callerid);

        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("Caller ID");

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
        findViewById(R.id.ly_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isReady()) {
                    AperoAd.getInstance().forceShowInterstitial(CallerIdActivity.this, mInterstitialAd, new AperoAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                            gotoNext(NumberSearchActivity.class);
                            mInterstitialAd = null;
                            loadAds();
                        }

                    }, true);
                }

            }
        });

        findViewById(R.id.ly_loctioninfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager lm = (LocationManager) CallerIdActivity.this.getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;

                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex) {
                }

                try {
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (!gps_enabled && !network_enabled) {
                    // notify user
                    new AlertDialog.Builder(CallerIdActivity.this)
                            .setMessage(R.string.gps_network_not_enabled)
                            .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                            .setNegativeButton(R.string.Cancel, null)
                            .show();
                } else {
                    gotoNext(LocationInfoActivity.class);
                }
            }
        });

        findViewById(R.id.ly_isdcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isReady()) {
                    AperoAd.getInstance().forceShowInterstitial(CallerIdActivity.this, mInterstitialAd, new AperoAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                            gotoNext(ISDCodeActivity.class);
                            mInterstitialAd = null;
                            loadAds();
                        }

                    }, true);
                }


            }
        });

        findViewById(R.id.ly_stdcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isReady()) {
                    AperoAd.getInstance().forceShowInterstitial(CallerIdActivity.this, mInterstitialAd, new AperoAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                            gotoNext(STDCodeActivity.class);
                            mInterstitialAd = null;
                            loadAds();
                        }

                    }, true);
                }

            }
        });

        loadAds();
//        loadNative();
    }



    public void loadAds() {

        mInterstitialAd = AperoAd.getInstance().getInterstitialAds(this,idInter);
    }

    private void gotoNext(Class actObj) {
//        InterstrialUtils.getSharedInstance().showInterstrialAd(CallerIdActivity.this, false, new InterstrialUtils.AdCloseListener() {
//            @Override
//            public void onAdClosed() {
        Intent intent = new Intent(CallerIdActivity.this, actObj);
        startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
