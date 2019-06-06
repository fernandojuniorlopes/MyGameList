package com.example.mygamelist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.snackbar.Snackbar;

import static java.lang.Integer.valueOf;


public class DetalhesJogoActivity extends AppCompatActivity {

    private Jogo jogo = null;
    private Uri enderecoJogo;
    private Uri enderecoJogoGen;
    private EditText textViewnomejogo;
    private EditText textViewnomeGenero;

    public static final String NOME_TABELA = "BdTableJogosGeneros";

    long idJogo;
    JogoGenero jogoGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewnomejogo = (EditText) findViewById(R.id.textViewnomejogo);
        textViewnomeGenero = (EditText) findViewById(R.id.textViewnomeGenero);

        Intent intent = getIntent();

        idJogo = intent.getLongExtra(JogosActivity.ID_JOGO, -1);

        if (idJogo == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoJogo = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_JOGOS, String.valueOf(idJogo));
        enderecoJogoGen = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_JOGOS_GENEROS, String.valueOf(idJogo));
        Cursor cursor = getContentResolver().query(enderecoJogo, BdTableJogos.TODAS_COLUNAS, null, null, null);
        Cursor cursor2 = getContentResolver().query(enderecoJogoGen, BdTableJogosGeneros.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        String NomeGeneros = "Adeus";

        JogoGenero jogosGeneros = null;

        while(cursor2.moveToNext()){
            jogosGeneros = JogoGenero.fromCursor(cursor2);
            if(jogosGeneros.getId_jogo() == idJogo){
                NomeGeneros = jogosGeneros.getNomeGenero();
            }
        }
        textViewnomeGenero.setText(NomeGeneros);
        jogo = Jogo.fromCursor(cursor);

        textViewnomejogo.setText(jogo.getNome());

    }
}

