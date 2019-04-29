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
        Spinner spinnerDia = findViewById(R.id.spinnerDia);
        Spinner spinnerMes = findViewById(R.id.spinnerMes);
        Spinner spinnerAno = findViewById(R.id.spinnerAno);


        String NomeJogo = editTextnomejogo.getText().toString();
        int Plataforma = spinnerPlataforma.getSelectedItemPosition();
        int Genero = spinnerGenero.getSelectedItemPosition();
        int Jogado = spinnerJogado.getSelectedItemPosition();
        int Dia = spinnerDia.getSelectedItemPosition();
        int Mes = spinnerMes.getSelectedItemPosition();
        int Ano = spinnerAno.getSelectedItemPosition();
        int flag = 0;

        TextView errorGenero = (TextView)spinnerGenero.getSelectedView();
        TextView errorPlataforma = (TextView)spinnerPlataforma.getSelectedView();
        TextView errorJogado = (TextView)spinnerJogado.getSelectedView();
        TextView errorDia = (TextView)spinnerDia.getSelectedView();
        TextView errorMes = (TextView)spinnerMes.getSelectedView();
        TextView errorAno = (TextView)spinnerAno.getSelectedView();


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

        if (Dia == 0) {
            errorDia.setTextColor(getResources().getColor(R.color.colorRed));
            errorDia.setText(R.string.dias_obrigatorio);
        }else
            errorDia.setError(null);

        if (Mes == 0) {
            errorMes.setTextColor(getResources().getColor(R.color.colorRed));
            errorMes.setText(R.string.mes_obrigatorio);
        }else
            errorMes.setError(null);

        if (Ano == 0) {
            errorAno.setTextColor(getResources().getColor(R.color.colorRed));
            errorAno.setText(R.string.ano_obrigatorio);
        }else
            errorAno.setError(null);

        if((Mes != 0)&&(Dia!=0)&&(Ano!=0)) {
            switch (Mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (Dia > 31) {
                        errorDia.setTextColor(getResources().getColor(R.color.colorRed));
                        errorDia.setText(R.string.dias_obrigatorio);
                        errorMes.setTextColor(getResources().getColor(R.color.colorRed));
                        errorMes.setText(R.string.mes_obrigatorio);
                        errorAno.setTextColor(getResources().getColor(R.color.colorRed));
                        errorAno.setText(R.string.ano_obrigatorio);
                        flag = 0;
                    } else {
                        flag = 1;
                        errorDia.setError(null);
                        errorMes.setError(null);
                        errorAno.setError(null);
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (Dia > 30) {
                        errorDia.setTextColor(getResources().getColor(R.color.colorRed));
                        errorDia.setText(R.string.dias_obrigatorio);
                        errorMes.setTextColor(getResources().getColor(R.color.colorRed));
                        errorMes.setText(R.string.mes_obrigatorio);
                        errorAno.setTextColor(getResources().getColor(R.color.colorRed));
                        errorAno.setText(R.string.ano_obrigatorio);
                        flag = 0;
                    } else {
                        flag = 1;
                        errorDia.setError(null);
                        errorMes.setError(null);
                        errorAno.setError(null);
                    }
                    break;
                case 2:
                    if (Ano % 4 == 0) {
                        if (Dia > 29) {
                            errorDia.setTextColor(getResources().getColor(R.color.colorRed));
                            errorDia.setText(R.string.dias_obrigatorio);
                            errorMes.setTextColor(getResources().getColor(R.color.colorRed));
                            errorMes.setText(R.string.mes_obrigatorio);
                            errorAno.setTextColor(getResources().getColor(R.color.colorRed));
                            errorAno.setText(R.string.ano_obrigatorio);
                            flag = 0;
                        } else {
                            flag = 1;
                            errorDia.setError(null);
                            errorMes.setError(null);
                            errorAno.setError(null);
                        }
                    } else {
                        if (Dia > 28) {
                            errorDia.setTextColor(getResources().getColor(R.color.colorRed));
                            errorDia.setText(R.string.dias_obrigatorio);
                            errorMes.setTextColor(getResources().getColor(R.color.colorRed));
                            errorMes.setText(R.string.mes_obrigatorio);
                            errorAno.setTextColor(getResources().getColor(R.color.colorRed));
                            errorAno.setText(R.string.ano_obrigatorio);
                            flag = 0;
                        } else {
                            flag = 1;
                            errorDia.setTextColor(getResources().getColor(R.color.colorRed));
                            errorDia.setText(R.string.dias_obrigatorio);
                            errorMes.setTextColor(getResources().getColor(R.color.colorRed));
                            errorMes.setText(R.string.mes_obrigatorio);
                            errorAno.setTextColor(getResources().getColor(R.color.colorRed));
                            errorAno.setText(R.string.ano_obrigatorio);
                        }
                        break;
                    }
            }

        }else{
            errorDia.setTextColor(getResources().getColor(R.color.colorRed));
            errorDia.setText(R.string.dias_obrigatorio);
            errorMes.setTextColor(getResources().getColor(R.color.colorRed));
            errorMes.setText(R.string.mes_obrigatorio);
            errorAno.setTextColor(getResources().getColor(R.color.colorRed));
            errorAno.setText(R.string.ano_obrigatorio);
        }
        if((NomeJogo.trim().length() != 0)&&(Genero!=0)&&(Plataforma!=0)&&(Dia!=0)&&(Mes!=0)&&(Ano!=0)&&(flag == 1)){
            finish();
            Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
        }
    }

    public void CancelarJogo(View view){
        finish();
    }
}
