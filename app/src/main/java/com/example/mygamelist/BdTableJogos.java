package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTableJogos implements BaseColumns {
    private SQLiteDatabase db;

    public static final String ALIAS_NOME_GENERO = "nome_gen";
    public static final String NOME_TABELA = "jogos";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_ATIVIDADE = "atividade";
    public static final String CAMPO_DATA_LANCAMENTO = "datalancamento";
    public static final String CAMPO_FAVORITO = "favorito";
    public static final String CAMPO_GENERO = "genero";
    public static final String CAMPO_NOME_GENERO = BdTableGeneros.NOME_TABELA + "." + BdTableGeneros.NOME_GENERO + " AS " + ALIAS_NOME_GENERO;

    public static final String [] TODAS_COLUNAS = new String[]{ NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_ATIVIDADE, CAMPO_DATA_LANCAMENTO, CAMPO_FAVORITO, CAMPO_NOME_GENERO};

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

        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " + NOME_TABELA +
                " JOIN " + BdTableJogosGeneros.NOME_TABELA + " ON " + BdTableJogos.NOME_TABELA + "." + BdTableJogos._ID +
                "=" + BdTableJogosGeneros.NOME_TABELA + "." + BdTableJogosGeneros.ID_JOGO +
                " JOIN " + BdTableGeneros.NOME_TABELA + " ON " + BdTableGeneros.NOME_TABELA + "."
                + BdTableGeneros._ID + "=" + BdTableJogosGeneros.NOME_TABELA + "." + BdTableJogosGeneros.ID_GENERO;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Jogos", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);
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
