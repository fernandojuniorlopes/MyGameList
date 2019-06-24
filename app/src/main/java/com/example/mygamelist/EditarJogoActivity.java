package com.example.mygamelist;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class EditarJogoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static  final int ID_CURSOR_LOADER_GENEROS =0;
    private static  final int ID_CURSOR_LOADER_PLATAFORMAS =1;

    long id = 0;
    long idJogo;

    ArrayList<Long> listaGeneros = new ArrayList<>();
    ArrayList<Long> listaPlataformas = new ArrayList<>();

    private Uri enderecoJogo;
    private Jogo jogo = null;

    private EditText textViewnomejogo;
    private Spinner spinnerJogado;
    private CheckBox checkBoxFavoritos;
    private Button selectDate;
    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    private RecyclerView recyclerViewGeneros;
    private AdaptadorGeneros adaptadorGeneros = new AdaptadorGeneros(this);
    private RecyclerView recyclerViewPlataformas;
    private AdaptadorPlataformas adaptadorPlataformas = new AdaptadorPlataformas(this);

    Cursor cursor2;
    Cursor cursor3;

    public static final String LISTA_GENERO = "LISTA_GENERO";
    public static final String LISTA_PLATAFORMA = "LISTA_PLATAFORMA";

    private ImageView imagem;
    private Button botaoImagem;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectDate = findViewById(R.id.buttonData);
        date = findViewById(R.id.textViewdataLanc);

        selectDate.setOnClickListener(new View.OnClickListener() {
                                          /**
                                           * Called when a view has been clicked.
                                           *
                                           * @param v The view that was clicked.
                                           */
                                          @Override
                                          public void onClick(View v) {
                                              calendar = Calendar.getInstance();
                                              year = calendar.get(Calendar.YEAR);
                                              month = calendar.get(Calendar.MONTH);
                                              dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                              datePickerDialog = new DatePickerDialog(EditarJogoActivity.this,
                                                      new DatePickerDialog.OnDateSetListener() {
                                                          @Override
                                                          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                              date.setText(dayOfMonth + "/" + (month + 1) + "/" +year);

                                                          }
                                                      }, year, month, dayOfMonth);
                                                datePickerDialog.show();
                                          }
                                      });

        textViewnomejogo = (EditText) findViewById(R.id.textViewnomejogo);
        spinnerJogado = (Spinner) findViewById(R.id.spinnerJogado);
        checkBoxFavoritos = (CheckBox) findViewById(R.id.checkBoxFavoritos);

        imagem = findViewById(R.id.imageViewFoto);
        botaoImagem = findViewById(R.id.buttonProcurarImagem);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewGeneros = (RecyclerView) findViewById(R.id.recyclerViewGeneros);
        recyclerViewGeneros.setLayoutManager(layoutManager);
        recyclerViewGeneros.setAdapter(adaptadorGeneros);

        recyclerViewPlataformas = (RecyclerView) findViewById(R.id.recyclerViewPlataformas);
        recyclerViewPlataformas.setLayoutManager(layoutManager2);
        recyclerViewPlataformas.setAdapter(adaptadorPlataformas);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_GENEROS, null, this);
        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_PLATAFORMAS, null, this);

        Intent intent = getIntent();

        idJogo = intent.getLongExtra(DetalhesJogoActivity.ID_JOGO, -1);
        listaGeneros = (ArrayList<Long>)intent.getSerializableExtra(DetalhesJogoActivity.LISTA_GEN);
        listaPlataformas = (ArrayList<Long>)intent.getSerializableExtra(DetalhesJogoActivity.LISTA_PLAT);

        Intent intent1 = new Intent(this, AdaptadorGeneros.class);
        intent1.putExtra(LISTA_GENERO, listaGeneros);

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
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTableJogosGeneros tabelaJogosGen = new BdTableJogosGeneros(db);

        cursor2 = tabelaJogosGen.query(BdTableJogosGeneros.TODAS_COLUNAS, null,null,null,null,null);

        BdTableJogosPlataformas tabelaJogosPlataforma = new BdTableJogosPlataformas(db);

        cursor3 = tabelaJogosPlataforma.query(BdTableJogosPlataformas.TODAS_COLUNAS, null,null,null,null,null);

        jogo = Jogo.fromCursor(cursor);

        int atividade = 0;
        String atividadeString = jogo.getAtividade();

        if (atividadeString.equals("Não jogado")) {
            atividade = 1;
        } else if (atividadeString.equals("A jogar")) {
            atividade = 2;
        } else {
            atividade = 3;
        }

        textViewnomejogo.setText(jogo.getNome());
        spinnerJogado.setSelection(atividade);
        date.setText(jogo.getDataLancamento());

        if(jogo.getFavorito()==1){
            checkBoxFavoritos.setChecked(true);
        }

        byte[] imagemByte = jogo.getImagem();

        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemByte, 0, imagemByte.length);

        imagem.setImageBitmap(bitmap);

        botaoImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        EditarJogoActivity.this,
                        new String []{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }


    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_GENEROS, null, this);
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_PLATAFORMAS, null, this);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.Settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(), "You don't have permisson", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imagem.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void ConfirmarJogo(View view) {

        TextView errorJogado = (TextView) spinnerJogado.getSelectedView();

        String NomeJogo = textViewnomejogo.getText().toString();
        String data = date.getText().toString();

        int Jogado = spinnerJogado.getSelectedItemPosition();

        boolean flag = true;
        int favoritos = 0;
        String atividade;

        if (checkBoxFavoritos.isChecked()) {
            favoritos = 1;
        }

        if (NomeJogo.trim().length() == 0) {
            textViewnomejogo.setError(getString(R.string.nome_obrigatorio));
            textViewnomejogo.requestFocus();
            flag = false;
        } else
            textViewnomejogo.setError(null);

        if (data.trim().length() == 0) {
            date.setError("Data é obrigatória");
            date.requestFocus();
            flag = false;
        } else
            date.setError(null);

        if (Jogado == 0) {
            tratarErros(errorJogado, (getString(R.string.jogados_obrigatorio)));
            flag = false;
        } else
            errorJogado.setError(null);

        if (Jogado == 1) {
            atividade = "Não jogado";
        } else if (Jogado == 2) {
            atividade = "A jogar";
        } else {
            atividade = "Completado";
        }

        Jogo jogo = new Jogo();

        jogo.setNome(NomeJogo);
        jogo.setAtividade(atividade);
        jogo.setFavorito(favoritos);
        jogo.setDataLancamento(data);
        if(imagem != null) {
            jogo.setImagem(imageViewToByte(imagem));
        }else{
            jogo.setImagem(null);
        }

        long id = -1;

        if(flag) {
            try {
                getContentResolver().update(enderecoJogo, jogo.getContentValues(), null,null);
                Toast.makeText(this, "Jogo guardado com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Snackbar.make(
                        textViewnomejogo,
                        "Erro a guardar Jogo",
                        Snackbar.LENGTH_LONG)
                        .show();

                e.printStackTrace();
            }
        }

        ArrayList<Long> lista;
        lista = adaptadorGeneros.lista();
        long idgeneros;
        for(int i=0; i<listaGeneros.size();i++){
           Long idvelho=listaGeneros.get(i);
           for(int j=0;j<lista.size();j++){
               if(idvelho.compareTo(lista.get(j))==0){
                   listaGeneros.remove(idvelho);
                   lista.remove(j);
               }
           }
        }

        JogoGenero jogosGeneros = null;
        while(cursor2.moveToNext()) {
            jogosGeneros = JogoGenero.fromCursor(cursor2);
            if (jogosGeneros.getId_jogo() == idJogo) {
                for(int i=0;i<listaGeneros.size();i++){
                    if(jogosGeneros.getId_genero() == listaGeneros.get(i)){
                        long idJogoApagar = jogosGeneros.getId();
                        Uri endereçoJogoGenApagar = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_JOGOS_GENEROS, String.valueOf(idJogoApagar));
                        getContentResolver().delete(endereçoJogoGenApagar, null,null);
                    }
                }
            }
        }

        if (lista.size()==0) {
            lista.addAll(listaGeneros);
        }
            for (int i = 0; i < lista.size(); i++) {
                JogoGenero jogoGeneros = new JogoGenero();
                idgeneros = lista.get(i);
                jogoGeneros.setId_jogo(idJogo);
                jogoGeneros.setId_genero(idgeneros);

                getContentResolver().insert(MyGamesListContentProvider.ENDERECO_JOGOS_GENEROS, jogoGeneros.getContentValues());
            }

        ArrayList<Long> lista2 = adaptadorPlataformas.lista();
        long idPlataforma;
        for(int i=0; i<listaPlataformas.size();i++){
            Long idvelho=listaPlataformas.get(i);
            for(int j=0;j<lista2.size();j++){
                if(idvelho.compareTo(lista2.get(j))==0){
                    listaPlataformas.remove(idvelho);
                    lista2.remove(j);
                }
            }
        }


        JogoPlataforma jogosPlataformas = null;
        while(cursor3.moveToNext()) {
            jogosPlataformas = JogoPlataforma.fromCursor(cursor3);
            if (jogosPlataformas.getId_jogo() == idJogo) {
                for(int i=0;i<listaPlataformas.size();i++){
                    if(jogosPlataformas.getId_plataforma() == listaPlataformas.get(i)){
                        long idJogoPlataformaApagar = jogosPlataformas.getId();
                        Uri endereçoJogoPlataformaApagar = Uri.withAppendedPath(MyGamesListContentProvider.ENDERECO_JOGOS_PLATAFORMAS, String.valueOf(idJogoPlataformaApagar));
                        getContentResolver().delete(endereçoJogoPlataformaApagar, null,null);
                    }
                }
            }
        }

        if (lista2.size()==0) {
            lista2.addAll(listaPlataformas);
        }
            for (int i = 0; i < lista2.size(); i++) {
                JogoPlataforma jogoPlataforma = new JogoPlataforma();
                idPlataforma = lista2.get(i);
                jogoPlataforma.setId_jogo(idJogo);
                jogoPlataforma.setId_plataforma(idPlataforma);

                getContentResolver().insert(MyGamesListContentProvider.ENDERECO_JOGOS_PLATAFORMAS, jogoPlataforma.getContentValues());
            }


        if(flag){
            finish();

        }
    }

    public void CancelarJogo(View view){
        finish();
    }

    public void tratarErros(TextView textView, String string){
        textView.setError(string);
        textView.setTextColor(getResources().getColor(R.color.colorRed));
        textView.setText(string);
    }
    public void ErroData(TextView textView, String string){
        textView.setTextColor(getResources().getColor(R.color.colorRed));
        textView.setText(string);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id){
            case ID_CURSOR_LOADER_GENEROS:
                CursorLoader cursorLoader = new CursorLoader(this, MyGamesListContentProvider.ENDERECO_GENEROS, BdTableGeneros.TODAS_COLUNAS, null, null, BdTableGeneros.NOME_GENERO);
                return cursorLoader;
            case ID_CURSOR_LOADER_PLATAFORMAS:
                CursorLoader cursorLoader2 = new CursorLoader(this, MyGamesListContentProvider.ENDERECO_PLATAFORMAS, BdTablePlataformas.TODAS_COLUNAS, null, null, BdTablePlataformas.NOME_PLATAFORMA);
                return cursorLoader2;
            default:
                return null;
        }

    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(loader.getId()==ID_CURSOR_LOADER_GENEROS){
            adaptadorGeneros.setCursor(data);
        }else{
            adaptadorPlataformas.setCursor(data);
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(loader.getId()==ID_CURSOR_LOADER_GENEROS){
            adaptadorGeneros.setCursor(null);
        }else{
            adaptadorPlataformas.setCursor(null);
        }
    }
}