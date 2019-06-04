package com.example.mygamelist;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovaPlataformaActivity extends AppCompatActivity {

    private EditText textViewnomePlataforma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_plataforma);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewnomePlataforma = (EditText) findViewById(R.id.textViewPlataforma);


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
            getContentResolver().insert(MyGamesListContentProvider.ENDERECO_PLATAFORMAS, plataforma.getContentValues());

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
