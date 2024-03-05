package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.config.AperoAdConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.fragment.CheckBankBalanceActivity;

public class NearByActivity extends BaseActivity {

    String[] placenames = {"delivery", "bus station", "train station", "airport", "restaurant", "bank", "atm", "hospital", "police", "lodging", "car repair", "gas station", "mosque", "hindu temple", "church", "jewelry store", "shopping mall", "bar", "spa", "beauty salon", "amusement park", "aquarium", "park", "zoo", "cafe", "dry cleaning", "pharmacy", "bakery", "doctor", "veterinary care", "dentist", "gym", "book store", "bowling alley", "car rental", "car wash", "taxi stand", "parking", "art gallery", "electronics store", "casino", "convenience store", "school", "fire station", "lawyer", "department store", "library", "liquor store", "movie theater", "museum", "night club", "pet store", "stadium", "local government office", "groceries", "car dealers", "home garden store", "clothing stores"};
    int position;
    private ApInterstitialAd mInterstitialAd;

    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView((int) R.layout.nearbyme_activity);

        configMediationProvider();

        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("Near by me");

        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(NearByActivity.this, mInterstitialAd, new AperoAdCallback() {
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
            }
        });

        loadAds();

//        showToolBar(true,"Near By Places");
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

    public void restaurant(View view) {
        doAction(4);
    }

    public void bar(View view) {
        doAction(17);
    }

    public void cafe(View view) {
        doAction(24);
    }

    public void delivery(View view) {
        doAction(0);
    }

    public void lodging(View view) {
        doAction(9);
    }

    public void atm(View view) {
        doAction(6);
    }

    public void saloon(View view) {
        doAction(19);
    }

    public void bank(View view) {
        doAction(5);
    }

    public void drycleaning(View view) {
        doAction(25);
    }

    public void hospitals(View view) {
        doAction(7);
    }

    public void gas(View view) {
        doAction(11);
    }

    public void pharmacy(View view) {
        doAction(26);
    }

    public void parking(View view) {
        doAction(37);
    }

    public void park(View view) {
        doAction(22);
    }

    public void gym(View view) {
        doAction(31);
    }

    public void art(View view) {
        doAction(38);
    }

    public void stadium(View view) {
        doAction(52);
    }

    public void nightlife(View view) {
        doAction(50);
    }

    public void amusement(View view) {
        doAction(20);
    }

    public void movies(View view) {
        doAction(48);
    }

    public void musium(View view) {
        doAction(49);
    }

    public void library(View view) {
        doAction(46);
    }

    public void groceries(View view) {
        doAction(54);
    }

    public void book(View view) {
        doAction(32);
    }

    public void cardealer(View view) {
        doAction(55);
    }

    public void homegarden(View view) {
        doAction(56);
    }

    public void clothing(View view) {
        doAction(57);
    }

    public void shopping(View view) {
        doAction(16);
    }

    public void electronics(View view) {
        doAction(39);
    }

    public void jewellery(View view) {
        doAction(15);
    }

    public void convenience(View view) {
        doAction(41);
    }

    public void doAction(int i) {
        position = i;

        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.co.in/maps?q=" + placenames[i])));

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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
