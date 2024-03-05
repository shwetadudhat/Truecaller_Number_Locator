package com.yi.truecaller.number.locator.name.finder.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.Constructor.NumberSearch_Constructor;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.DataBase_Helper;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class NumberSearchActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String DB_NAME = "contactlistdatabase";
    public static final String PREF_NAME = "MyPrefsFile";
    public ArrayList<NumberSearch_Constructor> arraylist;
    protected ArrayList at_maplist;
    Button btn_call;
    Button btn_save;
    CircleImageView circleimageview;
    public String country;
    public String country_code;
    public String country_name;
    public Cursor cursor;
    DataBase_Helper databasehelper;
    public EditText et_numberedit;
    public GoogleMap googlemap;
    ImageView iv_back;
    protected ImageView iv_submit;
    private String photoUri;
    public ProgressDialog progressdialog;
    RelativeLayout rl_dragView;
    RelativeLayout rl_iconlayout;
    RelativeLayout rl_userprofile;
    RelativeLayout rl_viewlayout;
    protected String sg_contactid;
    private String sg_contactname;
    protected String sg_finalnumber;
    String sg_number;
    public String sg_threechar;
    TextView tv_comma;
    TextView tv_countrytext;
    TextView tv_name;
    TextView tv_operator;
    TextView tv_state;


    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.searchnumber_activity);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        this.rl_dragView = (RelativeLayout) findViewById(R.id.dragView);
        this.rl_iconlayout = (RelativeLayout) findViewById(R.id.iconlay);
        this.rl_viewlayout = (RelativeLayout) findViewById(R.id.viewlay);
        this.rl_userprofile = (RelativeLayout) findViewById(R.id.userprofile);
        this.circleimageview = (CircleImageView) findViewById(R.id.logoimage);
        this.tv_name = (TextView) findViewById(R.id.name);
        this.tv_operator = (TextView) findViewById(R.id.operator);
        this.tv_state = (TextView) findViewById(R.id.state);
        this.tv_comma = (TextView) findViewById(R.id.comma);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_countrytext = (TextView) findViewById(R.id.country);
        this.btn_call = (Button) findViewById(R.id.pop_call);
        this.btn_save = (Button) findViewById(R.id.pop_save);
        this.btn_call.setOnClickListener(this);
        this.btn_save.setOnClickListener(this);
        this.iv_back.setOnClickListener(this);
        this.iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, 0);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        this.country = sharedPreferences.getString("COUNTRY_CODE_ISO", "+91");
        this.country_name = sharedPreferences.getString("COUNTRY_NAME", "India");
        this.country_code = sharedPreferences.getString("COUNTRY_CODE", (String) null);
        Log.e("Logforcountrylist", sharedPreferences.getString("COUNTRY_CODE_ISO", (String) null) + "\n" + sharedPreferences.getString("COUNTRY_NAME", (String) null) + "\n" + sharedPreferences.getString("COUNTRY_CODE", (String) null));
        this.databasehelper = DataBase_Helper.getInstance(this, DB_NAME);
        CountryCodePicker countryCodePicker = (CountryCodePicker) findViewById(R.id.spincountry);
        String str = this.country;
        if (str != null) {
            countryCodePicker.setCountryForNameCode(str.toUpperCase());
            countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                public void onCountrySelected(Country country) {
                    edit.putString("COUNTRY_CODE_ISO", country.getIso());
                    edit.putString("COUNTRY_NAME", country.getName());
                    SharedPreferences.Editor editor = edit;
                    editor.putString("COUNTRY_CODE", "+" + country.getPhoneCode());
                    Context applicationContext = getApplicationContext();
                    Toast.makeText(applicationContext, "plus" + country.getPhoneCode(), Toast.LENGTH_LONG).show();
                    edit.apply();
                    if (googlemap != null) {
                        CallMapMethod(country.getName());
                    }
                }
            });
        }
        this.et_numberedit = (EditText) findViewById(R.id.et1);
        ImageView imageView = (ImageView) findViewById(R.id.submitfinal);
        this.iv_submit = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rl_dragView.setVisibility(View.VISIBLE);
                NumberSearchActivity numberSearch_Activity = NumberSearchActivity.this;
                numberSearch_Activity.sg_number = numberSearch_Activity.et_numberedit.getText().toString();
                if (sg_number == null || sg_number.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please enter mobile number", Toast.LENGTH_LONG).show();
                    return;
                }
                country = sharedPreferences.getString("COUNTRY_CODE_ISO", "India");
                country_name = sharedPreferences.getString("COUNTRY_NAME", "+91");
                country_code = sharedPreferences.getString("COUNTRY_CODE", "+91");
                NumberSearchActivity numberSearch_Activity2 = NumberSearchActivity.this;
                if (numberSearch_Activity2.isValidMobile(numberSearch_Activity2.sg_number)) {
                    if (country_code != null) {
                        if (country_code.equalsIgnoreCase("+91")) {
                            NumberSearchActivity numberSearch_Activity3 = NumberSearchActivity.this;
                            numberSearch_Activity3.sg_threechar = numberSearch_Activity3.sg_number.substring(0, 4);
                        } else {
                            NumberSearchActivity numberSearch_Activity4 = NumberSearchActivity.this;
                            numberSearch_Activity4.sg_threechar = numberSearch_Activity4.sg_number.substring(0, 3);
                        }
                    }
                    try {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception unused) {
                    }
                    NumberSearchActivity numberSearch_Activity5 = NumberSearchActivity.this;
                    new DBAsyncTask(numberSearch_Activity5.sg_threechar).execute(new Void[0]);
                    return;
                }
                Toast.makeText(getApplicationContext(), "Please Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class DBAsyncTask extends AsyncTask<Void, String, String> {
        public DBAsyncTask(String str) {
        }

        public void onPreExecute() {
            super.onPreExecute();
            if (rl_iconlayout.getVisibility() == View.VISIBLE) {
                tv_operator.setText("");
                tv_state.setText("");
                tv_comma.setText("");
                tv_countrytext.setText("");
            }
            NumberSearchActivity numberSearch_Activity = NumberSearchActivity.this;
            numberSearch_Activity.progressdialog = new ProgressDialog(numberSearch_Activity);
            progressdialog.setTitle("Please Wait");
            progressdialog.setMessage("Fetching search information");
            progressdialog.show();
        }

        public String doInBackground(Void... voidArr) {
            NumberSearchActivity numberSearch_Activity = NumberSearchActivity.this;
            numberSearch_Activity.callDbNumberSearch(numberSearch_Activity.sg_threechar);
            return null;
        }

        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (progressdialog.isShowing()) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (!isFinishing() && progressdialog != null) {
                            progressdialog.dismiss();
                        }
                    }
                }, 1000);
            }
            if (cursor.getCount() == 0) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        try {
                            getcontactname(sg_number);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 320);
                Toast.makeText(getApplicationContext(), "Phone Number Not Found", Toast.LENGTH_LONG).show();
                tv_operator.setText("UnKnown");
                tv_state.setVisibility(View.GONE);
                tv_comma.setVisibility(View.GONE);
                tv_countrytext.setText(country_name);
                return;
            }
            Iterator<NumberSearch_Constructor> it = arraylist.iterator();
            while (it.hasNext()) {
                NumberSearch_Constructor next = it.next();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        getcontactname(sg_number);
                    }
                }, 320);
                rl_iconlayout.setVisibility(View.VISIBLE);
                circleimageview.setImageDrawable(getResources().getDrawable(R.drawable.pop_user));
                rl_viewlayout.setVisibility(View.VISIBLE);
                rl_userprofile.setVisibility(View.VISIBLE);
                if (country_code.equalsIgnoreCase("+1")) {
                    tv_operator.setText(next.getOperator());
                    tv_state.setText(next.getStateName());
                    CallMapMethod(next.getStateName());
                } else {
                    String stateName = next.getStateName();
                    if (stateName.equalsIgnoreCase("NewJersey")) {
                        tv_operator.setText("UnKnown");
                        tv_comma.setVisibility(View.GONE);
                        tv_state.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("DistrictOfColumbia")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Connecticut")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Alabama")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Washington")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Maine")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("California")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Texas")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("NonGeographic")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("NewYork")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Pennsylvania")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Ohio")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Illinois")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Minnesota")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Louisiana")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Ontario")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Mississippi")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Georgia")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Michigan")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("BritishColumbia")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Florida")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Maryland")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("FreeportNassau")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Bridgetown")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Alabama")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Indiana")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Wisconsin")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("StJohns")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Kentucky")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Virginia")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Delaware")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Colorado")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("WestVirginia")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Saskathcewan")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Wyoming")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Nebraska")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Missouri")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Kansas")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Iowa")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("NorthCarolina")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Massachusetts")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("CharlotteAmalie")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("GeorgeTown")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Utah")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Alberta")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Oklahoma")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Montana")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Quebec")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Tennessee")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Pembroke")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Arkansas")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Arizona")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("NewMexico")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("NewBrunswick")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("Oregon")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("NewHampshire")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("SouthDakota")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else if (stateName.equalsIgnoreCase("BradesEstate")) {
                        tv_operator.setText("UnKnown");
                        tv_state.setVisibility(View.GONE);
                        tv_comma.setVisibility(View.GONE);
                    } else {
                        tv_operator.setText(next.getOperator());
                        tv_comma.setVisibility(View.VISIBLE);
                        tv_state.setVisibility(View.VISIBLE);
                        tv_state.setText(next.getStateName());
                        tv_comma.setText(",");
                        CallMapMethod(next.getStateName());
                    }
                }
                tv_countrytext.setText(country_name);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isValidMobile(String str) {
        return !Pattern.matches("[a-zA-Z]+", str) && str.length() > 6 && str.length() <= 13;
    }

    @SuppressLint("Range")
    public void callDbNumberSearch(String str) {
        ArrayList<NumberSearch_Constructor> arrayList = new ArrayList<>();
        this.arraylist = arrayList;
        arrayList.clear();
        Cursor rawQuery = DataBase_Helper.rawQuery("SELECT operatorname, statename,iconval,lat,lang FROM mobileNumberfinder WHERE mobilenumber =" + str);
        this.cursor = rawQuery;
        if (rawQuery.moveToFirst()) {
            do {
                NumberSearch_Constructor numberSearch_Constructor = new NumberSearch_Constructor();
                Cursor cursor2 = this.cursor;
                numberSearch_Constructor.setOperator(cursor2.getString(cursor2.getColumnIndex("operatorname")));
                Cursor cursor3 = this.cursor;
                numberSearch_Constructor.stateName(cursor3.getString(cursor3.getColumnIndex("statename")));
                Cursor cursor4 = this.cursor;
                numberSearch_Constructor.iconValue(Integer.parseInt(cursor4.getString(cursor4.getColumnIndex("iconval"))));
                Cursor cursor5 = this.cursor;
                numberSearch_Constructor.setLatitude(cursor5.getString(cursor5.getColumnIndex("lat")));
                Cursor cursor6 = this.cursor;
                numberSearch_Constructor.setLongitude(cursor6.getString(cursor6.getColumnIndex("lang")));
                this.arraylist.add(numberSearch_Constructor);
            } while (this.cursor.moveToNext());
        }
    }

    @SuppressLint("Range")
    public void getcontactname(String str) {
        if (str.length() > 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                this.sg_finalnumber = this.country_code + PhoneNumberUtils.formatNumber(str, Locale.getDefault().getCountry());
            } else {
                this.sg_finalnumber = this.country_code + PhoneNumberUtils.formatNumber(str);
            }
            Cursor query = getContentResolver().query(Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(this.sg_finalnumber)), (String[]) null, (String) null, (String[]) null, (String) null);
            if (query.moveToFirst()) {
                try {
                    this.sg_contactid = query.getString(query.getColumnIndex("contact_id"));
                    this.sg_contactname = query.getString(query.getColumnIndex("display_name"));
                    Log.v("contact_name", "" + this.tv_name);
                    this.photoUri = query.getString(query.getColumnIndex("photo_uri"));
                } catch (CursorIndexOutOfBoundsException unused) {
                }
                query.close();
                if (!TextUtils.isEmpty(this.sg_contactname)) {
                    this.tv_name.setText(this.sg_contactname);
                    if (!TextUtils.isEmpty(this.photoUri)) {
                        this.circleimageview.setImageURI(Uri.parse(this.photoUri));
                    } else {
                        this.circleimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_callerid));
                    }
                } else {
                    String str2 = this.sg_number;
                    if (str2 == null) {
                        this.tv_name.setText("");
                        this.circleimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_callerid));
                    } else if (str2.equals("8888888888")) {
                        this.tv_name.setText("");
                        this.circleimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_callerid));
                    } else {
                        TextView textView = this.tv_name;
                        textView.setText(this.country_code + " " + str);
                        this.circleimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_callerid));
                    }
                }
            } else {
                TextView textView2 = this.tv_name;
                textView2.setText(this.country_code + " " + str);
            }
        }
    }

    public void CallMapMethod(String str) {
        if (Geocoder.isPresent()) {
            try {
                List<Address> fromLocationName = new Geocoder(this).getFromLocationName(str, 5);
                this.at_maplist = new ArrayList(fromLocationName.size());
                for (Address next : fromLocationName) {
                    if (next.hasLatitude() && next.hasLongitude() && next.hasLatitude() && next.hasLongitude()) {
                        LatLng latLng = new LatLng(next.getLatitude(), next.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        this.googlemap.clear();
                        this.googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.0f));
                        this.googlemap.addMarker(markerOptions);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googlemap = googleMap;
        if (!(this.googlemap == null || this.country_name == null)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    NumberSearchActivity numberSearch_Activity = NumberSearchActivity.this;
                    numberSearch_Activity.CallMapMethod(numberSearch_Activity.country_name);
                }
            }, 2000);
        }
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        }
        if ("Normal".equals(getString(R.string.normal))) {
            this.googlemap.setMapType(1);
        } else if ("Normal".equals(getString(R.string.hybrid))) {
            this.googlemap.setMapType(4);
        } else if ("Normal".equals(getString(R.string.satellite))) {
            this.googlemap.setMapType(2);
        } else if ("Normal".equals(getString(R.string.terrain))) {
            this.googlemap.setMapType(3);
        } else {
            Log.i("TAG", "Error setting layer with name " + "Normal");
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.pop_call) {
            try {
                if (this.sg_number != null) {
                    Intent intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse("tel:" + this.sg_number));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to Fetch Number", Toast.LENGTH_LONG).show();
                }
            } catch (Exception unused) {
            }
        }
        if (view.getId() == R.id.pop_save) {
            Intent intent2 = new Intent("android.intent.action.INSERT");
            intent2.setType("vnd.android.cursor.dir/contact");
            intent2.putExtra("name", this.sg_contactname);
            intent2.putExtra("phone", this.sg_number);
            startActivity(intent2);
            finish();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        ProgressDialog progressDialog = this.progressdialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.progressdialog.cancel();
        }
    }
}
