package com.example.mygamelist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class NovoJogo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.spinnerGeneros);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.spinnerPlataformas);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.plataformas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

    }

    public void ConfirmarJogo(View view){
        TextView textviewgenero = findViewById(R.id.textViewGeneros);
        TextView textviewplataforma = findViewById(R.id.textViewPlataformas);
        EditText editTextNomeJogo = findViewById(R.id.textViewNomeJogo);
        Spinner spinnerGenero = findViewById(R.id.spinnerGeneros);
        Spinner spinnerPlataforma = findViewById(R.id.spinnerPlataformas);

        String NomeJogo = editTextNomeJogo.getText().toString();
        int Genero = spinnerGenero.getSelectedItemPosition();
        int Plataforma = spinnerPlataforma.getSelectedItemPosition();

            if (NomeJogo.trim().length() == 0) {
                editTextNomeJogo.setError(getString(R.string.nome_obrigatorio));

            }else
                editTextNomeJogo.setError(null);


            if (Genero == 0) {
                textviewgenero.setError(getString(R.string.genero_obrigatorio));
            }else
                textviewgenero.setError(null);

            if (Plataforma == 0) {
                textviewplataforma.setError(getString(R.string.plataforma_obrigatorio));
            }else
                textviewplataforma.setError(null);
            if((NomeJogo.trim().length() == 0)&&(Genero!=0)&&(Plataforma!=0)){
                finish();
                Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
            }
    }

    public void CancelarJogo(View view){
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
