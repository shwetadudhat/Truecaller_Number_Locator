package com.yi.truecaller.number.locator.name.finder.Adaptor;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BankMyDatabaseAdapter extends SQLiteOpenHelper {
    private static String TAG = "MyDatabaseHelper";
    private static String database_name = "bank_checker.db";
    private static String database_path = "";
    private final Context context;
    private SQLiteDatabase sqLiteDatabase;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public BankMyDatabaseAdapter(Context context2) {
        super(context2, database_name, (SQLiteDatabase.CursorFactory) null, 1);
        database_path = "/data/data/" + context2.getPackageName() + "/databases/";
        this.context = context2;
    }

    private boolean checkDataBase() {
        return new File(database_path + database_name).exists();
    }

    private void copyDataBase() throws IOException {
        InputStream open = this.context.getAssets().open(database_name);
        FileOutputStream fileOutputStream = new FileOutputStream(database_path + database_name);
        byte[] bArr = new byte[63];
        while (true) {
            int read = open.read(bArr);
            if (read <= 0) {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
            fileOutputStream.write(bArr, 0, read);
        }
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.sqLiteDatabase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        super.close();
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            getReadableDatabase();
            close();
        }
        try {
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
        } catch (IOException unused) {
            throw new Error("ErrorCopyingDataBase");
        }
    }

    public boolean openDataBase() throws SQLException {
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(database_path + database_name, (SQLiteDatabase.CursorFactory) null, 268435456);
        this.sqLiteDatabase = openDatabase;
        return openDatabase != null;
    }
}
