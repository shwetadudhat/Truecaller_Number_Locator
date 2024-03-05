package com.yi.truecaller.number.locator.name.finder.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.config.AperoAdConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.yi.truecaller.number.locator.name.finder.Adaptor.SimDetailsAdapter;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;

public class SimCardActivity extends BaseActivity {

    String title;
    ApInterstitialAd mInterstitialAd;

    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;

    public static String[] arr = {"How To Recharge", "Main Balance Enquiry", "Best Offers", "Net Balance Enquiry", "How to Know your number", "Customer care number"};
    String[] airtel = {"*120*(16 digits code)#", "*123#", "*121*1# OR Call on 12131", "*121*2# OR Dial *121#", "*282# OR *121*9#", "121 or 198"};
    String[] bsnl = {"*124*2*(16 digits code)#", "*123#", "*124*5#1", "*234#", "164 OR *8888#", "1503 OR 1800-345-1500"};
    String[] data;
    String[] jio = {"199", "*333#", "199", "*333*1*3*#", "*1#", "1800 889 9999"};
    public int position;
    protected RecyclerView rv_sim_details;
    String[] vodafone = {"*199*5#", "*199*2*1#", "**199*1#", "*199*2*2#", "*111*2#", "199"};

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView((int) R.layout.siminfo_activity);
//        showToolBar(true,"SIM Info");
        configMediationProvider();
        loadAds();
        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(SimCardActivity.this, mInterstitialAd, new AperoAdCallback() {
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

        position = getIntent().getIntExtra("position", 0);
        this.rv_sim_details = (RecyclerView) findViewById(R.id.rv_sim_details);
        this.rv_sim_details.setHasFixedSize(true);
        this.rv_sim_details.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if (this.position == 0) {
            this.data = this.vodafone;
            this.title = "Vodafone";
        }
        if (this.position == 1) {
            this.data = this.jio;
            this.title = "Jio";
        }
        if (this.position == 2) {
            this.data = this.airtel;
            this.title = "Airtel";
        }
        if (this.position == 3) {
            this.data = this.bsnl;
            this.title = "BSNL";
        }
        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText(title+" information");
        this.rv_sim_details.setAdapter(new SimDetailsAdapter(this, this.data));
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

    public void loadAds() {
        mInterstitialAd = AperoAd.getInstance().getInterstitialAds(this, idInter);
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
