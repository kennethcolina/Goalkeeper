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
        mCheckGol = (CheckBox) findViewById(R.id.check_gol);
        mBtnSetorCampo = (Button) findViewById(R.id.btn_setor_campo);
        mBtnSetorGol = (Button) findViewById(R.id.btn_setor_gol);

        carregarValores();

        mBtnSetorCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagemAjuda(R.drawable.campo_setores_mais);
            }
        });

        mBtnSetorGol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagemAjuda(R.drawable.gol_setores_mais);
            }
        });

        btnSalvarJD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoSaida.getSelectedItemPosition()>0){
                    //verificar inconsistencias
                    if(verificarInconsistencia(v) == false || verificarInconsistenciaEspecifico(v) == false) return;

                    //cadastrar a jogada
                    int idJogadaDefensiva=saveJD();
                    mDb.cadastrarDefSaida(idJogadaDefensiva, mSpinTipoSaida.getSelectedItem().toString(), mSpinMotivoSaida.getSelectedItem().toString());
                    if(tipoFinalizacao == "chute bola rolando") {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE CHUTE (tentou defesa com saída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SAÍDA EM CHUTE:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CHUTE:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CHUTE:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nAcertou a jogada\n\n";
                                }
                            }
                        }
                    } else if (tipoFinalizacao == "cabeceio") {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com saída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SAÍDA EM CABECEIO:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    } else {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SAÍDA EM CRUZAMENTO/LANÇAMENTO:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CRUZAMENTO/LANÇAMENTO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com saída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SAÍDA EM CRUZAMENTO/LANÇAMENTO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoSaida.getSelectedItem().toString() + "\nMotivo de saída: " + mSpinMotivoSaida.getSelectedItem().toString() + "\nSetor atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
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

    }

    private boolean verificarInconsistenciaEspecifico(View v) {
        if ((mSpinMotivoSaida.getSelectedItem().equals("cruzamento/lançamento bola rolando") || mSpinMotivoSaida.getSelectedItem().equals("falta")) && (mSpinSetorBolaVeio.getSelectedItem() == Constantes.SETOR_ED || mSpinSetorBolaVeio.getSelectedItem() == Constantes.SETOR_EE)) {
            Mensagem msg = new Mensagem();
            msg.alertaDefSaidaRolando(v.getContext());
            return false;
        }


        if ((mSpinMotivoSaida.getSelectedItem().equals("escanteio")) && (mSpinSetorBolaVeio.getSelectedItem() != Constantes.SETOR_ED && mSpinSetorBolaVeio.getSelectedItem() != Constantes.SETOR_EE)) {
            Mensagem msg = new Mensagem();
            msg.alertaDefSaidaEscanteio(v.getContext());
            return false;
        }

        return true;
    }

    private void carregarValores() {

        tempos = new ArrayList<String>();
        tempos.add("Selecione o tempo de jogo");
        for (int i = 0; i < 91; i++) tempos.add(i + "'");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempos);
        mSpinTempo.setAdapter(adapter1);

        arraySetorBolaFoi = Constantes.getListSetoresCampo(Constantes.labelDestino);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaFoi);
        mSpinSetorBolaFoi.setAdapter(adapter2);

        arraySetorBolaVeio = new ArrayList<String>();
        arraySetorBolaVeio.add("Selecione o setor "+ Constantes.labelorigem+" da bola");
        arraySetorBolaVeio.add(Constantes.SETOR_DE);
        arraySetorBolaVeio.add(Constantes.SETOR_ADE);
        arraySetorBolaVeio.add(Constantes.SETOR_ADC);
        arraySetorBolaVeio.add(Constantes.SETOR_PAD);
        arraySetorBolaVeio.add(Constantes.SETOR_ADD);
        arraySetorBolaVeio.add(Constantes.SETOR_DD);
        arraySetorBolaVeio.add(Constantes.SETOR_MDE);
        arraySetorBolaVeio.add(Constantes.SETOR_MDC);
        arraySetorBolaVeio.add(Constantes.SETOR_MDD);
        arraySetorBolaVeio.add(Constantes.SETOR_MOE);
        arraySetorBolaVeio.add(Constantes.SETOR_MOC);
        arraySetorBolaVeio.add(Constantes.SETOR_MOD);
        arraySetorBolaVeio.add(Constantes.SETOR_OE);
        arraySetorBolaVeio.add(Constantes.SETOR_AOE);
        arraySetorBolaVeio.add(Constantes.SETOR_AOC);
        arraySetorBolaVeio.add(Constantes.SETOR_PAO);
        arraySetorBolaVeio.add(Constantes.SETOR_AOD);
        arraySetorBolaVeio.add(Constantes.SETOR_OD);
        arraySetorBolaVeio.add(Constantes.SETOR_EE);
        arraySetorBolaVeio.add(Constantes.SETOR_ED);

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
