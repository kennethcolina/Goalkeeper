package goalkeeper.matheus.goalkeeper.view.jogadas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.util.Constantes;
import goalkeeper.matheus.goalkeeper.view.CadastroPartida;
import goalkeeper.matheus.goalkeeper.util.Mensagem;
import goalkeeper.matheus.goalkeeper.R;

public class ReporMaoTela extends JogadaOfensivaTela {
    ArrayList<String> tiposReporMao;
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arrayPrimBola;
    ArrayList<String> arraySegBola;

    Spinner mSpinTipoReporMao;
    DBManager mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repor_mao_tela);

        mDb = new DBManager(this);

        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        btnCancelarJO = (Button) findViewById(R.id.btn_calcelJO);
        mSpinTipoReporMao = (Spinner) findViewById(R.id.spinner_tipoReporMao);
        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJO);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoiJO);
        mSpinPrimeiraBola = (Spinner) findViewById(R.id.spinner_primBola);
        mSpinSegundaBola = (Spinner) findViewById(R.id.spinner_segBola);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erroJO);
       // mSpinErro = (Spinner) findViewById(R.id.spinner_erroJO);
        mTextObservacao = (EditText) findViewById(R.id.edit_txt_observacao);
        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        btnCancelarJO = (Button) findViewById(R.id.btn_calcelJO);

        carregarValores();

        btnSalvarJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoReporMao.getSelectedItemId()>0){
                    //cadastrar a jogada
                    int idJogadaOfensiva=saveJO();
                    mDb.cadastrarReporMao(idJogadaOfensiva, mSpinTipoReporMao.getSelectedItem().toString());
                    if(errou==1)CadastroPartida.historico += "REPOSIÇÃO COM MÃO:\n"+
                            "Tempo: " + tempo + "'\nTipo de reposição: " + mSpinTipoReporMao.getSelectedItem().toString() + "\nSetor destino da jogada: " + setorBolaFoi + "\nPrimeira bola ganha por: " + primeiraBola+ "\nSegunda bola ganha por: " + segundaBola + "\nObservação: " + observacao + "\n\n";

                    if(observacao.isEmpty()) {
                        if (errou == 0) CadastroPartida.historico += "REPOSIÇÃO COM MÃO:\n" +
                                "Tempo: " + tempo + "'\nTipo de reposição: " + mSpinTipoReporMao.getSelectedItem().toString() + "\nSetor destino da jogada: " + setorBolaFoi + "\nPrimeira bola ganha por: " + primeiraBola + "\nSegunda bola ganha por: " + segundaBola + "\nStatus: Acertou a jogada\n\n";
                    } else {
                        if (errou == 0) CadastroPartida.historico += "REPOSIÇÃO COM MÃO:\n" +
                                "Tempo: " + tempo + "'\nTipo de reposição: " + mSpinTipoReporMao.getSelectedItem().toString() + "\nSetor destino da jogada: " + setorBolaFoi + "\nPrimeira bola ganha por: " + primeiraBola + "\nSegunda bola ganha por: " + segundaBola + "\nObservação: "+observacao+ "\nStatus: Acertou a jogada\n\n";
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
        tiposReporMao = new ArrayList<String>();
        tiposReporMao.add("Selecione o tipo");
        tiposReporMao.add("por cima");
        tiposReporMao.add("por baixo");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tiposReporMao);
        mSpinTipoReporMao.setAdapter(adapter);

        tempos = new ArrayList<String>();
        tempos.add("Selecione o tempo de jogo");
        for(int i=0;i<91;i++) tempos.add(i+"'");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempos);
        mSpinTempo.setAdapter(adapter1);

        arraySetorBolaFoi = Constantes.getListSetoresCampo(Constantes.labelDestino);
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
