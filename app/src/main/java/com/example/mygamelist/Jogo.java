package com.example.mygamelist;

import android.content.ContentValues;
import android.database.Cursor;

import java.sql.Blob;

public class Jogo {

    private long Id;
    private String Nome;
    private String Atividade;
    private String DataLancamento;
    private int favorito;
    private String nomeGenero;
    private byte[] imagem;

    public String getNomeGenero() {
        return nomeGenero;
    }

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

    public byte[] getImagem(){
         return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableJogos.CAMPO_NOME, Nome);
        valores.put(BdTableJogos.CAMPO_ATIVIDADE, Atividade);
        valores.put(BdTableJogos.CAMPO_DATA_LANCAMENTO, DataLancamento);
        valores.put(BdTableJogos.CAMPO_FAVORITO, favorito);
        valores.put(BdTableJogos.CAMPO_IMAGEM, imagem);

        return valores;
    }

    public static Jogo fromCursor(Cursor cursor) {
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

        byte[] imagem = cursor.getBlob(
                cursor.getColumnIndex(BdTableJogos.CAMPO_IMAGEM)
        );
        //String nomeGenero = cursor.getString(
          //      cursor.getColumnIndex(BdTableJogos.ALIAS_NOME_GENERO));


        Jogo jogo = new Jogo();

        jogo.setId(id);
        jogo.setNome(nome);
        jogo.setAtividade(atividade);
        jogo.setDataLancamento(data_lancamento);
        jogo.setFavorito(favorito);
        jogo.setImagem(imagem);
        //jogo.nomeGenero = nomeGenero;

        return jogo;
    }
}
