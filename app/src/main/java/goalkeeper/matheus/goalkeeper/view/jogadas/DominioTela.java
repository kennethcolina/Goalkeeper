package goalkeeper.matheus.goalkeeper.view.jogadas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.util.Constantes;
import goalkeeper.matheus.goalkeeper.view.CadastroPartida;
import goalkeeper.matheus.goalkeeper.util.Mensagem;
import goalkeeper.matheus.goalkeeper.R;

public class DominioTela extends JogadaOfensivaTela {
    ArrayList<String> tiposDominio;
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arrayPrimBola;
    ArrayList<String> arraySegBola;
    Spinner mSpinTipoDominio;
    DBManager mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dominio_tela);

        mDb = new DBManager(this);

        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        mSpinTipoDominio = (Spinner) findViewById(R.id.spinner_tipoDominio);
        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJO);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoiJO);
        mSpinPrimeiraBola = (Spinner) findViewById(R.id.spinner_primBola);
        mSpinSegundaBola = (Spinner) findViewById(R.id.spinner_segBola);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erroJO);
        mTextObservacao = (EditText) findViewById(R.id.edit_txt_observacao);
        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        mBtnSetorCampo = (Button) findViewById(R.id.btn_setor_campo);

        mLinearPrimBola = (LinearLayout) findViewById(R.id.linear_primBola);
        mLinearSegBola = (LinearLayout) findViewById(R.id.linear_segBola);

        carregarValores();

        mBtnSetorCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagemAjuda(R.drawable.campo_setores_mais);
            }
        });

        btnSalvarJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoDominio.getSelectedItemId()>0){
                    //cadastrar a jogada
                    int idJogadaOfensiva=saveJO();
                    mDb.cadastrarDominio(idJogadaOfensiva,mSpinTipoDominio.getSelectedItem().toString());
                    if(errou==1)CadastroPartida.historico += "DOMÍNIO:\n"+
                            "Tempo: " + tempo + "'\nTipo de domínio: "+ mSpinTipoDominio.getSelectedItem().toString() + "\nObservação: "+observacao+ "\n" + "Status: Errou na jogada\n\n";

                    if(observacao.isEmpty()) {
                        if (errou == 0) CadastroPartida.historico += "DOMÍNIO:\n" +
                                "Tempo: " + tempo + "'\nTipo de domínio: " + mSpinTipoDominio.getSelectedItem().toString() + "\nStatus: Acertou a jogada\n\n";
                    } else {
                        if (errou == 0) CadastroPartida.historico += "DOMÍNIO:\n" +
                                "Tempo: " + tempo + "'\nTipo de domínio: " + mSpinTipoDominio.getSelectedItem().toString() + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                    }
                    finish();
                }else{
                    Mensagem msg = new Mensagem();
                    msg.alerta(v.getContext());
                }
            }
        });
    }

    private void carregarValores() {
        tiposDominio = new ArrayList<String>();
        tiposDominio.add("Selecione o tipo de domínio");
        tiposDominio.add("Alto");
        tiposDominio.add("Baixo");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tiposDominio);
        mSpinTipoDominio.setAdapter(adapter);

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
        mSpinPrimeiraBola.setSelection(1);
        mLinearPrimBola.setVisibility(View.GONE);

        arraySegBola = new ArrayList<String>();
        arraySegBola.add("Selecione quem ganhou a segunda bola");
        arraySegBola.add("Companheiro");
        arraySegBola.add("Adversario");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySegBola);
        mSpinSegundaBola.setAdapter(adapter4);
        mSpinSegundaBola.setSelection(1);
        mLinearSegBola.setVisibility(View.GONE);

        mTextObservacao.setFocusable(false);
        mTextObservacao.setFocusable(true);
        mTextObservacao.setFocusableInTouchMode(true);
    }
}

