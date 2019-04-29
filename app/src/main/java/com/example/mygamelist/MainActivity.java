package com.example.mygamelist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        }
        else if (id == R.id.Jogos) {
            Intent intent2 = new Intent(MainActivity.this, Jogos.class);
            startActivity(intent2);
            Toast.makeText(this, "Jogos", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.Plataformas) {
            Intent intent3 = new Intent(MainActivity.this, Plataformas.class);
            startActivity(intent3);
            Toast.makeText(this, "Plataformas", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.Generos) {
            Intent intent3 = new Intent(MainActivity.this, Generos.class);
            startActivity(intent3);
            Toast.makeText(this, "Generos", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
