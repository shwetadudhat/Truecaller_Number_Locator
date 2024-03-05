package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//import com.facebook.ads.AdSettings;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;

public class SplashActivity extends BaseActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        hideStatusBar();
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);

//        AdSettings.addTestDevice("0f0fa9fa-6155-4845-a4dd-8d5d968c95e4");
//
        postDelay(3000);
    }

    private void postDelay(long milliSec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        }, milliSec);
    }
}