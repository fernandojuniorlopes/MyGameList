package com.example.mygamelist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Jogos extends AppCompatActivity {


   /* public Jogos(int id_jogo, String nome_jogo, String atividade_jogo, String data_Lancamento) {
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

    private String Data_Lancamento;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jogo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.NovoJogo) {
            Intent intent = new Intent(Jogos.this, NovoJogo.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemadicionarnovojogo), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.EditarJogo) {
            Intent intent = new Intent(Jogos.this, EditarJogo.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.EditarJogo), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.EliminarJogo) {
            Intent intent = new Intent(Jogos.this, EliminarJogo.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemeliminarjogo), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
