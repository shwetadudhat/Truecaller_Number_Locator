package com.yi.truecaller.number.locator.name.finder.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.yi.truecaller.number.locator.name.finder.R;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView webView = findViewById(R.id.web);
        webView.loadUrl("https://yanshinfotech21.blogspot.com/2022/08/policy-1we-built-play-store-app-app-as.html");
    }
}