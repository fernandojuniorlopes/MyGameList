package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTablePlataformas implements BaseColumns {

    private SQLiteDatabase db;

    public BdTablePlataformas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}
