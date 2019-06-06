package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;

public class JogoGenero {

    private long Id;
    private long id_genero;
    private long id_jogo;
    private String nomeGenero; // Campo "externo"

    public String getNomeGenero() {
        return nomeGenero;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getId_genero() {
        return id_genero;
    }

    public void setId_genero(long id_genero) {
        this.id_genero = id_genero;
    }

    public long getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(long id_jogo) {
        this.id_jogo = id_jogo;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableJogosGeneros.ID_JOGO, id_jogo);
        valores.put(BdTableJogosGeneros.ID_GENERO, id_genero);

        return valores;
    }

    public static JogoGenero fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableJogosGeneros._ID)
        );

        long id_jogo = cursor.getLong(
                cursor.getColumnIndex(BdTableJogosGeneros.ID_JOGO)
        );

        long id_genero = cursor.getLong(
                cursor.getColumnIndex(BdTableJogosGeneros.ID_GENERO)
        );

        String nomeGenero = cursor.getString(
                cursor.getColumnIndex(BdTableJogosGeneros.ALIAS_NOME_GENERO)
        );


        JogoGenero jogoGenero = new JogoGenero();

        jogoGenero.setId(id);
        jogoGenero.setId_jogo(id_jogo);
        jogoGenero.setId_genero(id_genero);
        jogoGenero.nomeGenero = nomeGenero;

        return jogoGenero;
    }
}
