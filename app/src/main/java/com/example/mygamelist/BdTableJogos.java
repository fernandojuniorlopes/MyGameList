package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogos implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "jogos";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_ATIVIDADE = "atividade";
    public static final String CAMPO_DATA_LANCAMENTO = "datalancamento";
    public static final String CAMPO_FAVORITO = "favorito";

    public BdTableJogos(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_ATIVIDADE + " TEXT NOT NULL," +
                         CAMPO_DATA_LANCAMENTO + " TEXT NOT NULL," +
                         CAMPO_FAVORITO + " TEXT" +
                        ")"
        );
    }
}
