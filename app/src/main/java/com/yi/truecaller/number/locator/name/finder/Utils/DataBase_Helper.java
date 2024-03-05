package com.yi.truecaller.number.locator.name.finder.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class DataBase_Helper extends SQLiteOpenHelper {
    static Cursor cursor;
    private static DataBase_Helper database;
    private static SQLiteDatabase sqlite;
    protected Context context;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DataBase_Helper(Context context2, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context2, str, cursorFactory, i);
        this.context = context2;
    }

    private static void initialize(Context context2, String str) {
        if (database == null) {
            if (!checkDatabase(context2, str)) {
                try {
                    copyDataBase(context2, str);
                } catch (IOException unused) {
                    PrintStream printStream = System.out;
                    printStream.println(str + " does not exists ");
                }
            }
            DataBase_Helper dataBase_Helper = new DataBase_Helper(context2, str, (SQLiteDatabase.CursorFactory) null, 1);
            database = dataBase_Helper;
            sqlite = dataBase_Helper.getWritableDatabase();
            PrintStream printStream2 = System.out;
            printStream2.println("instance of  " + str + " created ");
        }
    }

    public static final DataBase_Helper getInstance(Context context2, String str) {
        initialize(context2, str);
        return database;
    }

    private static void copyDataBase(Context context2, String str) throws IOException {
        InputStream open = context2.getAssets().open(str);
        String databasePath = getDatabasePath(context2, str);
        File file = new File("/data/data/" + context2.getPackageName() + "/databases/");
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(databasePath);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                PrintStream printStream = System.out;
                printStream.println(str + " copied");
                return;
            }
        }
    }

    public static boolean checkDatabase(Context context2, String str) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = SQLiteDatabase.openDatabase(getDatabasePath(context2, str), (SQLiteDatabase.CursorFactory) null, SQLiteDatabase.OPEN_READONLY);
            sQLiteDatabase.close();
        } catch (SQLiteException unused) {
            PrintStream printStream = System.out;
            printStream.println(str + " does not exists");
        }
        if (sQLiteDatabase != null) {
            return true;
        }
        return false;
    }

    private static String getDatabasePath(Context context2, String str) {
        return "/data/data/" + context2.getPackageName() + "/databases/" + str;
    }

    public static Cursor rawQuery(String str) {
        try {
            if (sqlite.isOpen()) {
                sqlite.close();
            }
            SQLiteDatabase writableDatabase = database.getWritableDatabase();
            sqlite = writableDatabase;
            cursor = null;
            cursor = writableDatabase.rawQuery(str, (String[]) null);
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }
}
