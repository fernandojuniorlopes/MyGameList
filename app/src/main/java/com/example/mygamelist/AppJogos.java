package com.example.mygamelist;

public class AppJogos {

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
}
