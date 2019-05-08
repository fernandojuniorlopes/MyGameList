package com.example.mygamelist;

import android.database.Cursor;

public class Jogos {

    private long Id;
    private String Nome;
    private String Atividade;
    private String DataLancamento;
    private int favorito;

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

    public String getAtividade() {
        return Atividade;
    }

    public void setAtividade(String atividade) {
        Atividade = atividade;
    }

    public String getDataLancamento() {
        return DataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        DataLancamento = dataLancamento;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public static Jogos fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableJogos._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableJogos.CAMPO_NOME)
        );

        String atividade = cursor.getString(
                cursor.getColumnIndex(BdTableJogos.CAMPO_ATIVIDADE)
        );

        String data_lancamento = cursor.getString(
                cursor.getColumnIndex(BdTableJogos.CAMPO_DATA_LANCAMENTO)
        );

        int favorito = cursor.getInt(
                cursor.getColumnIndex(BdTableJogos.CAMPO_FAVORITO)
        );


        Jogos jogo = new Jogos();

        jogo.setId(id);
        jogo.setNome(nome);
        jogo.setAtividade(atividade);
        jogo.setDataLancamento(data_lancamento);
        jogo.setFavorito(favorito);

        return jogo;
    }
}
