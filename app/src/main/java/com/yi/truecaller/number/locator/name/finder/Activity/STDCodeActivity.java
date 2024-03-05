package com.yi.truecaller.number.locator.name.finder.Activity;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yi.truecaller.number.locator.name.finder.Adaptor.STDCodeAdapter;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.Constructor.STDCode_Constructor;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.DataBase_Helper;

import java.util.ArrayList;
import java.util.Locale;

public class STDCodeActivity extends BaseActivity
{
    private static final String DB_NAME = "contactlistdatabase";
    ArrayList<STDCode_Constructor> arraylist = new ArrayList<>();
    DataBase_Helper databasehelper;
    EditText et_search;
    ListView lv_list;
    STDCodeAdapter std_adapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.stdcode_activity);
//        showToolBar(true, "STD Codes");
        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("STD codes");

        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        databasehelper = DataBase_Helper.getInstance(getApplicationContext(), DB_NAME);
        lv_list = (ListView) findViewById(R.id.area_list);
        lv_list.setDivider((Drawable) null);
        showSTDcodes();
        et_search = findViewById(R.id.area_search);
        et_search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                std_adapter.filter(et_search.getText().toString().toLowerCase(Locale.getDefault()));
            }
        });
    }

    private void showSTDcodes() {
        ArrayList<STDCode_Constructor> arrayList = new ArrayList<>();
        this.arraylist = arrayList;
        arrayList.clear();
        Cursor rawQuery = DataBase_Helper.rawQuery("SELECT * FROM stdcodes");
        if (rawQuery == null || rawQuery.getCount() == 0 || !rawQuery.moveToFirst()) {
            STDCodeAdapter sTDCode_Adapter = new STDCodeAdapter(this, this.arraylist);
            this.std_adapter = sTDCode_Adapter;
            this.lv_list.setAdapter(sTDCode_Adapter);
        }
        do {
            STDCode_Constructor sTDCode_Constructor = new STDCode_Constructor();
            sTDCode_Constructor.setAreacode(rawQuery.getString(rawQuery.getColumnIndex("stdcode")));
            sTDCode_Constructor.setAreaname(rawQuery.getString(rawQuery.getColumnIndex("city")));
            this.arraylist.add(sTDCode_Constructor);
        } while (rawQuery.moveToNext());
        STDCodeAdapter sTDCode_Adapter2 = new STDCodeAdapter(this, this.arraylist);
        this.std_adapter = sTDCode_Adapter2;
        this.lv_list.setAdapter(sTDCode_Adapter2);
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
