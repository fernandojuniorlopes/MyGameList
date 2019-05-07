package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogosGeneros implements BaseColumns {

    private SQLiteDatabase db;

    public BdTableJogosGeneros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}
