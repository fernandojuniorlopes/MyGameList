package com.example.mygamelist;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarPlataformaActivity extends AppCompatActivity {

    private Uri enderecoPlataformaEditar;
    Plataforma plataforma;
    private EditText TextviewPlataforma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_plataforma);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextviewPlataforma = findViewById(R.id.textViewPlataforma);
        Intent intent = getIntent();

        long idPlataforma = intent.getLongExtra(PlataformasActivity.ID_PLATAFORMA, -1);

        if (idPlataforma == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoPlataformaEditar = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_PLATAFORMAS, String.valueOf(idPlataforma));

        Cursor cursor = getContentResolver().query(enderecoPlataformaEditar, BdTablePlataformas.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        plataforma = plataforma.fromCursor(cursor);

        TextviewPlataforma.setText(plataforma.getNome());
    }

    public void ConfirmarPlataforma(View view) {
        EditText editTextnomeplataforma = findViewById(R.id.textViewPlataforma);
        String NomePlataforma = editTextnomeplataforma.getText().toString();

        if (NomePlataforma.trim().length() == 0) {
            editTextnomeplataforma.setError(getString(R.string.plataforma_obrigatorio));
            editTextnomeplataforma.requestFocus();
        } else
            editTextnomeplataforma.setError(null);

        if (NomePlataforma.trim().length() != 0) {
            finish();
            Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
        }

        Plataforma plataforma = new Plataforma();

        plataforma.setNome(NomePlataforma);

        try {
            getContentResolver().update(enderecoPlataformaEditar, plataforma.getContentValues(),null ,null );

            Toast.makeText(this, "Jogo guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextnomeplataforma,
                    "Erro a guardar Jogo",
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    public void CancelarPlataforma(View view){
        finish();
    }

}

