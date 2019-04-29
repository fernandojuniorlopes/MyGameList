package com.example.mygamelist;

public class AppJogos {
    public AppJogos(int id_jogo, String nome_jogo, String atividade_jogo, String data_Lancamento) {
        Id_jogo = id_jogo;
        Nome_jogo = nome_jogo;
        Atividade_jogo = atividade_jogo;
        Data_Lancamento = data_Lancamento;
    }

    private int Id_jogo;
    private String Nome_jogo;
    private String Atividade_jogo;

    public int getId_jogo() {
        return Id_jogo;
    }

    public void setId_jogo(int id_jogo) {
        Id_jogo = id_jogo;
    }

    public String getNome_jogo() {
        return Nome_jogo;
    }

    public void setNome_jogo(String nome_jogo) {
        Nome_jogo = nome_jogo;
    }

    public String getAtividade_jogo() {
        return Atividade_jogo;
    }

    public void setAtividade_jogo(String atividade_jogo) {
        Atividade_jogo = atividade_jogo;
    }

    public String getData_Lancamento() {
        return Data_Lancamento;
    }

    public void setData_Lancamento(String data_Lancamento) {
        Data_Lancamento = data_Lancamento;
    }

    private String Data_Lancamento;
}
