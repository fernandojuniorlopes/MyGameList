package com.example.mygamelist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class JogosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jogo, menu);
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
            Intent intent = new Intent(JogosActivity.this, NovoJogoActivity.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemadicionarnovojogo), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.EditarJogo) {
            Intent intent = new Intent(JogosActivity.this, EditarJogoActivity.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.EditarJogo), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.EliminarJogo) {
            Intent intent = new Intent(JogosActivity.this, EliminarJogoActivity.class);
            startActivity(intent);
            Toast.makeText(this, getString(R.string.itemeliminarjogo), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}