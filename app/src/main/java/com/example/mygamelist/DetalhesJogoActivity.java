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

import java.util.ArrayList;

import static java.lang.Integer.valueOf;


public class DetalhesJogoActivity extends AppCompatActivity {

    private Jogo jogo = null;
    private Uri enderecoJogo;
    private TextView textViewnomejogo;
    private TextView textViewnomeGenero;
    private TextView textViewPlataformas;
    private TextView textViewData;
    private TextView textViewAtividade;

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

        textViewnomejogo = findViewById(R.id.textViewNomeJogo);
        textViewnomeGenero = findViewById(R.id.textViewGeneros);
        textViewPlataformas = findViewById(R.id.textViewPlataformas);
        textViewData = findViewById(R.id.textViewData);
        textViewAtividade = findViewById(R.id.textViewAtividade);

        Intent intent = getIntent();

        idJogo = intent.getLongExtra(JogosActivity.ID_JOGO, -1);

        if (idJogo == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o Jogo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoJogo = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_JOGOS, String.valueOf(idJogo));
        Cursor cursor = getContentResolver().query(enderecoJogo, BdTableJogos.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o Jogo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        String NomeGeneros = "";
        JogoGenero jogosGeneros = null;

        BdMyGameListOpenHelper openHelper = new BdMyGameListOpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        BdTableJogosGeneros tabelaJogosGen = new BdTableJogosGeneros(db);

        Cursor cursor2 = tabelaJogosGen.query(BdTableJogosGeneros.TODAS_COLUNAS, null,null,null,null,null);

        while(cursor2.moveToNext()) {
            jogosGeneros = JogoGenero.fromCursor(cursor2);
            if (jogosGeneros.getId_jogo() == idJogo) {
                NomeGeneros += "-";
                NomeGeneros += jogosGeneros.getNomeGenero();
            }
        }
        BdMyGameListOpenHelper openHelper2 = new BdMyGameListOpenHelper(this);
        SQLiteDatabase db2 = openHelper2.getReadableDatabase();
        BdTableJogosPlataformas tabelaJogosPlataforma = new BdTableJogosPlataformas(db);

        Cursor cursor3 = tabelaJogosPlataforma.query(BdTableJogosPlataformas.TODAS_COLUNAS, null,null,null,null,null);

        String NomePlataformas = "";
        JogoPlataforma jogoPlataforma = null;

        while(cursor3.moveToNext()) {
            jogoPlataforma = JogoPlataforma.fromCursor(cursor3);
            if (jogoPlataforma.getId_jogo() == idJogo) {
                NomePlataformas += "-";
                NomePlataformas += jogoPlataforma.getNomePlataforma();
            }
        }

        textViewnomeGenero.setText(NomeGeneros);
        jogo = Jogo.fromCursor(cursor);

        textViewnomejogo.setText(jogo.getNome());
        textViewnomeGenero.setText(NomeGeneros);
        textViewAtividade.setText(jogo.getAtividade());
        textViewData.setText(jogo.getDataLancamento());
        textViewPlataformas.setText(NomePlataformas);


    }
}

