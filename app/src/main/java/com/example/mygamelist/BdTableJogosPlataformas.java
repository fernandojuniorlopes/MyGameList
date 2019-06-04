package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogosPlataformas implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "jogosplataformas";
    public static final String ID_PLATAFORMA = "id_plataformas";
    public static final String ID_JOGO = "id_jogo";
    public static final String [] TODAS_COLUNAS = new String[]{NOME_TABELA + "." +_ID, ID_JOGO, ID_PLATAFORMA};

    public BdTableJogosPlataformas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL( "CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_PLATAFORMA + " INTEGER NOT NULL," +
                ID_JOGO + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + ID_PLATAFORMA + ") REFERENCES " + BdTableGeneros.NOME_TABELA + "(" + BdTableGeneros._ID + ")," +
                "FOREIGN KEY (" + ID_JOGO + ") REFERENCES " + BdTableJogos.NOME_TABELA + "(" + BdTableJogos._ID + ")" +
                ")"
        );
    }
    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete (String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
