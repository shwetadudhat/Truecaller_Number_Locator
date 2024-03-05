package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
//import com.jojoapps.callerid.truecalleridname.location.Utils.AdsManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.Constant;

import java.util.List;

public class StartActivity extends BaseActivity {
    boolean doubleBackToExitPressedOnce = false;
    ApInterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView rateUsImg = findViewById(R.id.rateUsImg);
        ImageView shareImg = findViewById(R.id.shareImg);
        ImageView moreImg = findViewById(R.id.moreImg);

        //
        ImageView startImg = findViewById(R.id.startImg);
        startImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(StartActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                callnextactivity();
                            }

                        }, true);
                    } else {
                        loadAdInterstitial();
                    }
                    callnextactivity();

                } else if (!Settings.canDrawOverlays(StartActivity.this)) {
                    checkOverlaysPermission();
                } else if (!Settings.System.canWrite(StartActivity.this)) {
                    checkWriteSettingsPermission();
                } else {
                    askPermission("1");
                }
            }
        });

        //
        rateUsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.rateApp(StartActivity.this);
            }
        });

        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.shareApp(StartActivity.this);
            }
        });

        moreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    public void loadAdInterstitial() {
         mInterstitialAd = AperoAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.interstitialADID));
    }

    public void askPermission(final String str) {
        Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION").withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted() && str.equals("1")) {
                    callnextactivity();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    /* access modifiers changed from: private */
    public void checkWriteSettingsPermission() {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 111);
    }

    /* access modifiers changed from: private */
    public void checkOverlaysPermission() {
        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), 222);
    }


    private void callnextactivity() {
        Intent intent = new Intent(StartActivity.this, CategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StartActivity.this, ExitActivity.class);
        startActivity(intent);
        /*if (doubleBackToExitPressedOnce) {
            return;
        }*/

        /*this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}