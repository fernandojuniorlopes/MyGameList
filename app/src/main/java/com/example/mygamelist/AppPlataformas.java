package com.example.mygamelist;

public class AppPlataformas {
    private int ID_plataforma;
    private String Nome_plataforma;

    public int getID_plataforma() {
        return ID_plataforma;
    }

    public void setID_plataforma(int ID_plataforma) {
        this.ID_plataforma = ID_plataforma;
    }

    public String getNome_plataforma() {
        return Nome_plataforma;
    }

    public void setNome_plataforma(String nome_plataforma) {
        Nome_plataforma = nome_plataforma;
    }

    public AppPlataformas(int ID_plataforma, String nome_plataforma) {
        this.ID_plataforma = ID_plataforma;
        Nome_plataforma = nome_plataforma;
    }

}
