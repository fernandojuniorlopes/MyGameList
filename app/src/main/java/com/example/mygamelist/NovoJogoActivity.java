package com.example.mygamelist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class NovoJogoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static  final int ID_CURSOR_LOADER_GENEROS =0;
    private static  final int ID_CURSOR_LOADER_PLATAFORMAS =1;
    long id = 0;

    private EditText textViewnomejogo;
    private Spinner spinnerJogado;
    private CheckBox checkBoxFavoritos;

    private ImageView imagem;
    private Button botaoImagem;
    final int REQUEST_CODE_GALLERY = 999;
    private byte imageInByte[];

    private Button selectDate;
    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    int validacaoData=0;


    private RecyclerView recyclerViewGeneros;
    private AdaptadorGenerosJogos adaptadorGeneros = new AdaptadorGenerosJogos(this);
    private RecyclerView recyclerViewPlataformas;
    private AdaptadorPlataformasJogos adaptadorPlataformas = new AdaptadorPlataformasJogos(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectDate = findViewById(R.id.buttonData);
        date = findViewById(R.id.textViewDataLancamento);

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
                datePickerDialog = new DatePickerDialog(NovoJogoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (month + 1) + "/" +year);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
                validacaoData=1;
            }
        });

        textViewnomejogo = (EditText) findViewById(R.id.textViewnomejogo);
        spinnerJogado = (Spinner) findViewById(R.id.spinnerJogado);
        checkBoxFavoritos = (CheckBox) findViewById(R.id.checkBoxFavoritos);

        imagem = findViewById(R.id.imageViewFoto);

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
                Toast.makeText(getApplicationContext(), getString(R.string.sem_permicao), Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            imagem.setImageURI(selectedImage);
            convertToByte();
        }
    }

    public void getImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void convertToByte() {
        Bitmap image = ((BitmapDrawable)imagem.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        imageInByte = stream.toByteArray();
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

        if (validacaoData==0) {
            date.setError(getString(R.string.data_obrigatoria));
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
            atividade = getString(R.string.nao_jogado);
        } else if (Jogado == 2) {
            atividade = getString(R.string.a_jogar);
        } else {
            atividade = getString(R.string.completado);
        }


        long id = -1;

        if(flag) {

            Jogo jogo = new Jogo();

            jogo.setNome(NomeJogo);
            jogo.setAtividade(atividade);
            jogo.setFavorito(favoritos);
            jogo.setDataLancamento(data);

            convertToByte();
            jogo.setImagem(imageInByte);

            try {
                Uri uri = getContentResolver().insert(MyGamesListContentProvider.ENDERECO_JOGOS, jogo.getContentValues());
                id = Long.valueOf(uri.getLastPathSegment());
                Toast.makeText(this, getString(R.string.jogo_sucesso), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Snackbar.make(
                        textViewnomejogo,
                        getString(R.string.error_guardar),
                        Snackbar.LENGTH_LONG)
                        .show();

                e.printStackTrace();
            }
        }

        ArrayList<Long> lista;
        lista = adaptadorGeneros.lista();
        long idgeneros;

        if (lista.size() != 0) {
            for (int i = 0; i < lista.size(); i++) {
                JogoGenero jogoGeneros = new JogoGenero();
                idgeneros = lista.get(i);
                jogoGeneros.setId_jogo(id);
                jogoGeneros.setId_genero(idgeneros);

                getContentResolver().insert(MyGamesListContentProvider.ENDERECO_JOGOS_GENEROS, jogoGeneros.getContentValues());
            }
        }

        ArrayList<Long> lista2 = adaptadorPlataformas.lista();
        long idPlataformas;

        if (lista2.size() != 0) {
            for (int i = 0; i < lista2.size(); i++) {
                JogoPlataforma jogoPlataformas = new JogoPlataforma();
                idPlataformas = lista2.get(i);
                jogoPlataformas.setId_jogo(id);
                jogoPlataformas.setId_plataforma(idPlataformas);

                getContentResolver().insert(MyGamesListContentProvider.ENDERECO_JOGOS_PLATAFORMAS, jogoPlataformas.getContentValues());
            }
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
