package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogosPlataformas implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "jogosplataformas";
    public static final String ID_PLATAFORMA = "id_plataformas";
    public static final String ID_JOGO = "id_jogo";

    public BdTableJogosPlataformas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL( "CREATE TABLE " + NOME_TABELA + "(" +
                ID_PLATAFORMA + " INTEGER NOT NULL," +
                ID_JOGO + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + ID_PLATAFORMA + ") REFERENCES " + BdTableGeneros.NOME_TABELA + "(" + BdTableGeneros._ID + ")," +
                "FOREIGN KEY (" + ID_JOGO + ") REFERENCES " + BdTableJogos.NOME_TABELA + "(" + BdTableJogos._ID + ")," +
                "PRIMARY KEY (" + ID_PLATAFORMA + "," + ID_JOGO + ")" +
                ")"
        );
    }
}
