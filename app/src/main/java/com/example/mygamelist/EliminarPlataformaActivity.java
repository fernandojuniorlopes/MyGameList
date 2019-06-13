package com.example.mygamelist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EliminarPlataformaActivity extends AppCompatActivity {

    private Uri enderecoPlataformaEditar;
    Plataforma plataforma;
    private TextView TextviewPlataforma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_plataforma);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextviewPlataforma = findViewById(R.id.textViewPlataformaEliminar);
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
    public void Eliminar(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(EliminarPlataformaActivity.this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.titulo_aviso));
        builder.setMessage(getString(R.string.mensagem_aviso));


        builder.setNegativeButton(getString(R.string.botao_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton(R.string.botao_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContentResolver().delete(enderecoPlataformaEditar, null, null);
                Toast.makeText(EliminarPlataformaActivity.this, getString(R.string.plataforma_elimando_sucesso), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.show();
    }
    public void Cancelar(View view){
        finish();
    }
    }

