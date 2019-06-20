package com.example.mygamelist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class DetalhesJogoActivity extends AppCompatActivity {

    private Jogo jogo = null;
    private Uri enderecoJogo;
    private TextView textViewnomejogo;
    private TextView textViewnomeGenero;
    private TextView textViewPlataformas;
    private TextView textViewData;
    private TextView textViewAtividade;
    private Menu menu;
    public static final String ID_JOGO = "ID_JOGO";
    public static final String LISTA_GEN = "LISTA_GEN";
    public static final String LISTA_PLAT = "LISTA_PLAT";
    private ImageView imagem;

    long idJogo;
    ArrayList<Long> listaGeneros = new ArrayList<>();
    ArrayList<Long> listaPlataformas = new ArrayList<>();

    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewnomejogo = findViewById(R.id.textViewNomeJogo);
        textViewnomeGenero = findViewById(R.id.textViewGeneros);
        textViewPlataformas = findViewById(R.id.textViewPlataformas);
        textViewData = findViewById(R.id.textViewData);
        textViewAtividade = findViewById(R.id.textViewAtividade);
        imagem = findViewById(R.id.imageViewFotoJogo);

        Intent intent = getIntent();

        idJogo = intent.getLongExtra(JogosActivity.ID_JOGO, -1);

        if (idJogo == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o Jogo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoJogo = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_JOGOS, String.valueOf(idJogo));
        cursor = getContentResolver().query(enderecoJogo, BdTableJogos.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o Jogo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_det_jogo, menu);

        this.menu = menu;

        return true;
    }

    public void atualizaGenerosPlataformas(){
        String NomeGeneros = "- ";
        JogoGenero jogosGeneros = null;

        BdMyGameListOpenHelper openHelper = new BdMyGameListOpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        BdTableJogosGeneros tabelaJogosGen = new BdTableJogosGeneros(db);

        Cursor cursor2 = tabelaJogosGen.query(BdTableJogosGeneros.TODAS_COLUNAS, null,null,null,null,null);

        while(cursor2.moveToNext()) {
            jogosGeneros = JogoGenero.fromCursor(cursor2);
            if (jogosGeneros.getId_jogo() == idJogo) {
                NomeGeneros += jogosGeneros.getNomeGenero();
                listaGeneros.add(jogosGeneros.getId_genero());
                NomeGeneros+="; ";
            }
        }

        BdTableJogosPlataformas tabelaJogosPlataforma = new BdTableJogosPlataformas(db);

        Cursor cursor3 = tabelaJogosPlataforma.query(BdTableJogosPlataformas.TODAS_COLUNAS, null,null,null,null,null);

        String NomePlataformas = "- ";
        JogoPlataforma jogoPlataforma = null;

        while(cursor3.moveToNext()) {
            jogoPlataforma = JogoPlataforma.fromCursor(cursor3);
            if (jogoPlataforma.getId_jogo() == idJogo) {
                NomePlataformas += jogoPlataforma.getNomePlataforma();
                listaPlataformas.add(jogoPlataforma.getId_plataforma());
                NomePlataformas +="; ";
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

    @Override
    protected void onResume() {
        atualizaGenerosPlataformas();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.EditarJogo) {
            Intent intent = new Intent(this, EditarJogoActivity.class);
            intent.putExtra(ID_JOGO, idJogo);
            intent.putExtra(LISTA_GEN, listaGeneros);
            intent.putExtra(LISTA_PLAT, listaPlataformas);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.EditarJogo), Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.EliminarJogo) {
            Intent intent = new Intent(DetalhesJogoActivity.this, EliminarJogoActivity.class);
            intent.putExtra(ID_JOGO, idJogo);
            startActivity(intent);
            Toast.makeText(this, "Detalhes do Jogo", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

