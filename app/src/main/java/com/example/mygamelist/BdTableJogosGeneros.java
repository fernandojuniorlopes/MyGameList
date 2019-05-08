package com.example.mygamelist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogosGeneros implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "jogosgeneros";
    public static final String ID_GENERO = "id_genero";
    public static final String ID_JOGO = "id_jogo";

    public BdTableJogosGeneros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL( "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID +" INTEGER PRIMARY KEY (BdTableJogos._ID, BdTableGeneros._ID)" +
                        " FOREIGN KEY (" + ID_GENERO + ") REFERENCES, " + BdTableGeneros._ID +
                        " FOREIGN KEY (" + ID_JOGO + ") REFERENCES, " + BdTableJogos._ID +
                ")"
        );
    }
}
