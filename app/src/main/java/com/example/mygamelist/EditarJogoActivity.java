package com.example.mygamelist;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditarJogoActivity extends AppCompatActivity {

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

        CheckBox checkBoxFavoritos = findViewById(R.id.checkBoxFavoritos);

        TextView errorGenero = (TextView)spinnerGenero.getSelectedView();
        TextView errorPlataforma = (TextView)spinnerPlataforma.getSelectedView();
        TextView errorJogado = (TextView)spinnerJogado.getSelectedView();
        TextView errorDia = (TextView)spinnerDia.getSelectedView();
        TextView errorMes = (TextView)spinnerMes.getSelectedView();
        TextView errorAno = (TextView)spinnerAno.getSelectedView();

        String NomeJogo = editTextnomejogo.getText().toString();

        int Plataforma = spinnerPlataforma.getSelectedItemPosition();
        int Genero = spinnerGenero.getSelectedItemPosition();
        int Jogado = spinnerJogado.getSelectedItemPosition();
        int Dia = spinnerDia.getSelectedItemPosition();
        int Mes = spinnerMes.getSelectedItemPosition();
        int Ano = spinnerAno.getSelectedItemPosition();

        boolean flag = true;
        boolean favoritos = false;

        if(checkBoxFavoritos.isChecked()){
            favoritos = true;
        }

        if (NomeJogo.trim().length() == 0) {
            editTextnomejogo.setError(getString(R.string.nome_obrigatorio));
            editTextnomejogo.requestFocus();
            flag = false;
        }else
            editTextnomejogo.setError(null);


        if (Genero == 0) {
            tratarErros(errorGenero,(getString(R.string.genero_obrigatorio)));
            flag = false;
        }else
            errorGenero.setError(null);

        if (Plataforma == 0) {
            tratarErros(errorPlataforma, (getString(R.string.plataforma_obrigatorio)));
            flag = false;
        }else
            errorPlataforma.setError(null);

        if (Jogado == 0) {
            tratarErros(errorJogado, (getString(R.string.jogados_obrigatorio)));
            flag = false;
        }else
            errorJogado.setError(null);

        if (Dia == 0) {
            ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
            flag = false;
        }else
            errorDia.setError(null);

        if (Mes == 0) {
            ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
            flag = false;
        }else
            errorMes.setError(null);

        if (Ano == 0) {
            ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
            flag = false;
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
                        ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
                        ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
                        ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
                        flag = false;
                    } else {
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
                        ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
                        ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
                        ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
                        flag = false;
                    } else {
                        errorDia.setError(null);
                        errorMes.setError(null);
                        errorAno.setError(null);
                    }
                    break;
                case 2:
                    if (Ano % 4 == 0) {
                        if (Dia > 29) {
                            ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
                            ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
                            ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
                            flag = false;
                        } else {
                            errorDia.setError(null);
                            errorMes.setError(null);
                            errorAno.setError(null);
                        }
                    } else {
                        if (Dia > 28) {
                            ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
                            ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
                            ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
                            flag = false;
                        } else {
                            errorDia.setError(null);
                            errorMes.setError(null);
                            errorAno.setError(null);
                        }
                        break;
                    }
            }

        }else{
            ErroData(errorDia, (getString(R.string.dias_obrigatorio)));
            ErroData(errorMes,(getString(R.string.mes_obrigatorio)));
            ErroData(errorAno,(getString(R.string.ano_obrigatorio)));
        }
        if(flag){
            finish();
            Toast.makeText(this, getString(R.string.dados_sucesso), Toast.LENGTH_LONG).show();
        }
    }

    public void CancelarJogo(View view){
        finish();
    }

    public void tratarErros(TextView textView, String string){
        textView.setError(string);
        textView.setTextColor(getResources().getColor(R.color.colorRed));
        textView.setText(string);
    }
    public void ErroData(TextView textView, String string){
        textView.setTextColor(getResources().getColor(R.color.colorRed));
        textView.setText(string);
    }
}