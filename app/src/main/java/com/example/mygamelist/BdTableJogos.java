package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableJogos implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "jogos";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_ATIVIDADE = "atividade";
    public static final String CAMPO_DATA_LANCAMENTO = "datalancamento";
    public static final String CAMPO_FAVORITO = "favorito";
    public static final String [] TODAS_COLUNAS = new String[]{_ID, CAMPO_NOME, CAMPO_ATIVIDADE, CAMPO_DATA_LANCAMENTO, CAMPO_FAVORITO};

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
