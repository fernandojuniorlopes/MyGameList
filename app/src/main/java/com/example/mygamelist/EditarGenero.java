package com.example.mygamelist;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarGenero extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void ConfirmarGenero(View view) {
        EditText editTextNomeGenero = findViewById(R.id.textViewGenero);
        String NomeGenero = editTextNomeGenero.getText().toString();

        if (NomeGenero.trim().length() == 0) {
            editTextNomeGenero.setError(getString(R.string.genero_obrigatorio));
            editTextNomeGenero.requestFocus();
        } else
            editTextNomeGenero.setError(null);

        if (NomeGenero.trim().length() != 0) {
            finish();
            Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
        }
    }

    public void CancelarGenero(View view){
        finish();
    }

}
