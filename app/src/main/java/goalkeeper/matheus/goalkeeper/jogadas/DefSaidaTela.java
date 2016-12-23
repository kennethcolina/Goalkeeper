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

public class DefSaidaTela extends JogadaDefensivaTela {

    public Spinner mSpinTipoSaida;
    public Spinner mSpinMotivoSaida;

    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arraySetorBolaVeio;
    ArrayList<String> arrayTipoFinalizacao;
    ArrayList<String> arrayTipoSaida;
    ArrayList<String> arrayMotivoSaida;

    DBManager mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_saida_tela);
        mDb = new DBManager(this);

        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJD);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoi);
        mSpinSetorBolaVeio = (Spinner) findViewById(R.id.spinner_setorBolaVeio);
        mSpinTipoFinalizacao = (Spinner) findViewById(R.id.spinner_tipoFinalizacao);
        mSpinTipoSaida = (Spinner) findViewById(R.id.spinner_tipoSaida); //inseri e criei
        mSpinMotivoSaida = (Spinner) findViewById(R.id.spinner_motivoSaida); //inseri e criei
        mCheckErrou = (CheckBox) findViewById(R.id.check_erro);
        mTextObservacao = (EditText) findViewById(R.id.edit_txt_observacao);
        btnSalvarJD = (Button) findViewById(R.id.btn_salvarJD);
        btnCancelarJD = (Button) findViewById(R.id.btn_calcelJD);
        mCheckGol = (CheckBox) findViewById(R.id.check_gol);

        carregarValores();

        btnSalvarJD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoSaida.getSelectedItemPosition()>0){
                    //cadastrar a jogada
                    int idJogadaDefensiva=saveJD();
                    mDb.cadastrarDefSaida(idJogadaDefensiva, mSpinTipoSaida.getSelectedItem().toString(), mSpinMotivoSaida.getSelectedItem().toString());
                    if(tipoFinalizacao == "chute bola rolando") {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE CHUTE (tentou defesa com saída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SAÍDA EM CHUTE:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CHUTE:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CHUTE:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nAcertou a jogada\n\n";
                                }
                            }
                        }
                    } else if (tipoFinalizacao == "cabeceio") {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com saída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SAÍDA EM CABECEIO:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    } else {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SAÍDA EM CRUZAMENTO/LANÇAMENTO:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CRUZAMENTO/LANÇAMENTO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CRUZAMENTO/LANÇAMENTO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da jogada: " + setorBolaVeio + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    }
                    finish();
                }else{
                    Mensagem msg = new Mensagem();
                    msg.alerta(v.getContext());
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
        for (int i = 0; i < 91; i++) tempos.add(i + "'");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempos);
        mSpinTempo.setAdapter(adapter1);

        arraySetorBolaFoi = new ArrayList<String>();
        arraySetorBolaFoi.add("Selecione o setor onde a bola foi no campo");
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

        arraySetorBolaVeio = new ArrayList<String>();
        arraySetorBolaVeio.add("Selecione o setor onde a bola veio");
        arraySetorBolaVeio.add("DE - Defensivo Esquerdo");
        arraySetorBolaVeio.add("EE - Escanteio Esquerdo");
        arraySetorBolaVeio.add("ADE - Área Defensiva Esquerda");
        arraySetorBolaVeio.add("ADC - Área Defensiva Centro");
        arraySetorBolaVeio.add("PAD - Pequena Área Defensiva");
        arraySetorBolaVeio.add("ADD - Área Defensiva Direita");
        arraySetorBolaVeio.add("DD - Defensivo Direito");
        arraySetorBolaVeio.add("ED - Escanteio Direito");
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

        arrayTipoSaida = new ArrayList<String>();
        arrayTipoSaida.add("Selecione o tipo de saída");
        arrayTipoSaida.add("não saiu");
        arrayTipoSaida.add("com os pés");
        arrayTipoSaida.add("encaixe");
        arrayTipoSaida.add("soco");
        arrayTipoSaida.add("espalmo");
        arrayTipoSaida.add("cabeça");
        arrayTipoSaida.add("dividida");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoSaida);
        mSpinTipoSaida.setAdapter(adapter4);

        arrayMotivoSaida = new ArrayList<String>();
        arrayMotivoSaida.add("Selecione o motivo de saída");
        arrayMotivoSaida.add("cruzamento/lançamento bola rolando");
        arrayMotivoSaida.add("escanteio");
        arrayMotivoSaida.add("falta");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayMotivoSaida);
        mSpinMotivoSaida.setAdapter(adapter5);

        arrayTipoFinalizacao = new ArrayList<String>();
        arrayTipoFinalizacao.add("Selecione o tipo de finalização");
        arrayTipoFinalizacao.add("não houve finalização");
        arrayTipoFinalizacao.add("chute bola rolando");
        arrayTipoFinalizacao.add("cabeceio");
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoFinalizacao);
        mSpinTipoFinalizacao.setAdapter(adapter6);
    }
}
