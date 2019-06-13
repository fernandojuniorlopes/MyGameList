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
import android.widget.TextView;
import android.widget.Toast;

public class EliminarGeneroActivity extends AppCompatActivity {

    private Uri enderecoGeneroEditar;
    Genero genero;
    private TextView TextviewGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextviewGenero = (TextView)findViewById(R.id.textViewPlataformaEliminar);
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

    public void Eliminar(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(EliminarGeneroActivity.this);
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
                getContentResolver().delete(enderecoGeneroEditar, null, null);
                Toast.makeText(EliminarGeneroActivity.this, getString(R.string.genero_elimando_sucesso), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.show();
    }
    public void Cancelar(View view){
        finish();
    }
}
