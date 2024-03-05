package com.yi.truecaller.number.locator.name.finder.Adaptor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class BankDataBaseAdapter {
    private BankMyDatabaseAdapter bankMyDatabaseAdapter;
    protected Context context;
    protected String sg_result;
    protected String sg_tbl = "tbl_bank";
    private SQLiteDatabase sqLiteDatabase;

    public BankDataBaseAdapter(Context context2) {
        this.context = context2;
        this.bankMyDatabaseAdapter = new BankMyDatabaseAdapter(context2);
    }

    public BankDataBaseAdapter createDatabase() {
        try {
            this.bankMyDatabaseAdapter.createDataBase();
            return this;
        } catch (IOException e) {
            Log.e("error", e.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
    }

    public Cursor get_all() throws Exception {
        try {
            String str = "select  *  from " + this.sg_tbl;
            this.sg_result = str;
            Cursor rawQuery = this.sqLiteDatabase.rawQuery(str, (String[]) null);
            if (rawQuery != null) {
                rawQuery.moveToNext();
            }
            return rawQuery;
        } catch (Exception e) {
            Log.e("TAG", "getTestData >>" + e.toString());
            throw e;
        }
    }

    public BankDataBaseAdapter open() throws Exception {
        try {
            this.bankMyDatabaseAdapter.openDataBase();
            this.bankMyDatabaseAdapter.close();
            this.sqLiteDatabase = this.bankMyDatabaseAdapter.getReadableDatabase();
            return this;
        } catch (Exception e) {
            Log.e("error", "open >>" + e.toString());
            throw e;
        }
    }
}
