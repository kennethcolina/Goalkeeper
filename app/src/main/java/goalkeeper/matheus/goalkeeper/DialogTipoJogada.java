package goalkeeper.matheus.goalkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import goalkeeper.matheus.goalkeeper.jogadas.DefSaidaTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefBaseTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefCaidaTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefPeTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefPunhoTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefSobreCabecaTela;
import goalkeeper.matheus.goalkeeper.jogadas.DominioTela;
import goalkeeper.matheus.goalkeeper.jogadas.ReporMaoTela;
import goalkeeper.matheus.goalkeeper.jogadas.ReporVoleioTela;
import goalkeeper.matheus.goalkeeper.jogadas.TiroMetaTela;

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

        listTiposJogdas = new ArrayList<>();

        listTiposJogdas.add("Defesa com Saída"); //0
        listTiposJogdas.add("Defesa com os Pés"); //1
        listTiposJogdas.add("Defesa com Caída"); //2
        listTiposJogdas.add("Defesa Sobre Cabeça"); //3
        listTiposJogdas.add("Defesa Base"); //4
        listTiposJogdas.add("Defesa com Punho"); //5
        listTiposJogdas.add("Domínio de bola"); //6
        listTiposJogdas.add("Reposição com as mãos"); //7
        listTiposJogdas.add("Reposição com Voleio"); //8
        listTiposJogdas.add("Tiro de Meta"); //9

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
                int op = (int) mSpinnerTipoJogadas.getSelectedItemId();
                switch (op) {

                    case 0:
                        Intent defSaida = new Intent(getApplicationContext(), DefSaidaTela.class);
                        startActivity(defSaida);
                        finish(); //finish Activity.
                        break;
                    case 1:
                        Intent defPe = new Intent(getApplicationContext(), DefPeTela.class);
                        startActivity(defPe);
                        finish(); //finish Activity.
                        break;
                    case 2:
                        Intent defCaida = new Intent(getApplicationContext(), DefCaidaTela.class);
                        startActivity(defCaida);
                        finish(); //finish Activity.
                        break;
                    case 3:
                        Intent defSobreCabeca = new Intent(getApplicationContext(), DefSobreCabecaTela.class);
                        startActivity(defSobreCabeca);
                        finish(); //finish Activity.
                        break;
                    case 4:
                        Intent defBase = new Intent(getApplicationContext(), DefBaseTela.class);
                        startActivity(defBase);
                        finish(); //finish Activity.
                        break;
                    case 5:
                        Intent defPunho = new Intent(getApplicationContext(), DefPunhoTela.class);
                        startActivity(defPunho);
                        finish(); //finish Activity.
                        break;
                    case 6:
                        Intent dominio = new Intent(getApplicationContext(), DominioTela.class);
                        startActivity(dominio);
                        finish(); //finish Activity.
                        break;
                    case 7:
                        Intent reporMao = new Intent(getApplicationContext(), ReporMaoTela.class);
                        startActivity(reporMao);
                        finish(); //finish Activity.
                        break;
                    case 8:
                        Intent reporVoleio = new Intent(getApplicationContext(), ReporVoleioTela.class);
                        startActivity(reporVoleio);
                        finish(); //finish Activity.
                        break;
                    case 9:
                        Intent cadastroTiroMeta = new Intent(getApplicationContext(), TiroMetaTela.class);
                        startActivity(cadastroTiroMeta);
                        finish(); //finish Activity.
                        break;
                }
            }
        });
    }
}
