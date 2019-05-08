package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;

public class Generos {
    public long Id;
    public String Nome;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableGeneros.NOME_GENERO, Nome);

        return valores;
    }

    public static Generos fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableGeneros._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableGeneros.NOME_GENERO)
        );

        Generos genero = new Generos();

        genero.setId(id);
        genero.setNome(nome);

        return genero;
    }
}
