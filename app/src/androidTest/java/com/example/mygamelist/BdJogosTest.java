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
}
