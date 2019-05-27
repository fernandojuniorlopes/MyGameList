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


       //CREATE Tabela Generos
        BdTableGeneros tabelaGeneros = new BdTableGeneros(db);

        Cursor cursorGenero = getGenero(tabelaGeneros);
        assertEquals(0,cursorGenero.getCount());

        //INSERT nos Generos
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

        //UPDATE nos Generos
        nomeGenero= "Strategy";
        genero.setNome(nomeGenero);
        tabelaGeneros.update(genero.getContentValues(), BdTableGeneros._ID + "=?", new String[] {String.valueOf(idEstrategia)});
        cursorGenero = getGenero(tabelaGeneros);
        genero = getGeneroComID(cursorGenero, idEstrategia);
        assertEquals(nomeGenero, genero.getNome());

        nomeGenero="RPG";
        long idRPG = criaGenero(tabelaGeneros, nomeGenero);
        cursorGenero = getGenero(tabelaGeneros);
        assertEquals(3, cursorGenero.getCount());

        //DELETE nos Generos
        genero = getGeneroComID(cursorGenero, idRPG);
        assertEquals(nomeGenero, genero.getNome());
        tabelaGeneros.delete(BdTableGeneros._ID + "=?", new String[]{String.valueOf(idRPG)});
        cursorGenero=getGenero(tabelaGeneros);
        assertEquals(2, cursorGenero.getCount());

        //CREATE Tabela Plataformas
        BdTablePlataformas tabelaplataforma = new BdTablePlataformas(db);

        Cursor cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(0,cursorPlataforma.getCount());

        //INSERT Tabela Plataformas
        String nomePlataforma = "PS4";
        long idPS4 = criaPlataforma(tabelaplataforma, nomePlataforma);
        cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(1, cursorPlataforma.getCount());

        assertTrue(cursorPlataforma.moveToNext());
        Plataformas plataforma = Plataformas.fromCursor(cursorPlataforma);
        assertEquals(plataforma.getId(), idPS4);
        assertEquals(plataforma.getNome(), nomePlataforma);

        nomePlataforma ="PS3";
        long idPS3 = criaPlataforma(tabelaplataforma, nomePlataforma);

        cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(2,cursorPlataforma.getCount());

        assertTrue(cursorPlataforma.moveToNext());
        plataforma = getPlataformacomID(cursorPlataforma, idPS3);
        assertEquals(nomePlataforma, plataforma.getNome());

        nomePlataforma ="Xbox 1";
        long idXbox1 = criaPlataforma(tabelaplataforma, nomePlataforma);

        cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(3,cursorPlataforma.getCount());

        assertTrue(cursorPlataforma.moveToNext());
        plataforma = getPlataformacomID(cursorPlataforma, idXbox1);
        assertEquals(nomePlataforma, plataforma.getNome());

        //UPDATE Tabela Plataformas
        nomePlataforma= "Playstation 4";
        plataforma.setNome(nomePlataforma);
        tabelaplataforma.update(plataforma.getContentValues(), BdTablePlataformas._ID + "=?", new String[] {String.valueOf(idPS4)});
        cursorPlataforma = getPlataforma(tabelaplataforma);
        plataforma = getPlataformacomID(cursorPlataforma, idPS4);
        assertEquals(plataforma.getNome(), nomePlataforma);

        //DELETE Tabela Plataformas
        tabelaplataforma.delete(BdTablePlataformas._ID + "=?", new String[] {String.valueOf(idXbox1)});
        cursorPlataforma = getPlataforma(tabelaplataforma);
        assertEquals(2,cursorPlataforma.getCount());

        //CREATE Tabela Jogos
        BdTableJogos tabelaJogo = new BdTableJogos(db);

        Cursor cursorJogo = getJogo(tabelaJogo);
        assertEquals(0, cursorJogo.getCount());

        //INSERT Tabela Jogos
        String nomeJogo = "Tetris";
        String atividade = "Completado";
        String datalancamento = "01-01-2001";
        int favorito = 1;

        long idTetris = criaJogo(tabelaJogo,nomeJogo, atividade, datalancamento, favorito);
        cursorJogo = getJogo(tabelaJogo);
        assertEquals(1, cursorJogo.getCount());

        cursorJogo.moveToNext();
        cursorJogo = getJogo(tabelaJogo);
        Jogos jogo = getJogosComID(cursorJogo, idTetris);
        verificarDadosJogo(nomeJogo,atividade,datalancamento, favorito, idTetris, jogo);

        nomeJogo = "Fifa";
        atividade = "Não começado";
        datalancamento = "03-03-1997";
        favorito = 0;
        long idFifa = criaJogo(tabelaJogo,nomeJogo, atividade, datalancamento, favorito);
        cursorJogo = getJogo(tabelaJogo);
        assertEquals(2, cursorJogo.getCount());
        jogo = getJogosComID(cursorJogo, idFifa);
        verificarDadosJogo(nomeJogo,atividade,datalancamento, favorito, idFifa, jogo);

        nomeJogo = "PES";
        atividade = "Não começado";
        datalancamento = "03-03-2009";
        favorito = 1;
        long idPES = criaJogo(tabelaJogo,nomeJogo, atividade, datalancamento, favorito);
        cursorJogo = getJogo(tabelaJogo);
        assertEquals(3, cursorJogo.getCount());

        jogo = getJogosComID(cursorJogo, idPES);
        verificarDadosJogo(nomeJogo,atividade,datalancamento, favorito, idPES, jogo);

        //UPDATE Tabela Jogos
        cursorJogo = getJogo(tabelaJogo);
        jogo = getJogosComID(cursorJogo, idTetris);
        nomeJogo = "Tetris2";
        atividade = "Não jogado";
        datalancamento ="01-01-2011";
        favorito = 0;
        jogo.setNome(nomeJogo);
        jogo.setAtividade(atividade);
        jogo.setDataLancamento(datalancamento);
        jogo.setFavorito(favorito);
        verificarDadosJogo(nomeJogo, atividade, datalancamento, favorito, idTetris, jogo);
        tabelaJogo.update(jogo.getContentValues(), BdTableJogos._ID + "=?", new String[] {String.valueOf(idTetris)});

        cursorJogo = getJogo(tabelaJogo);
        jogo = getJogosComID(cursorJogo, idTetris);
        verificarDadosJogo(nomeJogo, atividade, datalancamento, favorito, idTetris, jogo);

        //DELETE Tabela Jogos
        cursorJogo = getJogo(tabelaJogo);
        tabelaJogo.delete(BdTableJogos._ID + "=?", new String[] {String.valueOf(idPES)});
        assertEquals(2, cursorJogo.getCount());

        //CREATE Tabela JogosGeneros
        BdTableJogosGeneros tabelaJogosGeneros = new BdTableJogosGeneros(db);

        Cursor cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        assertEquals(0, cursorJogoGenero.getCount());

        //INSERT Tabela JogosGeneros
        long idTetrisEstrategia = criaJogoGenero(tabelaJogosGeneros, idTetris, idEstrategia);
        cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        assertEquals(1, cursorJogoGenero.getCount());

        long idTetrisFPS = criaJogoGenero(tabelaJogosGeneros, idTetris, idFPS);
        cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        assertEquals(2, cursorJogoGenero.getCount());

        JogosGeneros jogosGenero = getJogoGeneroComID(cursorJogoGenero, idTetris, idEstrategia);
        jogosGenero.setId_genero(idFPS);
        jogosGenero.setId_jogo(idFifa);
        tabelaJogosGeneros.update(jogosGenero.getContentValues(), BdTableJogosGeneros.ID_JOGO + "=? AND " + BdTableJogosGeneros.ID_GENERO + "=?", new String[] {String.valueOf(idTetris), String.valueOf(idEstrategia)});

        cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        jogosGenero = getJogoGeneroComID(cursorJogoGenero, idFifa, idFPS);
        assertEquals(jogosGenero.getId_jogo(), idFifa);
        assertEquals(jogosGenero.getId_genero(), idFPS);

        tabelaJogosGeneros.delete(BdTableJogosGeneros.ID_JOGO + "=? AND " + BdTableJogosGeneros.ID_GENERO + "=?", new String[] {String.valueOf(idTetris), String.valueOf(idFPS)});
        cursorJogoGenero = getJogoGenero(tabelaJogosGeneros);
        assertEquals(1, cursorJogoGenero.getCount());

        //CREATE Tabela JogosPlataformas
        BdTableJogosPlataformas tabelaJogosPlataformas = new BdTableJogosPlataformas(db);

        Cursor cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        assertEquals(0, cursorJogosPlataformas.getCount());

        //INSERT Tabela JogosPlataformas
        long idTetrisPS4 = criaJogoPlataformas(tabelaJogosPlataformas, idTetris, idPS4);
        cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        assertEquals(1, cursorJogosPlataformas.getCount());

        long idFifaPS4 = criaJogoPlataformas(tabelaJogosPlataformas, idFifa, idPS4);
        cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        assertEquals(2, cursorJogosPlataformas.getCount());

        //UPDATE Tabela JogosPlataformas
        JogosPlataformas jogosPlataformas = getJogoPlataformaComID(cursorJogosPlataformas,idTetris, idPS4);
        jogosPlataformas.setId_jogo(idFifa);
        jogosPlataformas.setId_plataforma(idPS3);
        tabelaJogosPlataformas.update(jogosPlataformas.getContentValues(), BdTableJogosPlataformas.ID_JOGO + "=? AND " + BdTableJogosPlataformas.ID_PLATAFORMA + "=?", new String[] {String.valueOf(idTetris), String.valueOf(idPS4)});

        cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        jogosPlataformas = getJogoPlataformaComID(cursorJogosPlataformas, idFifa, idPS3);
        assertEquals(jogosPlataformas.getId_jogo(), idFifa);
        assertEquals(jogosPlataformas.getId_plataforma(), idPS3);

        //DELETE Tabela JogosPlataformas
        tabelaJogosPlataformas.delete(BdTableJogosPlataformas.ID_JOGO + "=? AND " + BdTableJogosPlataformas.ID_PLATAFORMA + "=?", new String[] {String.valueOf(idFifa), String.valueOf(idPS3)});
        cursorJogosPlataformas = getJogoPlataforma(tabelaJogosPlataformas);
        assertEquals(1, cursorJogosPlataformas.getCount());
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
    private void verificarDadosJogo(String nomeJogo, String atividade, String datalancamento, int favorito, long idJogo, Jogos jogo) {
        assertEquals(jogo.getId(), idJogo);
        assertEquals(jogo.getNome(), nomeJogo);
        assertEquals(jogo.getAtividade(), atividade);
        assertEquals(jogo.getDataLancamento(), datalancamento);
        assertEquals(jogo.getFavorito(), favorito);
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
