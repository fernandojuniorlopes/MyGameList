package com.example.mygamelist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
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

public class JogosActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static  final int ID_CURSOR_LOADER_JOGOS =0;

    private RecyclerView recyclerViewJogos;
    private AdaptadorJogos adaptadorJogos = new AdaptadorJogos(this);
    private Menu menu;
    public static final String ID_JOGO = "ID_JOGO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewJogos = (RecyclerView) findViewById(R.id.recyclerViewJogos);
        recyclerViewJogos.setAdapter(adaptadorJogos);
        recyclerViewJogos.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_JOGOS, null, this);
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_JOGOS, null, this);
        super.onResume();
    }

    public void atualizaOpcoesMenu(){
        Jogo jogo = adaptadorJogos.getJogoSelecionado();

        boolean mostrarAlterarEliminar = (jogo != null);

        menu.findItem(R.id.DetalhesJogo).setVisible(mostrarAlterarEliminar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jogo, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.NovoJogo) {
            Intent intent = new Intent(this, NovoJogoActivity.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemadicionarnovojogo), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.EditarJogo) {
            Intent intent = new Intent(JogosActivity.this, EditarJogoActivity.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.EditarJogo), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.EliminarJogo) {
            Intent intent = new Intent(JogosActivity.this, EliminarJogoActivity.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemeliminarjogo), Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.DetalhesJogo) {
            Intent intent = new Intent(JogosActivity.this, DetalhesJogoActivity.class);
            intent.putExtra(ID_JOGO, adaptadorJogos.getJogoSelecionado().getId());
            startActivity(intent);
            Toast.makeText(this, "Detalhes do Jogo", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        CursorLoader cursorLoader = new CursorLoader(this, MyGamesListContentProvider.ENDERECO_JOGOS, BdTableJogos.TODAS_COLUNAS, null, null, BdTableJogos.CAMPO_NOME);

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
        adaptadorJogos.setCursor(data);
        Toast.makeText(this, "numero de jogos " + data.getCount(), Toast.LENGTH_SHORT).show();
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
        adaptadorJogos.setCursor(null);
    }
}
