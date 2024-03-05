package com.yi.truecaller.number.locator.name.finder.Activity;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
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

public class DeviceDetailsActivity extends BaseActivity {

    String binryTypes = Build.TYPE;
    int buildversion;
    private AperoNativeAdView aperoNativeAdView;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;
    String osame;
    private TextView tv_adversion;
    private TextView tv_binarytype;
    private TextView tv_brand;
    private TextView tv_hardware;
    private TextView tv_imeinumber;
    private TextView tv_manufact;
    private TextView tv_model;
    private TextView tv_osinstall;
    String version;

    public String getOsName(int i) {
        return i == 8 ? "Froyo" : (i == 9 || i == 10) ? "Gingerbread" : (i == 11 || i == 12 || i == 13 || i == 14 || i == 15) ? "Ice Cream Sandwich" : i == 16 ? "Jelly Bean" : (i == 17 || i == 18) ? "Jelly Bean Plus" : (i == 19 || i == 20) ? "KitKat" : (i == 21 || i == 22) ? "Lollipop" : i == 23 ? "Marshmallow" : (i == 24 || i == 25) ? "Nougat" : "UnKnown";
    }

    public DeviceDetailsActivity() {
        int i = Build.VERSION.SDK_INT;
        this.buildversion = i;
        this.osame = getOsName(i);
        this.version = Build.VERSION.RELEASE;
    }

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView((int) R.layout.deviceinfo_activity);
//        showToolBar(true,"Device Details");
        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("Device information");

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

        if (Build.VERSION.SDK_INT < 23) {
            deviceInfo();
        } else if (checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"}, 0);
        } else {
            deviceInfo();
        }
        if (Build.VERSION.SDK_INT < 23) {
            networkInfo();
        } else if (checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            requestPermissions(new String[]{"android.permission.ACCESS_NETWORK_STATE"}, 0);
        } else {
            networkInfo();
        }
    }

    private void configMediationProvider() {
        if (AperoAd.getInstance().getMediationProvider() == AperoAdConfig.PROVIDER_ADMOB) {

            idBanner = getString(R.string.category_screen_banner);
            idNative = getString(R.string.check_balance_native);
            idInter = getString(R.string.interstitialADID);

            layoutNativeCustom = com.ads.control.R.layout.custom_native_admod_medium_rate;
        }
    }

    public void loadNative() {
        aperoNativeAdView.loadNativeAd(this, idNative,layoutNativeCustom,R.layout.loading_native_medium);

    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 0 && iArr[0] == 0) {
            deviceInfo();
        }
        if (i == 0 && iArr[0] == 0) {
            networkInfo();
        }
    }

    private void deviceInfo() {
        String string = Settings.Secure.getString(getApplicationContext().getContentResolver(), "android_id");
        this.tv_imeinumber = (TextView) findViewById(R.id.imeiNumber);
        this.tv_model = (TextView) findViewById(R.id.model);
        this.tv_brand = (TextView) findViewById(R.id.brand);
        this.tv_manufact = (TextView) findViewById(R.id.manufact);
        this.tv_hardware = (TextView) findViewById(R.id.hardware);
        this.tv_imeinumber.setText(string);
        this.tv_model.setText(Build.MODEL);
        this.tv_brand.setText(Build.BRAND);
        this.tv_manufact.setText(Build.MANUFACTURER);
        this.tv_hardware.setText(Build.HARDWARE);
    }

    private void networkInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("android_id");
        this.tv_osinstall = (TextView) findViewById(R.id.osInstall);
        this.tv_adversion = (TextView) findViewById(R.id.androidVersion);
        this.tv_binarytype = (TextView) findViewById(R.id.binaryType);
        this.tv_osinstall.setText(this.osame);
        this.tv_adversion.setText(this.version);
        this.tv_binarytype.setText(this.binryTypes);
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
