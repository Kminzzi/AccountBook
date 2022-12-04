package com.example.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
    private Context context;
    private static final String DATABASE_NAME = "Account.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "account_user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CARD = "card";
    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_CONTENT = "content";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_CARD + " TEXT, "
                + COLUMN_CLASS + " TEXT, "
                + COLUMN_AMOUNT + " TEXT, "
                + COLUMN_CONTENT + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(Date date, String card, String classfication, int amount, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, String.valueOf(date));
        cv.put(COLUMN_CARD, card);
        cv.put(COLUMN_CLASS, classfication);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_CONTENT, content);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
        }
    }

}
