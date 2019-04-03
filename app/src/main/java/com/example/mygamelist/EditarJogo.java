package com.example.mygamelist;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditarJogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_jogo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void ConfirmarJogo(View view){
        EditText editTextnomejogo = findViewById(R.id.textViewnomejogo);
        Spinner spinnerGenero = findViewById(R.id.spinnerGenero);
        Spinner spinnerPlataforma = findViewById(R.id.spinnerPlataforma);
        Spinner spinnerJogado = findViewById(R.id.spinnerJogado);

        String NomeJogo = editTextnomejogo.getText().toString();
        int Plataforma = spinnerPlataforma.getSelectedItemPosition();
        int Genero = spinnerGenero.getSelectedItemPosition();
        int Jogado = spinnerJogado.getSelectedItemPosition();


        TextView errorGenero = (TextView)spinnerGenero.getSelectedView();
        TextView errorPlataforma = (TextView)spinnerPlataforma.getSelectedView();
        TextView errorJogado = (TextView)spinnerJogado.getSelectedView();

        if (NomeJogo.trim().length() == 0) {
            editTextnomejogo.setError(getString(R.string.nome_obrigatorio));
            editTextnomejogo.requestFocus();
        }else
            editTextnomejogo.setError(null);


        if (Genero == 0) {
            errorGenero.setError(getString(R.string.genero_obrigatorio));
            errorGenero.setTextColor(getResources().getColor(R.color.colorRed));
            errorGenero.setText(R.string.genero_obrigatorio);
        }else
            errorGenero.setError(null);

        if (Plataforma == 0) {
            errorPlataforma.setError(getString(R.string.plataforma_obrigatorio));
            errorPlataforma.setTextColor(getResources().getColor(R.color.colorRed));
            errorPlataforma.setText(R.string.plataforma_obrigatorio);
        }else
            errorPlataforma.setError(null);

        if (Jogado == 0) {
            errorJogado.setError(getString(R.string.jogados_obrigatorio));
            errorJogado.setTextColor(getResources().getColor(R.color.colorRed));
            errorJogado.setText(R.string.jogados_obrigatorio);
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
