package goalkeeper.matheus.goalkeeper.view.jogadas;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.util.Constantes;
import goalkeeper.matheus.goalkeeper.view.CadastroPartida;
import goalkeeper.matheus.goalkeeper.util.Mensagem;
import goalkeeper.matheus.goalkeeper.R;

public class DefCaidaTela extends JogadaDefensivaTela {
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arraySetorBolaVeio;
    ArrayList<String> arrayTipoFinalizacao;
    ArrayList<String> arrayTipoDefCaida;
    Spinner mSpinTipoDefCaida;
    DBManager mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_caida_tela);
        mDb = new DBManager(this);

        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJD);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoi);
        mSpinSetorBolaVeio = (Spinner) findViewById(R.id.spinner_setorBolaVeio);
        mSpinTipoFinalizacao = (Spinner) findViewById(R.id.spinner_tipoFinalizacao);
        mSpinTipoDefCaida = (Spinner) findViewById(R.id.spinner_tipoDefesaCaida);
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
                if(testePreenchimento() && mSpinTipoDefCaida.getSelectedItemPosition()>0){
                    //verificar inconsistencias
                    if(verificarInconsistencia(v) == false) return;

                    //cadastrar a jogada
                    int idJogadaDefensiva=saveJD();
                    mDb.cadastrarDefCaida(idJogadaDefensiva, mSpinTipoDefCaida.getSelectedItem().toString());
                    if(tipoFinalizacao == Constantes.FINALIZACAO_PENALTI) {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE PÊNALTI (tentou defesa com caída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM CAÍDA:\n" +
                                        "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE PÊNALTI (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA:\n" +
                                            "Tempo:" + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE PÊNALTI (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    }else if (tipoFinalizacao == Constantes.FINALIZACAO_CABECEIO) {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com caída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM CAÍDA EM CABECEIO:\n" +
                                        "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if(observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de saída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE CABECEIO (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM SAÍDA EM CABECEIO:\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    } else if (tipoFinalizacao == Constantes.FINALIZACAO_FALTA) {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa com caída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM CAÍDA EM FALTA:\n" +
                                        "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA EM FALTA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA EM FALTA:\n" +
                                            "Tempo: " + tempo + "\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            }
                        }
                    } else {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa com caída):\n" +
                                        "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM CAÍDA:\n" +
                                        "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\n" + "Status: Errou na jogada\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (observacao.isEmpty()) {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA:\n" +
                                            "Tempo:" + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nStatus: Acertou a jogada\n\n";
                                }
                            } else {
                                if (gol == 1) {
                                    CadastroPartida.historico += "SOFREU GOL (tentou defesa com caída):\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
                                } else {
                                    CadastroPartida.historico += "DEFESA COM CAÍDA:\n" +
                                            "Tempo: " + tempo + "'\nTipo de caída: " + mSpinTipoDefCaida.getSelectedItem().toString() + "\nSetor do gol atingido: " + setorBolaFoi + "\nOrigem da bola: " + setorBolaVeio + "\nTipo finalização: " + tipoFinalizacao + "\nObservação: " + observacao + "\nStatus: Acertou a jogada\n\n";
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

        arrayTipoDefCaida = new ArrayList<String>();
        arrayTipoDefCaida.add("Selecione o tipo de caída");
        arrayTipoDefCaida.add("mão direita");
        arrayTipoDefCaida.add("mão esquerda");
        arrayTipoDefCaida.add("duas mãos");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoDefCaida);
        mSpinTipoDefCaida.setAdapter(adapter5);
    }

}
