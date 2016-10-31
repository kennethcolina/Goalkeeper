package goalkeeper.matheus.goalkeeper.jogadas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

import bd.DBManeger;
import goalkeeper.matheus.goalkeeper.CadastroPartida;
import goalkeeper.matheus.goalkeeper.R;

public class CaraCaraTela extends JogadaDefensivaTela {
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arraySetorBolaVeio;
    ArrayList<String> arrayTipoFinalizacao;
    ArrayList<String> arrayErros;
    ArrayList<String> arrayTipoCaraCara;
    Spinner mSpinTipoCaraCara;
    DBManeger mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_cara_tela);
        mDb = new DBManeger(this);


        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJD);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoi);
        mSpinSetorBolaVeio = (Spinner) findViewById(R.id.spinner_setorBolaVeio);
        mSpinTipoFinalizacao = (Spinner) findViewById(R.id.spinner_tipoFinalizacao);
        mSpinTipoCaraCara = (Spinner) findViewById(R.id.spinner_tipoCaraCara);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erro);
        mSpinErro = (Spinner) findViewById(R.id.spinner_erroJD);
        btnSalvarJD = (Button) findViewById(R.id.btn_salvarJD);
        btnCancelarJD = (Button) findViewById(R.id.btn_calcelJD);
        mCheckGol = (CheckBox) findViewById(R.id.check_gol);

        mSpinErro = (Spinner) findViewById(R.id.spinner_erroJD);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erro);
        mSpinErro.setVisibility(View.GONE);
        mCheckErrou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckErrou.isChecked()) mSpinErro.setVisibility(View.VISIBLE);
                else mSpinErro.setVisibility(View.GONE);
            }
        });

        carregarValores();

        btnSalvarJD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoCaraCara.getSelectedItemPosition()>0){
                    //cadastrar a jogada
                    int idJogadaDefensiva=saveJD();
                    mDb.cadastrarCaraCara(idJogadaDefensiva, mSpinTipoCaraCara.getSelectedItem().toString());
                    if(tipoFinalizacao != "chute de falta") {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa cara a cara):\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA CARA A CARA:\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa cara a cara):\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA CARA A CARA:\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            }
                        }
                    }else{
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa cara a cara):\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA CARA A CARA EM FALTA:\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE FALTA(tentou defesa punho):\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA PUNHO EM FALTA:\n" +
                                        tempo + " minutos, tipo de cara a cara: " + mSpinTipoCaraCara.getSelectedItem().toString() + ", bola foi no setor " + setorBolaFoi + " do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            }
                        }
                    }
                    finish();
                }else{
                    mensagem();
                }
            }
        });
        btnCancelarJD.setOnClickListener(new View.OnClickListener() {
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
        arraySetorBolaFoi.add("Selecione o setor onde a bola foi no gol");
        arraySetorBolaFoi.add("CID - Canto Inferior Direito");
        arraySetorBolaFoi.add("CSD - Canto Superior Direito");
        arraySetorBolaFoi.add("MI - Meio Inferior");
        arraySetorBolaFoi.add("MS - Meio Superior");
        arraySetorBolaFoi.add("CIE - Canto Inferior Esquerdo");
        arraySetorBolaFoi.add("CSE - Canto Superior Esquerdo");
        arraySetorBolaFoi.add("T - Trave");
        arraySetorBolaFoi.add("FCID - Fora em Canto Inferior Direito");
        arraySetorBolaFoi.add("FCSD - Fora em Canto Superior Direito");
        arraySetorBolaFoi.add("FMS - Fora em Meio Superior");
        arraySetorBolaFoi.add("FCIE - Fora em Canto Inferior Esquerdo");
        arraySetorBolaFoi.add("FCSE - Fora em Canto Superior Esquerdo");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaFoi);
        mSpinSetorBolaFoi.setAdapter(adapter2);

        arraySetorBolaVeio = new ArrayList<String>();
        arraySetorBolaVeio.add("Selecione o setor onde a bola veio");
        arraySetorBolaVeio.add("DE - Defensivo Esquerdo");
        arraySetorBolaVeio.add("ADE - Área Defensiva Esquerda");
        arraySetorBolaVeio.add("ADC - Área Defensiva Centro");
        arraySetorBolaVeio.add("PAD - Pequena Área Defensiva");
        arraySetorBolaVeio.add("ADD - Área Defensiva Direita");
        arraySetorBolaVeio.add("DD - Defensivo Esquerdo");
        arraySetorBolaVeio.add("MDE - Meio Defensivo Esquerdo");
        arraySetorBolaVeio.add("MDC - Meio Defensivo Centro");
        arraySetorBolaVeio.add("MDD - Meio Defensivo Direito");
        arraySetorBolaVeio.add("MOE - Meio Ofensivo Esquerdo");
        arraySetorBolaVeio.add("MOC - Meio Ofensivo Centro");
        arraySetorBolaVeio.add("MOD - Meio Ofensivo Direita");
        arraySetorBolaVeio.add("OE - Ofensivo Esquerdo");
        arraySetorBolaVeio.add("AOE - Área Ofensiva Esquerda");
        arraySetorBolaVeio.add("AOC - Área Ofensiva Centro");
        arraySetorBolaVeio.add("PAO - Pequena Área Ofensiva");
        arraySetorBolaVeio.add("AOD - Área Ofensiva Direita");
        arraySetorBolaVeio.add("OD - Ofensivo direito");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaVeio);
        mSpinSetorBolaVeio.setAdapter(adapter3);

        arrayTipoFinalizacao = new ArrayList<String>();
        arrayTipoFinalizacao.add("Selecione o tipo de finalização");
        arrayTipoFinalizacao.add("chute bola rolando");
        arrayTipoFinalizacao.add("chute de falta");
        arrayTipoFinalizacao.add("cabeceio");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoFinalizacao);
        mSpinTipoFinalizacao.setAdapter(adapter4);

        arrayErros = new ArrayList<String>();
        arrayErros.add("Selecione o erro");
        arrayErros.add("saiu muito");
        arrayErros.add("saiu pouco");
        arrayErros.add("erro de decisão");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayErros);
        mSpinErro.setAdapter(adapter5);

        arrayTipoCaraCara = new ArrayList<String>();
        arrayTipoCaraCara.add("Selecione o tipo de Cara a Cara");
        arrayTipoCaraCara.add("em pé");
        arrayTipoCaraCara.add("base");
        arrayTipoCaraCara.add("chute");
        arrayTipoCaraCara.add("dividida");
        arrayTipoCaraCara.add("rebote");
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoCaraCara);
        mSpinTipoCaraCara.setAdapter(adapter6);
    }


    public void mensagem() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Ops");
        alertDialog.setMessage("Favor, preencher todos os campos antes de continuar tirometa"+testePreenchimento());
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }



}
