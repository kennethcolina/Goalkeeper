package goalkeeper.matheus.goalkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import goalkeeper.matheus.goalkeeper.jogadas.CaraCaraTela;
import goalkeeper.matheus.goalkeeper.jogadas.CruzamentoTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefBaseTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefCaidaTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefPeTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefPunhoTela;
import goalkeeper.matheus.goalkeeper.jogadas.DefSobCabecaTela;
import goalkeeper.matheus.goalkeeper.jogadas.DominioTela;
import goalkeeper.matheus.goalkeeper.jogadas.JogadaOfensivaTela;
import goalkeeper.matheus.goalkeeper.jogadas.ReporMaoTela;
import goalkeeper.matheus.goalkeeper.jogadas.ReporVoleioTela;
import goalkeeper.matheus.goalkeeper.jogadas.TiroMetaTela;
import goalkeeper.matheus.goalkeeper.resultados.ItemListActivity;
import model.DefSobCabeca;
import model.JogadaDefensiva;
import model.JogadaOfensiva;
import model.TiroMeta;

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

        listTiposJogdas.add("Cruzamento"); //0
        listTiposJogdas.add("Cara a Cara"); //1
        listTiposJogdas.add("Defesa com os Pés"); //2
        listTiposJogdas.add("Defesa com Caída"); //3
        listTiposJogdas.add("Defesa Sob Cabeça"); //4
        listTiposJogdas.add("Defesa Base"); //5
        listTiposJogdas.add("Defesa com Punho"); //6
        listTiposJogdas.add("Domínio de bola"); //7
        listTiposJogdas.add("Reposição com as mãos"); //8
        listTiposJogdas.add("Reposição com Voleio"); //9
        listTiposJogdas.add("Tiro de Meta"); //10

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
                        Intent cruzamento = new Intent(getApplicationContext(), CruzamentoTela.class);
                        startActivity(cruzamento);
                        finish(); //finish Activity.
                        break;
                     case 1:
                        Intent caraCara = new Intent(getApplicationContext(), CaraCaraTela.class);
                        startActivity(caraCara);
                        finish(); //finish Activity.
                        break;
                    case 2:
                        Intent defPeTela = new Intent(getApplicationContext(), DefPeTela.class);
                        startActivity(defPeTela);
                        finish(); //finish Activity.
                        break;
                    case 3:
                        Intent defCaida = new Intent(getApplicationContext(), DefCaidaTela.class);
                        startActivity(defCaida);
                        finish(); //finish Activity.
                        break;
                    case 4:
                        Intent defSobCabeca = new Intent(getApplicationContext(), DefSobCabecaTela.class);
                        startActivity(defSobCabeca);
                        finish(); //finish Activity.
                        break;
                    case 5:
                        Intent defBase = new Intent(getApplicationContext(), DefBaseTela.class);
                        startActivity(defBase);
                        finish(); //finish Activity.
                        break;
                    case 6:
                        Intent defPunho = new Intent(getApplicationContext(), DefPunhoTela.class);
                        startActivity(defPunho);
                        finish(); //finish Activity.
                        break;
                    case 7:
                        Intent dominio = new Intent(getApplicationContext(), DominioTela.class);
                        startActivity(dominio);
                        finish(); //finish Activity.
                        break;
                    case 8:
                        Intent reporMao = new Intent(getApplicationContext(), ReporMaoTela.class);
                        startActivity(reporMao);
                        finish(); //finish Activity.
                        break;
                    case 9:
                        Intent reporVoleio = new Intent(getApplicationContext(), ReporVoleioTela.class);
                        startActivity(reporVoleio);
                        finish(); //finish Activity.
                        break;
                    case 10:
                        Intent cadastroTiroMeta = new Intent(getApplicationContext(), TiroMetaTela.class);
                        startActivity(cadastroTiroMeta);
                        finish(); //finish Activity.
                        break;

                }

            }
        });
    }
}
