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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Plataformas extends AppCompatActivity {

    /*private int ID_plataforma;
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

    public Plataformas(int ID_plataforma, String nome_plataforma) {
        this.ID_plataforma = ID_plataforma;
        Nome_plataforma = nome_plataforma;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plataformas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plataforma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.AdicionarPlataformas) {
            Intent intent = new Intent(Plataformas.this, NovaPlataforma.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemadicionarnovojogo), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.EditarPlataforma) {
            Intent intent = new Intent(Plataformas.this, EditarPlataforma.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.EditarJogo), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.EliminarPlataforma) {
            Intent intent = new Intent(Plataformas.this, EditarPlataforma.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemeliminarjogo), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
