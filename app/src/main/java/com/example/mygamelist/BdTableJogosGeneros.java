package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTableJogosGeneros implements BaseColumns {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "jogosgeneros";
    public static final String ID_GENERO = "id_genero";
    public static final String ID_JOGO = "id_jogo";
    public static final String ALIAS_NOME_GENERO = "nomeGenero";
    public static final String CAMPO_NOME_GENERO = BdTableGeneros.NOME_TABELA + "." + BdTableGeneros.NOME_GENERO + " AS " + ALIAS_NOME_GENERO; // tabela de Generos (só de leitura)
    public static final String [] TODAS_COLUNAS = new String[]{NOME_TABELA + "." +_ID, ID_JOGO, ID_GENERO, CAMPO_NOME_GENERO};

    public BdTableJogosGeneros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
       db.execSQL( "CREATE TABLE " + NOME_TABELA + "(" +
               _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
               ID_GENERO + " INTEGER NOT NULL," +
               ID_JOGO + " INTEGER NOT NULL," +
               " FOREIGN KEY (" + ID_GENERO + ") REFERENCES " + BdTableGeneros.NOME_TABELA + "(" + BdTableGeneros._ID + ")," +
               " FOREIGN KEY (" + ID_JOGO + ") REFERENCES " + BdTableJogos.NOME_TABELA + "(" + BdTableJogos._ID + ")" +
               ")"
        );
    }
    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA + " INNER JOIN " + BdTableGeneros.NOME_TABELA + " WHERE " + BdTableJogosGeneros.NOME_TABELA + "." + BdTableJogosGeneros.ID_GENERO + "=" + BdTableGeneros.NOME_TABELA + "." + BdTableGeneros._ID
                ;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela jogosgeneros", "query: " + sql);

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
