package com.example.mygamelist;

import android.database.Cursor;

public class JogosGeneros {
    public long id_genero;
    public long id_jogo;

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
    public static JogosGeneros fromCursor(Cursor cursor) {
        long id_jogo = cursor.getLong(
                cursor.getColumnIndex(BdTableJogos._ID)
        );

        long id_genero = cursor.getLong(
                cursor.getColumnIndex(BdTableGeneros._ID)
        );

        JogosGeneros jogoGenero = new JogosGeneros();

        jogoGenero.setId_jogo(id_jogo);
        jogoGenero.setId_genero(id_genero);

        return jogoGenero;
    }
}
