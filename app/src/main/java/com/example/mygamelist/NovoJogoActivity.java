package com.example.mygamelist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
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

public class NovoJogoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static  final int ID_CURSOR_LOADER_GENEROS =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_GENEROS, null, this);

    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_GENEROS, null, this);
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
        } else if (id == R.id.EditarJogo) {
            return true;
        } else if (id == R.id.EliminarJogo) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ConfirmarJogo(View view){

        EditText editTextnomejogo = findViewById(R.id.textViewnomejogo);

        Spinner spinnerJogado = findViewById(R.id.spinnerJogado);
        Spinner spinnerDia = findViewById(R.id.spinnerDia);
        Spinner spinnerMes = findViewById(R.id.spinnerMes);
        Spinner spinnerAno = findViewById(R.id.spinnerAno);
        Spinner spinnerGenero = findViewById(R.id.spinnerGenero);
        Spinner spinnerPlataforma = findViewById(R.id.spinnerPlataforma);

        CheckBox checkBoxFavoritos = findViewById(R.id.checkBoxFavoritos);

        TextView errorJogado = (TextView)spinnerJogado.getSelectedView();
        TextView errorDia = (TextView)spinnerDia.getSelectedView();
        TextView errorMes = (TextView)spinnerMes.getSelectedView();
        TextView errorAno = (TextView)spinnerAno.getSelectedView();
        TextView errorGenero = (TextView)spinnerGenero.getSelectedView();
        TextView errorPlataforma = (TextView)spinnerPlataforma.getSelectedView();

        String NomeJogo = editTextnomejogo.getText().toString();

        int Jogado = spinnerJogado.getSelectedItemPosition();
        int Dia = spinnerDia.getSelectedItemPosition();
        int Mes = spinnerMes.getSelectedItemPosition();
        int Ano = spinnerAno.getSelectedItemPosition();
        int Genero = spinnerGenero.getSelectedItemPosition();
        int Plataforma = spinnerPlataforma.getSelectedItemPosition();

        boolean flag = true;
        int erro = 0;
        int favoritos=0;
        String atividade;
        String data;

        if(checkBoxFavoritos.isChecked()){
            favoritos = 1;
        }

        if (NomeJogo.trim().length() == 0) {
            editTextnomejogo.setError(getString(R.string.nome_obrigatorio));
            editTextnomejogo.requestFocus();
            flag = false;
        }else
            editTextnomejogo.setError(null);

        if (Jogado == 0) {
            tratarErros(errorJogado, (getString(R.string.jogados_obrigatorio)));
            flag = false;
        }else
            errorJogado.setError(null);

        if (Dia == 0) {
            ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
            flag = false;
        }else
            errorDia.setError(null);

        if (Mes == 0) {
            ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
            flag = false;
        }else
            errorMes.setError(null);

        if (Ano == 0) {
            ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
            flag = false;
        }else
            errorAno.setError(null);

        if (Genero == 0) {
            tratarErros(errorGenero, getString(R.string.genero_obrigatorio));
            flag = false;
        }else
            errorGenero.setError(null);

        if (Plataforma == 0) {
            tratarErros(errorPlataforma, getString(R.string.plataforma_obrigatorio));
            flag = false;
        }else
            errorPlataforma.setError(null);

        if((Mes != 0)&&(Dia!=0)&&(Ano!=0)) {
            switch (Mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (Dia > 31) {
                        erro = 1;
                        flag = false;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (Dia > 30) {
                        erro = 1;
                        flag = false;
                    }
                    break;
                case 2:
                    if (Ano % 4 == 0) {
                        if (Dia > 29) {
                            erro = 1;
                            flag = false;
                        }
                    } else {
                        if (Dia > 28) {
                            erro = 1;
                            flag = false;
                        }
                        break;
                    }
            }

        }else{
            erro=1;
        }

        if(erro==1){
            ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
            ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
            ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
        }

        if(flag){
            finish();
            Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
        }

        if(Jogado == 1){
            atividade = "Não jogado";
        }else if(Jogado == 2){
            atividade = "A jogar";
        }else{
            atividade = "Completado";
        }

        data = Dia + "/" + Mes + "/" + Ano;

        Jogos jogo = new Jogos();

        jogo.setNome(NomeJogo);
        jogo.setAtividade(atividade);
        jogo.setFavorito(favoritos);
        jogo.setDataLancamento(data);
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
        CursorLoader cursorLoader = new CursorLoader(this, MyGamesListContentProvider.ENDERECO_GENEROS, BdTableGeneros.TODAS_COLUNAS, null, null, BdTableGeneros.NOME_GENERO);

        return cursorLoader;
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

    }
}
