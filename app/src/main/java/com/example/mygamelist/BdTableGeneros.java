package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableGeneros implements BaseColumns {

    private SQLiteDatabase db;

    public BdTableGeneros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}
