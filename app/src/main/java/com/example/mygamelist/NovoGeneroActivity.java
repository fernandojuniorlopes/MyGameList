package com.example.mygamelist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NovoGeneroActivity extends AppCompatActivity {

    private EditText textViewnomeGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewnomeGenero = (EditText) findViewById(R.id.textViewPlataformaItem);

    }
    public void ConfirmarGenero(View view) {
        EditText editTextNomeGenero = findViewById(R.id.textViewPlataformaItem);
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

        Genero genero = new Genero();

        genero.setNome(NomeGenero);

        try {
            getContentResolver().insert(MyGamesListContentProvider.ENDERECO_GENEROS, genero.getContentValues());

            Toast.makeText(this, "Jogo guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeGenero,
                    "Erro a guardar Jogo",
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    public void CancelarGenero(View view){
        finish();
    }

}


