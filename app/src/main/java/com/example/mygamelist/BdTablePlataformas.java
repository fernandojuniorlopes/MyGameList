package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTablePlataformas implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "plataformas";
    public static final String NOME_PLATAFORMA = "nome";

    public BdTablePlataformas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NOME_PLATAFORMA + " TEXT NOT NULL" +
                        ")"
        );
    }
}
