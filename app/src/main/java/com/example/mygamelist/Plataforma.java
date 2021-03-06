package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;

public class Plataforma {

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

        valores.put(BdTablePlataformas.NOME_PLATAFORMA, Nome);

        return valores;
    }

    public static Plataforma fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTablePlataformas._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTablePlataformas.NOME_PLATAFORMA)
        );

        Plataforma plataforma = new Plataforma();

        plataforma.setId(id);
        plataforma.setNome(nome);

        return plataforma;
    }
}
