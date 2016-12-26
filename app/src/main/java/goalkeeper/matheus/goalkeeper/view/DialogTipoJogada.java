package goalkeeper.matheus.goalkeeper.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import goalkeeper.matheus.goalkeeper.R;
import goalkeeper.matheus.goalkeeper.util.Constantes;
import goalkeeper.matheus.goalkeeper.view.jogadas.DefSaidaTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.DefBaseTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.DefCaidaTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.DefPeTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.DefPunhoTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.DefSobreCabecaTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.DominioTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.ReporMaoTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.ReporVoleioTela;
import goalkeeper.matheus.goalkeeper.view.jogadas.TiroMetaTela;

public class DialogTipoJogada extends AppCompatActivity {
    Spinner mSpinnerTipoJogadas;
    ArrayList<String> listTiposJogdas;
    Button mBtnContinuar;
    Button mBtnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_tipo_jogada);
        mSpinnerTipoJogadas = (Spinner) findViewById(R.id.spinner_tipoJogada);
        mBtnCancelar = (Button) findViewById(R.id.btn_cancelarJogada);
        mBtnContinuar = (Button) findViewById(R.id.btn_ContinuarJogada);

        listTiposJogdas = Constantes.getListJogdas();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listTiposJogdas);
        mSpinnerTipoJogadas.setAdapter(adapter);

        mBtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = mSpinnerTipoJogadas.getSelectedItem().toString();
                switch (desc) {

                    case Constantes.DESCRICAO_DEFESA_SAIDA:
                        Intent defSaida = new Intent(getApplicationContext(), DefSaidaTela.class);
                        startActivity(defSaida);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_DEFESA_PE:
                        Intent defPe = new Intent(getApplicationContext(), DefPeTela.class);
                        startActivity(defPe);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_DEFESA_CAIDA:
                        Intent defCaida = new Intent(getApplicationContext(), DefCaidaTela.class);
                        startActivity(defCaida);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_DEFESA_SOBRE_CABECA:
                        Intent defSobreCabeca = new Intent(getApplicationContext(), DefSobreCabecaTela.class);
                        startActivity(defSobreCabeca);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_DEFESA_BASE:
                        Intent defBase = new Intent(getApplicationContext(), DefBaseTela.class);
                        startActivity(defBase);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_DEFESA_PUNHO:
                        Intent defPunho = new Intent(getApplicationContext(), DefPunhoTela.class);
                        startActivity(defPunho);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_DOMINIO:
                        Intent dominio = new Intent(getApplicationContext(), DominioTela.class);
                        startActivity(dominio);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_REPOR_MAO:
                        Intent reporMao = new Intent(getApplicationContext(), ReporMaoTela.class);
                        startActivity(reporMao);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_REPOR_VOLEIO:
                        Intent reporVoleio = new Intent(getApplicationContext(), ReporVoleioTela.class);
                        startActivity(reporVoleio);
                        finish(); //finish Activity.
                        break;
                    case Constantes.DESCRICAO_TIRO_META:
                        Intent cadastroTiroMeta = new Intent(getApplicationContext(), TiroMetaTela.class);
                        startActivity(cadastroTiroMeta);
                        finish(); //finish Activity.
                        break;
                }
            }
        });
    }
}
