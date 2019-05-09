package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;

public class JogosPlataformas {
    public long id_plataforma;
    public long id_jogo;

    public long getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(long id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public long getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(long id_jogo) {
        this.id_jogo = id_jogo;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableJogosPlataformas.ID_JOGO, id_jogo);
        valores.put(BdTableJogosPlataformas.ID_PLATAFORMA, id_plataforma);

        return valores;
    }

    public static JogosPlataformas fromCursor(Cursor cursor) {
        long id_jogo = cursor.getLong(
                cursor.getColumnIndex(BdTableJogosPlataformas.ID_JOGO)
        );

        long id_plataforma = cursor.getLong(
                cursor.getColumnIndex(BdTableJogosPlataformas.ID_PLATAFORMA)
        );

        JogosPlataformas jogoPlataforma = new JogosPlataformas();

        jogoPlataforma.setId_jogo(id_jogo);
        jogoPlataforma.setId_plataforma(id_plataforma);

        return jogoPlataforma;
    }
}
