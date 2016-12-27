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

public class DefSobreCabecaTela extends JogadaDefensivaTela {
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arraySetorBolaVeio;
    ArrayList<String> arrayTipoFinalizacao;
    ArrayList<String> arrayTipoDefPunho;
    Spinner mSpinTipoDefSobreCabeca;
    DBManager mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_sobre_cabeca_tela);
        mDb = new DBManager(this);


        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJD);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoi);
        mSpinSetorBolaVeio = (Spinner) findViewById(R.id.spinner_setorBolaVeio);
        mSpinTipoFinalizacao = (Spinner) findViewById(R.id.spinner_tipoFinalizacao);
        mSpinTipoDefSobreCabeca = (Spinner) findViewById(R.id.spinner_tipoDefesaSobreCabeca);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erro);
        mTextObservacao = (EditText) findViewById(R.id.edit_txt_observacao);
        btnSalvarJD = (Button) findViewById(R.id.btn_salvarJD);
        mCheckGol = (CheckBox) findViewById(R.id.check_gol);

        carregarValores();

        btnSalvarJD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoDefSobreCabeca.getSelectedItemPosition()>0) {
                    //verificar o setor do penalti
                    if(mSpinTipoFinalizacao.getSelectedItem() == Constantes.FINALIZACAO_PENALTI) {
                        if(mSpinSetorBolaVeio.getSelectedItem() != Constantes.SETOR_ADC) {
                            Mensagem msg = new Mensagem();
                            msg.alertaPenalti(v.getContext());
                            return;
                        }
                    }
                    //cadastrar a jogada
                    int idJogadaDefensiva = saveJD();
                    mDb.cadastrarDefSobreCabeca(idJogadaDefensiva, mSpinTipoDefSobreCabeca.getSelectedItem().toString());
                    if (tipoFinalizacao == Constantes.FINALIZACAO_PENALTI) {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE PÊNALTI (tentou defesa sobre a cabeça):\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString()+ "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SOBRE A CABEÇA:\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE PÊNALTI (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE PÊNALTI (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    }else if (tipoFinalizacao == Constantes.FINALIZACAO_CABECEIO) {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa sobre a cabeça):\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SOBRE A CABEÇA EM CABECEIO:\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE CABEÇA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    } else if (tipoFinalizacao == Constantes.FINALIZACAO_FALTA) {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa sobre a cabeça):\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça:" + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SOBRE A CABEÇA EM FALTA:\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça:" + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça:" + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA EM FALTA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça:" + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça:" + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA EM FALTA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça:" + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    }else {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa sobre a cabeça):\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA SOBRE A CABEÇA:\n" +
                                        "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa sobre a cabeça):\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA SOBRE A CABEÇA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de defesa sobre a cabeça: " + mSpinTipoDefSobreCabeca.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
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

    private void carregarValores() {
        tempos = new ArrayList<String>();
        tempos.add("Selecione o tempo de jogo");
        for(int i=0;i<91;i++) tempos.add(i+"'");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempos);
        mSpinTempo.setAdapter(adapter1);

        arraySetorBolaFoi = Constantes.getListSetoresGol();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaFoi);
        mSpinSetorBolaFoi.setAdapter(adapter2);

        arraySetorBolaVeio = Constantes.getListSetoresCampo(Constantes.labelorigem);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaVeio);
        mSpinSetorBolaVeio.setAdapter(adapter3);

        arrayTipoFinalizacao = Constantes.getListTiposFinalizacao();
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoFinalizacao);
        mSpinTipoFinalizacao.setAdapter(adapter4);

        arrayTipoDefPunho = new ArrayList<String>();
        arrayTipoDefPunho.add("Selecione o tipo de Defesa Sobre a Cabeça");
        arrayTipoDefPunho.add("mão direita");
        arrayTipoDefPunho.add("mão esquerda");
        arrayTipoDefPunho.add("mão trocada");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoDefPunho);
        mSpinTipoDefSobreCabeca.setAdapter(adapter5);
    }
}

