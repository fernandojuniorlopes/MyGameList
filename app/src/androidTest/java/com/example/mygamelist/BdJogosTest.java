package com.example.mygamelist;

import android.content.Context;
import android.database.Cursor;
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

        //Teste create /insert /update Generos

        BdTableGeneros tabelaGeneros = new BdTableGeneros(db);

        Cursor cursorGenero = getGenero(tabelaGeneros);
        assertEquals(0,cursorGenero.getCount());

        String nomeGenero = "Estratégia";
        long idEstrategia = criaGenero(tabelaGeneros,nomeGenero);

        cursorGenero = getGenero(tabelaGeneros);
        assertEquals(1, cursorGenero.getCount());

        assertTrue(cursorGenero.moveToNext());
        Generos genero = Generos.fromCursor(cursorGenero);
        assertEquals(idEstrategia, genero.getId());
        assertEquals(nomeGenero,genero.getNome());

        nomeGenero ="FPS";
        long idFPS = criaGenero(tabelaGeneros, nomeGenero);

        cursorGenero = getGenero(tabelaGeneros);
        assertEquals(2,cursorGenero.getCount());

        genero = getGeneroComID(cursorGenero, idFPS);
        assertEquals(nomeGenero, genero.getNome());

        nomeGenero= "Strategy";
        genero.setNome(nomeGenero);
        tabelaGeneros.update(genero.getContentValues(), BdTableGeneros._ID + "=?", new String[] {String.valueOf(idEstrategia)});
        cursorGenero = getGenero(tabelaGeneros);
        genero = getGeneroComID(cursorGenero, idEstrategia);
        assertEquals(nomeGenero, genero.getNome());


        //Teste create /insert /update Generos

        BdTablePlataformas tabelaplataforma = new BdTablePlataformas(db);

        Cursor cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(0,cursorPlataforma.getCount());

        String nomePlataforma = "PS4";
        long idPlataforma = criaPlataforma(tabelaplataforma, nomePlataforma);
        cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(1, cursorPlataforma.getCount());

        assertTrue(cursorPlataforma.moveToNext());
        Plataformas plataforma = Plataformas.fromCursor(cursorPlataforma);
        assertEquals(plataforma.getId(), idPlataforma);
        assertEquals(plataforma.getNome(), nomePlataforma);

        nomePlataforma= "Playstation 4";
        plataforma.setNome(nomePlataforma);
        tabelaplataforma.update(plataforma.getContentValues(), BdTablePlataformas._ID + "=?", new String[] {String.valueOf(idPlataforma)});
        cursorPlataforma = getPlataforma(tabelaplataforma);
        plataforma = getPlataformacomID(cursorPlataforma, idPlataforma);
        assertEquals(plataforma.getNome(), nomePlataforma);

        nomePlataforma ="PS3";
        idPlataforma = criaPlataforma(tabelaplataforma, nomePlataforma);

        cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(2,cursorPlataforma.getCount());

        plataforma = getPlataformacomID(cursorPlataforma, idPlataforma);
        assertEquals(nomePlataforma, plataforma.getNome());

        //Teste create /insert /update Jogos

        BdTableJogos tabelaJogo = new BdTableJogos(db);

        Cursor cursorJogo = getJogo(tabelaJogo);
        assertEquals(0, cursorJogo.getCount());

        String nomeJogo = "Tetris";
        String atividade = "Completado";
        String datalancamento = "01-01-2001";
        int favorito = 1;

        long idJogo = criaJogo(tabelaJogo,nomeJogo, atividade, datalancamento, favorito);
        cursorJogo = getJogo(tabelaJogo);
        assertEquals(1, cursorJogo.getCount());

        cursorJogo.moveToNext();
        Jogos jogo = Jogos.fromCursor(cursorJogo);
        assertEquals(jogo.getId(), idJogo);
        assertEquals(jogo.getNome(), nomeJogo);
        assertEquals(jogo.getAtividade(), atividade);
        assertEquals(jogo.getDataLancamento(), datalancamento);
        assertEquals(jogo.getFavorito(), favorito);

        nomeJogo = "Tetris2";
        atividade = "Não jogado";
        datalancamento ="01-01-2011";
        favorito = 0;
        jogo.setNome(nomeJogo);
        jogo.setAtividade(atividade);
        jogo.setDataLancamento(datalancamento);
        jogo.setFavorito(favorito);
        tabelaJogo.update(jogo.getContentValues(), BdTableJogos._ID + "=?", new String[] {String.valueOf(idJogo)});

        cursorJogo = getJogo(tabelaJogo);
        jogo = getJogosComID(cursorJogo, idJogo);
        assertEquals(jogo.getId(), idJogo);
        assertEquals(jogo.getNome(), nomeJogo);
        assertEquals(jogo.getAtividade(), atividade);
        assertEquals(jogo.getDataLancamento(), datalancamento);
        assertEquals(jogo.getFavorito(), favorito);

        //Test read / insert / update jogosGeneros
        
        BdTableJogosGeneros tabelaJogosGeneros = new BdTableJogosGeneros(db);

        Cursor cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        assertEquals(0, cursorJogoGenero.getCount());

        long idJogoGenero = criaJogoGenero(tabelaJogosGeneros, idJogo, idEstrategia);
        cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        assertEquals(1, cursorJogoGenero.getCount());

        cursorJogoGenero.moveToNext();
        JogosGeneros jogosGenero = JogosGeneros.fromCursor(cursorJogoGenero);
        jogosGenero.setId_genero(idFPS);
        tabelaJogosGeneros.update(jogosGenero.getContentValues(), BdTableJogosGeneros.ID_JOGO + "=?", new String[] {String.valueOf(idJogo)});

        cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        jogosGenero = getJogoGeneroComID(cursorJogoGenero, idJogo, idFPS);
        assertEquals(jogosGenero.getId_jogo(), idJogo);
        assertEquals(jogosGenero.getId_genero(), idFPS);

        //Test read/insert/update jogosPlataformas

        BdTableJogosPlataformas tabelaJogosPlataformas = new BdTableJogosPlataformas(db);

        Cursor cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        assertEquals(0, cursorJogosPlataformas.getCount());

        long idJogoPlataformas = criaJogoPlataformas(tabelaJogosPlataformas, idJogo, idPlataforma);
        cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        assertEquals(1, cursorJogosPlataformas.getCount());

        cursorJogosPlataformas.moveToNext();
        JogosPlataformas jogosPlataformas = JogosPlataformas.fromCursor(cursorJogosPlataformas);
        jogosPlataformas.setId_plataforma(idJogoPlataformas);
        tabelaJogosPlataformas.update(jogosPlataformas.getContentValues(), BdTableJogosPlataformas.ID_JOGO + "=?", new String[] {String.valueOf(idJogo)});

        cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        jogosPlataformas = getJogoPlataformaComID(cursorJogosPlataformas, idJogo, idJogoPlataformas);
        assertEquals(jogosPlataformas.getId_jogo(), idJogo);
        assertEquals(jogosPlataformas.getId_plataforma(), idJogoPlataformas);

    }
    private Cursor getGenero(BdTableGeneros tabelaGeneros) {
        return tabelaGeneros.query(BdTableGeneros.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Generos getGeneroComID(Cursor cursor, long id){
        Generos genero = null;

        while(cursor.moveToNext()){
            genero = Generos.fromCursor(cursor);
            if(genero.getId() == id){
                break;
            }
        }
        assertNotNull(genero);
        return genero;
    }
    private long criaGenero(BdTableGeneros tabelaGeneros, String nomeGenero) {
        Generos genero = new Generos();
        genero.setNome(nomeGenero);

        long id = tabelaGeneros.insert(genero.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }
    private Cursor getPlataforma(BdTablePlataformas tabelaPlataformas) {
        return tabelaPlataformas.query(BdTablePlataformas.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Plataformas getPlataformacomID(Cursor cursor, long id){
        Plataformas plataforma = null;

        while(cursor.moveToNext()){
            plataforma = Plataformas.fromCursor(cursor);
            if(plataforma.getId() == id){
                break;
            }
        }
        assertNotNull(plataforma);
        return plataforma;
    }
    private long criaPlataforma(BdTablePlataformas tablePlataformas, String nomePlataforma) {
        Plataformas plataforma = new Plataformas();
        plataforma.setNome(nomePlataforma);

        long id = tablePlataformas.insert(plataforma.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getJogo(BdTableJogos tabelaJogos) {
        return tabelaJogos.query(BdTableJogos.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Jogos getJogosComID(Cursor cursor, long id){
        Jogos jogo = null;

        while(cursor.moveToNext()){
            jogo = Jogos.fromCursor(cursor);
            if(jogo.getId() == id){
                break;
            }
        }
        assertNotNull(jogo);
        return jogo;
    }
    private long criaJogo(BdTableJogos tableJogos, String nomeJogo, String atividade, String dataLancamento, int favorito) {
        Jogos jogo = new Jogos();
        jogo.setNome(nomeJogo);
        jogo.setAtividade(atividade);
        jogo.setDataLancamento(dataLancamento);
        jogo.setFavorito(favorito);

        long id = tableJogos.insert(jogo.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }
    private Cursor getJogoGenero(BdTableJogosGeneros tabelaJogoGenero) {
        return tabelaJogoGenero.query(BdTableJogosGeneros.TODAS_COLUNAS, null, null, null, null, null);
    }

    private JogosGeneros getJogoGeneroComID(Cursor cursor, long id_jogo, long id_genero){
        JogosGeneros jogosGeneros = null;

        while(cursor.moveToNext()){
            jogosGeneros = JogosGeneros.fromCursor(cursor);
            if((jogosGeneros.getId_jogo() == id_jogo)&&(jogosGeneros.getId_genero() == id_genero)){
                break;
            }
        }
        assertNotNull(jogosGeneros);
        return jogosGeneros;
    }
    private long criaJogoGenero(BdTableJogosGeneros tableJogosGeneros,long id_jogo, long id_genero) {
        JogosGeneros jogosGeneros = new JogosGeneros();
        jogosGeneros.setId_jogo(id_jogo);
        jogosGeneros.setId_genero(id_genero);

        long id = tableJogosGeneros.insert(jogosGeneros.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }
    private Cursor getJogoPlataforma(BdTableJogosPlataformas tabelaJogosPlataformas) {
        return tabelaJogosPlataformas.query(BdTableJogosPlataformas.TODAS_COLUNAS, null, null, null, null, null);
    }

    private JogosPlataformas getJogoPlataformaComID(Cursor cursor, long id_jogo, long id_plataforma){
        JogosPlataformas jogosPlataformas = null;

        while(cursor.moveToNext()){
            jogosPlataformas = jogosPlataformas.fromCursor(cursor);
            if((jogosPlataformas.getId_jogo() == id_jogo)&&(jogosPlataformas.getId_plataforma() == id_plataforma)){
                break;
            }
        }
        assertNotNull(jogosPlataformas);
        return jogosPlataformas;
    }
    private long criaJogoPlataformas(BdTableJogosPlataformas tableJogosPlataformas,long id_jogo, long id_plataformas) {
        JogosPlataformas jogosPlataformas = new JogosPlataformas();
        jogosPlataformas.setId_jogo(id_jogo);
        jogosPlataformas.setId_plataforma(id_plataformas);

        long id = tableJogosPlataformas.insert(jogosPlataformas.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }
}
