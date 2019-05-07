package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogosPlataformas implements BaseColumns {

    private SQLiteDatabase db;

    public BdTableJogosPlataformas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}
