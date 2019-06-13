package com.example.mygamelist;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class EditarGeneroActivity extends AppCompatActivity {

    private Uri enderecoGeneroEditar;
    Genero genero;
    private EditText TextviewGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextviewGenero = findViewById(R.id.textViewGeneroItem);
        Intent intent = getIntent();

        long idGenero = intent.getLongExtra(GenerosActivity.ID_GENERO, -1);

        if (idGenero == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoGeneroEditar = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_GENEROS, String.valueOf(idGenero));

        Cursor cursor = getContentResolver().query(enderecoGeneroEditar, BdTableGeneros.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        genero = genero.fromCursor(cursor);

        TextviewGenero.setText(genero.getNome());

    }

    public void ConfirmarGenero(View view) {
        EditText editTextNomeGenero = findViewById(R.id.textViewGeneroItem);
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
            getContentResolver().update(enderecoGeneroEditar, genero.getContentValues(), null, null);

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
