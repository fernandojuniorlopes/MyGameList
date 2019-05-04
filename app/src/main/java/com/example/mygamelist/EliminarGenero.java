package com.example.mygamelist;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class EliminarGenero extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void Eliminar(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(EliminarGenero.this);
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
                Toast.makeText(EliminarGenero.this, getString(R.string.genero_elimando_sucesso), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.show();
    }
    public void Cancelar(View view){
        finish();
    }
}