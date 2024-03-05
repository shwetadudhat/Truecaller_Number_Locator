package com.yi.truecaller.number.locator.name.finder.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.bannerAds.AperoBannerAdView;
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
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.GPS_Tracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationInfoActivity extends BaseActivity {
    double latitude = 0.0d;
    double longitude = 0.0d;
    LinearLayout ly_currentloction;
    TextView tv_address;
    TextView tv_city;
    TextView tv_country;
    TextView tv_latitude;
    TextView tv_longitude;
    TextView tv_state;
    LinearLayout banner_container;
    private ApInterstitialAd mInterstitialAd;
    private String idBanner = "",idNative = "",idInter = "";
    private int layoutNativeCustom;
    private AdView mAdView;
    AperoBannerAdView bannerAdView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.loctioninfo_activity);
//        showToolBar(true, "Location Info");

        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("Location information");

        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        configMediationProvider();
        initVIew();


        LinearLayout ly_currentloction = (LinearLayout) findViewById(R.id.ivCurrentLocation);
        ly_currentloction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    if (mInterstitialAd.isReady()) {
                        AperoAd.getInstance().forceShowInterstitial(LocationInfoActivity.this, mInterstitialAd, new AperoAdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
//                                Log.d(TAG, "onNextAction");
                                startActivity(new Intent("android.intent.action.VIEW",
                                        Uri.parse(String.format(Locale.ENGLISH, "geo:%f,%f",
                                                new Object[]{Double.valueOf(latitude), Double.valueOf(longitude)}))));
                                mInterstitialAd = null;
                                loadAds();
                            }

                        }, true);
                    }

                }
            }
        });
        checkPermission();
        loadAds();




    }

    public void loadAds() {

        bannerAdView.loadBanner(this, idBanner);
        mInterstitialAd = AperoAd.getInstance().getInterstitialAds(this, idInter);

    }

    private void initVIew() {

        tv_latitude = findViewById(R.id.txtLatitude);
        tv_longitude = findViewById(R.id.txtLongitude);
        tv_address = findViewById(R.id.txtAddress);
        tv_city = findViewById(R.id.txtCity);
        tv_country = findViewById(R.id.txtCountry);
        tv_state = findViewById(R.id.txtState);
        banner_container = findViewById(R.id.banner_container);
        mAdView = findViewById(R.id.adView);
        bannerAdView = findViewById(R.id.bannerView);

    }

    private void configMediationProvider() {
        if (AperoAd.getInstance().getMediationProvider() == AperoAdConfig.PROVIDER_ADMOB) {

            idBanner = getString(R.string.category_screen_banner);
            idNative = getString(R.string.check_balance_native);
            idInter = getString(R.string.interstitialADID);

            layoutNativeCustom = com.ads.control.R.layout.custom_native_admod_medium_rate;
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            GetCurrent();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Location permissions are required to do the task.");
            builder.setTitle("Please grant those permissions");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(LocationInfoActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
                }
            });
            builder.setNeutralButton("Cancel", (DialogInterface.OnClickListener) null);
            builder.create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
        }
    }

    public void GetCurrent() {

        GPS_Tracker gPS_Tracker = new GPS_Tracker(this);
        if (gPS_Tracker.isgpsenabled() && gPS_Tracker.canGetLocation()) {
            this.latitude = gPS_Tracker.getLatitude();
            this.longitude = gPS_Tracker.getLongitude();
            Log.e("latitude ----> ", this.latitude + "");
            Log.e("longitude ----> ", this.longitude + "");
            if (this.latitude == 0.0d) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        LocationInfoActivity.this.GetCurrent();
                    }
                }, 700);
            } else {
                GetCurrentAddress();
            }
        }
    }

    private void GetCurrentAddress() {
        try {
            List<Address> fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(this.latitude, this.longitude, 1);
            if (fromLocation != null && fromLocation.size() > 0 && fromLocation.get(0).getAddressLine(0) != null && fromLocation.get(0).getAddressLine(0).length() > 0) {
                String addressLine = fromLocation.get(0).getAddressLine(0);
                String locality = fromLocation.get(0).getLocality();
                String adminArea = fromLocation.get(0).getAdminArea();
                String countryName = fromLocation.get(0).getCountryName();
                fromLocation.get(0).getPostalCode();
                fromLocation.get(0).getFeatureName();
                TextView textView = this.tv_latitude;
                textView.setText(this.latitude + "");
                TextView textView2 = this.tv_longitude;
                textView2.setText(this.longitude + "");
                if (addressLine != null) {
                    TextView textView3 = this.tv_address;
                    textView3.setText(addressLine + "");
                }
                if (locality != null) {
                    TextView textView4 = this.tv_city;
                    textView4.setText(locality + "");
                }
                if (adminArea != null) {
                    TextView textView5 = this.tv_state;
                    textView5.setText(adminArea + "");
                }
                if (adminArea != null) {
                    TextView textView6 = this.tv_country;
                    textView6.setText(countryName + "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 123) {
            if (iArr.length <= 0 || iArr[0] + iArr[1] != 0) {
                Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                return;
            }
            GetCurrent();
            Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void statusCheck() {
        if (!((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled("gps")) {
            buildAlertMessageNoGps();
        } else {
            GetCurrent();
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS Location is disabled, Please enable it!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                LocationInfoActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
