package com.yi.truecaller.number.locator.name.finder.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

//import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.Preferences;

public class BaseActivity extends AppCompatActivity {
    protected Dialog dialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Toast toast;
    public Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        pref = new Preferences(this);
        //requestAPI = APIClient2.getClient().create(APIServices.class);
    }

    public void hideStatusBar() {
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void showToast(final String text, final boolean isShort) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public BaseActivity getActivity() {
        return this;
    }

    public boolean checkConnection() {
        NetworkInfo info = getNetworkInfo(this);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public void connectivityDialog() {
        this.dialog = new Dialog(this);
        this.dialog.setContentView(R.layout.no_internet_dialog);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
        ((TextView) this.dialog.findViewById(R.id.try_again)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getActivity().recreate();
            }
        });
    }
}
