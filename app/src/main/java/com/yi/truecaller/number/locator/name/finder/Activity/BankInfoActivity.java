package com.yi.truecaller.number.locator.name.finder.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.fragment.BankInfo_Fragment;

public class BankInfoActivity extends BaseActivity implements BankInfo_Fragment.OnFragmentInteractionListener
{
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.bankinfo_activity);

        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("Bank Service");

        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container_bank, BankInfo_Fragment.newInstance());
        beginTransaction.commit();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
