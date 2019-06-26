package com.example.mygamelist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EliminarJogoActivity extends AppCompatActivity {

    private Jogo jogo = null;
    private Uri enderecoJogo;
    private Uri enderecoGenero;
    private Uri enderecoPlataforma;
    private TextView textViewnomejogo;
    private TextView textViewnomeGenero;
    private TextView textViewPlataformas;
    private TextView textViewData;
    private TextView textViewAtividade;
    private ImageView imageViewFav;
    private Button botaoEliminar;
    private Button botaoCancelar;
    public static final String ID_JOGO = "ID_JOGO";
    public static final String LISTA_GEN = "LISTA_GEN";
    public static final String LISTA_PLAT = "LISTA_PLAT";
    private Cursor cursor2;
    private Cursor cursor3;
    private ImageView imagem;

    String NomeGeneros = "- ";
    JogoGenero jogosGeneros = null;
    String NomePlataformas = "- ";
    JogoPlataforma jogosPlataformas = null;

    long idJogo;
    ArrayList<Long> listaGeneros = new ArrayList<>();
    ArrayList<Long> listaPlataformas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewnomejogo = findViewById(R.id.textViewNomeJogo);
        textViewnomeGenero = findViewById(R.id.textViewGeneros);
        textViewPlataformas = findViewById(R.id.textViewPlataformas);
        textViewData = findViewById(R.id.textViewData);
        textViewAtividade = findViewById(R.id.textViewAtividade);
        botaoEliminar = findViewById(R.id.buttonEliminar);
        botaoCancelar = findViewById(R.id.buttonCancelar);
        imagem = findViewById(R.id.imageViewFotoJogo);
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

        BdMyGameListOpenHelper openHelper = new BdMyGameListOpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        BdTableJogosGeneros tabelaJogosGen = new BdTableJogosGeneros(db);

        cursor2 = tabelaJogosGen.query(BdTableJogosGeneros.TODAS_COLUNAS, null,null,null,null,null);

        while(cursor2.moveToNext()) {
            jogosGeneros = JogoGenero.fromCursor(cursor2);
            if (jogosGeneros.getId_jogo() == idJogo) {
                NomeGeneros += jogosGeneros.getNomeGenero();
                NomeGeneros += "; ";
            }
        }

        BdTableJogosPlataformas tabelaJogosPlataforma = new BdTableJogosPlataformas(db);

        cursor3 = tabelaJogosPlataforma.query(BdTableJogosPlataformas.TODAS_COLUNAS, null,null,null,null,null);

        JogoPlataforma jogoPlataforma = null;

        while(cursor3.moveToNext()) {
            jogoPlataforma = JogoPlataforma.fromCursor(cursor3);
            if (jogoPlataforma.getId_jogo() == idJogo) {
                NomePlataformas += jogoPlataforma.getNomePlataforma();
                NomePlataformas += "; ";
            }
        }

        textViewnomeGenero.setText(NomeGeneros);
        jogo = Jogo.fromCursor(cursor);

        textViewnomejogo.setText(jogo.getNome());
        textViewnomeGenero.setText(NomeGeneros);
        textViewAtividade.setText(jogo.getAtividade());
        textViewData.setText(jogo.getDataLancamento());
        textViewPlataformas.setText(NomePlataformas);

        byte[] imagemByte = jogo.getImagem();

        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemByte, 0, imagemByte.length);

        imagem.setImageBitmap(bitmap);
    }

    public void Eliminar(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(EliminarJogoActivity.this);
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
                getContentResolver().delete(enderecoJogo, null, null);

                while(cursor2.moveToNext()) {
                    jogosGeneros = JogoGenero.fromCursor(cursor2);
                    if (jogosGeneros.getId_jogo() == idJogo) {
                        enderecoGenero = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_GENEROS, String.valueOf(jogosGeneros.getId()));
                        getContentResolver().delete(enderecoGenero, null, null);
                    }
                }
                while(cursor3.moveToNext()) {
                    jogosPlataformas = JogoPlataforma.fromCursor(cursor3);
                    if (jogosPlataformas.getId_jogo() == idJogo) {
                        enderecoPlataforma = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_PLATAFORMAS, String.valueOf(jogosPlataformas.getId()));
                        getContentResolver().delete(enderecoPlataforma, null, null);
                    }
                }
                Toast.makeText(EliminarJogoActivity.this, getString(R.string.jogo_elimando_sucesso), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
            builder.show();
    }
    public void Cancelar(View view){
        finish();
    }
    }
