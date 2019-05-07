package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogos implements BaseColumns {

    private SQLiteDatabase db;

    public BdTableJogos(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}
