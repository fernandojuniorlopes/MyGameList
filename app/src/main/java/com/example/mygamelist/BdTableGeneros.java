package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableGeneros implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "generos";
    public static final String NOME_GENERO = "nome";

    public BdTableGeneros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NOME_GENERO + " TEXT NOT NULL" +
                        ")"
        );
    }
}
