package goalkeeper.matheus.goalkeeper.jogadas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.CadastroPartida;
import goalkeeper.matheus.goalkeeper.Mensagem;
import goalkeeper.matheus.goalkeeper.R;

public class ReporVoleioTela extends JogadaOfensivaTela {
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arrayPrimBola;
    ArrayList<String> arraySegBola;
    DBManager mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repor_voleio_tela);

        mDb = new DBManager(this);

        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        btnCancelarJO = (Button) findViewById(R.id.btn_calcelJO);
        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJO);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoiJO);
        mSpinPrimeiraBola = (Spinner) findViewById(R.id.spinner_primBola);
        mSpinSegundaBola = (Spinner) findViewById(R.id.spinner_segBola);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erroJO);
        mTextObservacao = (EditText) findViewById(R.id.edit_txt_observacao);

        carregarValores();

        btnSalvarJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento()){
                    //cadastrar a jogada
                    int idJogadaOfensiva=saveJO();
                    mDb.cadastrarReporVoleio(idJogadaOfensiva);
                    if(errou==1)CadastroPartida.historico += "REPOSIÇÃO VOLEIO:\n"+
                            "Tempo: " + tempo + "'\nSetor destino da jogada: "+ setorBolaFoi + "\nPrimeira bola ganha por: " + primeiraBola + "\nSegunda bola ganha por: " + segundaBola + "\nObservação: " + observacao + "\n\n";

                    if(observacao.isEmpty()) {
                        if (errou == 0) CadastroPartida.historico += "REPOSIÇÃO VOLEIO:\n" +
                                "Tempo: " + tempo + "'\nSetor destino da jogada: " + setorBolaFoi + "\nPrimeira bola ganha por: " + primeiraBola + "\nSegunda bola ganha por: " + segundaBola + "\nStatus: Acertou a jogada\n\n";
                    } else {
                        if (errou == 0) CadastroPartida.historico += "REPOSIÇÃO VOLEIO:\n" +
                                "Tempo: " + tempo + "'\nSetor destino da jogada: " + setorBolaFoi + "\nPrimeira bola ganha por: " + primeiraBola + "\nSegunda bola ganha por: " + segundaBola + "\nObservação: "+ observacao + "\nStatus: Acertou a jogada\n\n";
                    }
                    finish();
                }else{
                    Mensagem msg = new Mensagem();
                    msg.alerta(v.getContext());
                }
            }
        });
        btnCancelarJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void carregarValores() {
        tempos = new ArrayList<String>();
        tempos.add("Selecione o tempo de jogo");
        for(int i=0;i<91;i++) tempos.add(i+"'");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempos);
        mSpinTempo.setAdapter(adapter1);

        arraySetorBolaFoi = new ArrayList<String>();
        arraySetorBolaFoi.add("Selecione o setor onde a bola foi");
        arraySetorBolaFoi.add("DE - Defensivo Esquerdo");
        arraySetorBolaFoi.add("ADE - Área Defensiva Esquerda");
        arraySetorBolaFoi.add("ADC - Área Defensiva Centro");
        arraySetorBolaFoi.add("PAD - Pequena Área Defensiva");
        arraySetorBolaFoi.add("ADD - Área Defensiva Direita");
        arraySetorBolaFoi.add("DD - Defensivo Direito");
        arraySetorBolaFoi.add("MDE - Meio Defensivo Esquerdo");
        arraySetorBolaFoi.add("MDC - Meio Defensivo Centro");
        arraySetorBolaFoi.add("MDD - Meio Defensivo Direito");
        arraySetorBolaFoi.add("MOE - Meio Ofensivo Esquerdo");
        arraySetorBolaFoi.add("MOC - Meio Ofensivo Centro");
        arraySetorBolaFoi.add("MOD - Meio Ofensivo Direita");
        arraySetorBolaFoi.add("OE - Ofensivo Esquerdo");
        arraySetorBolaFoi.add("AOE - Área Ofensiva Esquerda");
        arraySetorBolaFoi.add("AOC - Área Ofensiva Centro");
        arraySetorBolaFoi.add("PAO - Pequena Área Ofensiva");
        arraySetorBolaFoi.add("AOD - Área Ofensiva Direita");
        arraySetorBolaFoi.add("OD - Ofensivo direito");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaFoi);
        mSpinSetorBolaFoi.setAdapter(adapter2);

        arrayPrimBola = new ArrayList<String>();
        arrayPrimBola.add("Selecione quem ganhou a primeira bola");
        arrayPrimBola.add("Companheiro");
        arrayPrimBola.add("Adversario");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayPrimBola);
        mSpinPrimeiraBola.setAdapter(adapter3);

        arraySegBola = new ArrayList<String>();
        arraySegBola.add("Selecione quem ganhou a segunda bola");
        arraySegBola.add("Companheiro");
        arraySegBola.add("Adversario");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySegBola);
        mSpinSegundaBola.setAdapter(adapter4);
    }
}
