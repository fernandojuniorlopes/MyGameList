package com.example.mygamelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdJogosTest {
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdMyGameListOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdJogos() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdMyGameListOpenHelper openHelper = new BdMyGameListOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext()  {
        return InstrumentationRegistry.getTargetContext();
    }
    @Test
    public void testCRUD() {
        BdMyGameListOpenHelper openHelper = new BdMyGameListOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableGeneros tabelaGeneros = new BdTableGeneros(db);

        Generos genero = new Generos();
        genero.setNome("Estratégia");
        long idRPG = tabelaGeneros.insert(genero.getContentValues());
        assertNotEquals(-1, idRPG);

        BdTablePlataformas tabelaPlataformas = new BdTablePlataformas(db);

        Plataformas plataforma = new Plataformas();
        plataforma.setNome("PS4");
        long idPS4 = tabelaPlataformas.insert(plataforma.getContentValues());
        assertNotEquals(-1, idPS4);

        BdTableJogos tabelaJogos = new BdTableJogos(db);

        Jogos jogo = new Jogos();
        jogo.setNome("Tetris");
        jogo.setAtividade("Completado");
        jogo.setDataLancamento("20-01-1990");
        jogo.setFavorito(1);
        long idTetris = tabelaJogos.insert(jogo.getContentValues());
        assertNotEquals(-1, idTetris);

       BdTableJogosPlataformas tabelaJogosPlatormas = new BdTableJogosPlataformas(db);

        JogosPlataformas jogosPlataformas = new JogosPlataformas();
        jogosPlataformas.setId_plataforma(plataforma.getId());
        jogosPlataformas.setId_jogo(jogo.getId());
        long idTetrisPS4 = tabelaJogosPlatormas.insert(jogosPlataformas.getContentValues());
        assertNotEquals(-1, idTetrisPS4);


        BdTableJogosGeneros tabelaJogosGeneros = new BdTableJogosGeneros(db);

        JogosGeneros jogosGeneros = new JogosGeneros();
        jogosGeneros.setId_genero(genero.getId());
        jogosGeneros.setId_jogo(jogo.getId());
        long idTetrisEstrategia = tabelaJogosGeneros.insert(jogosGeneros.getContentValues());
        assertNotEquals(-1, idTetrisEstrategia);

    }
}
