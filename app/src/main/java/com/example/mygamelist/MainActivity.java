package com.example.mygamelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.Jogos) {
            Intent intent = new Intent(MainActivity.this, JogosActivity.class);
            startActivity(intent);
            Toast.makeText(this, "JogosActivity", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.Plataformas) {
            Intent intent = new Intent(MainActivity.this, PlataformasActivity.class);
            startActivity(intent);
            Toast.makeText(this, "PlataformasActivity", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.Generos) {
            Intent intent = new Intent(MainActivity.this, GenerosActivity.class);
            startActivity(intent);
            Toast.makeText(this, "GenerosActivity", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
