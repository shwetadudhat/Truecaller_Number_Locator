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

import com.yi.truecaller.number.locator.name.finder.Adaptor.ISDCodeAdapter;
import com.yi.truecaller.number.locator.name.finder.Base.BaseActivity;
import com.yi.truecaller.number.locator.name.finder.Constructor.ISDCode_Constructor;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.DataBase_Helper;

import java.util.ArrayList;
import java.util.Locale;

public class ISDCodeActivity extends BaseActivity {

    private static final String DB_NAME = "contactlistdatabase";
    ArrayList<ISDCode_Constructor> arraylist = new ArrayList<>();
    DataBase_Helper databasehelper;
    ISDCodeAdapter isd_adapter;
    ListView lv_list;
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.isdcode_activity);
//        showToolBar(true, "ISD Codes");

        TextView textView = findViewById(R.id.actionbar_title);
        textView.setText("ISD codes");

        ImageView backBtn = findViewById(R.id.ic_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        databasehelper = DataBase_Helper.getInstance(this, DB_NAME);
        lv_list = (ListView) findViewById(R.id.list);
        lv_list.setDivider((Drawable) null);
        showISDcodes();
        EditText et_search = (EditText) findViewById(R.id.isd_search);
        et_search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                isd_adapter.filter(et_search.getText().toString().toLowerCase(Locale.getDefault()));
            }
        });
    }

    private void showISDcodes()
    {
        arraylist.clear();
        Cursor rawQuery = DataBase_Helper.rawQuery("SELECT * FROM isdcodes");
        if (rawQuery == null || rawQuery.getCount() == 0 || !rawQuery.moveToFirst()) {
            isd_adapter = new ISDCodeAdapter(this, arraylist);
            lv_list.setAdapter(isd_adapter);
        }
        do {
            ISDCode_Constructor iSDCode_Constructor = new ISDCode_Constructor();
            iSDCode_Constructor.setEmpId(rawQuery.getString(rawQuery.getColumnIndex("isdcode")));
            iSDCode_Constructor.setFirstName(rawQuery.getString(rawQuery.getColumnIndex("country")));
            arraylist.add(iSDCode_Constructor);
        } while (rawQuery.moveToNext());

        isd_adapter = new ISDCodeAdapter(this, arraylist);
        lv_list.setAdapter(isd_adapter);
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
