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

public class NovoJogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void ConfirmarJogo(View view){
        EditText editTextNomeJogo = findViewById(R.id.textViewNomeJogo);
        Spinner spinnerGenero = findViewById(R.id.spinnerGenero);
        Spinner spinnerPlataforma = findViewById(R.id.spinnerPlataforma);
        Spinner spinnerJogado = findViewById(R.id.spinnerJogado);

        String NomeJogo = editTextNomeJogo.getText().toString();
        int Plataforma = spinnerPlataforma.getSelectedItemPosition();
        int Genero = spinnerGenero.getSelectedItemPosition();
        int Jogado = spinnerJogado.getSelectedItemPosition();


        TextView errorGenero = (TextView)spinnerGenero.getSelectedView();
        TextView errorPlataforma = (TextView)spinnerPlataforma.getSelectedView();
        TextView errorJogado = (TextView)spinnerJogado.getSelectedView();

            if (NomeJogo.trim().length() == 0) {
                editTextNomeJogo.setError(getString(R.string.nome_obrigatorio));
                editTextNomeJogo.requestFocus();
            }else
                editTextNomeJogo.setError(null);


            if (Genero == 0) {
                errorGenero.setError(getString(R.string.genero_obrigatorio));
                errorGenero.requestFocus();
            }else
                errorGenero.setError(null);

            if (Plataforma == 0) {
                errorPlataforma.setError(getString(R.string.plataforma_obrigatorio));
                errorPlataforma.requestFocus();
            }else
                errorPlataforma.setError(null);

            if (Jogado == 0) {
                errorJogado.setError(getString(R.string.jogados_obrigatorio));
                errorJogado.requestFocus();
            }else
                errorJogado.setError(null);

            if((NomeJogo.trim().length() != 0)&&(Genero!=0)&&(Plataforma!=0)){
                finish();
                Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
            }
    }

    public void CancelarJogo(View view){
        finish();
    }
}
