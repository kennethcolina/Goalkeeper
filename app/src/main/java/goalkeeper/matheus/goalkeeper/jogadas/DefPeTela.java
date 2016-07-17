package goalkeeper.matheus.goalkeeper.jogadas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
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

public class DefPeTela extends JogadaDefensivaTela {
    ArrayList<String> tempos;
    ArrayList<String> arraySetorBolaFoi;
    ArrayList<String> arraySetorBolaVeio;
    ArrayList<String> arrayTipoFinalizacao;
    ArrayList<String> arrayErros;
    DBManeger mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_pe_tela);
        mDb = new DBManeger(this);


        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJD);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoi);
        mSpinSetorBolaVeio = (Spinner) findViewById(R.id.spinner_setorBolaVeio);
        mSpinTipoFinalizacao = (Spinner) findViewById(R.id.spinner_tipoFinalizacao);
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
                if(testePreenchimento()){
                    //cadastrar a jogada
                    int idJogadaDefensiva=saveJD();
                    mDb.cadastrarDefPe(idJogadaDefensiva);
                    if(tipoFinalizacao != "chute de falta") {
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa com pé):\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM PÉ:\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL (tentou defesa com pé):\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM PÉ:\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            }
                        }
                    }else{
                        if (errou == 1) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE FALTA (tentou defesa com pé):\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM PÉ EM FALTA:\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nErro: " + erro + "\n\n";
                            }
                        }
                        if (errou == 0) {
                            if (gol == 1) {
                                CadastroPartida.historico += "SOFREU GOL DE FALTA(tentou defesa com pé):\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
                            } else {
                                CadastroPartida.historico += "DEFESA COM PÉ EM FALTA:\n" +
                                        tempo + " minutos, bola foi no setor " + setorBolaFoi + "do gol e veio do setor " + setorBolaVeio + ", finalização do tipo " + tipoFinalizacao + "\nAcertou a jogada\n\n";
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
        arraySetorBolaFoi.add("1");
        arraySetorBolaFoi.add("2");
        arraySetorBolaFoi.add("3");
        arraySetorBolaFoi.add("4");
        arraySetorBolaFoi.add("5");
        arraySetorBolaFoi.add("6");
        arraySetorBolaFoi.add("-1");
        arraySetorBolaFoi.add("-2");
        arraySetorBolaFoi.add("-3");
        arraySetorBolaFoi.add("-4");
        arraySetorBolaFoi.add("-6");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySetorBolaFoi);
        mSpinSetorBolaFoi.setAdapter(adapter2);

        arraySetorBolaVeio = new ArrayList<String>();
        arraySetorBolaVeio.add("Selecione o setor onde a bola veio");
        arraySetorBolaVeio.add("DE");
        arraySetorBolaVeio.add("DC");
        arraySetorBolaVeio.add("DD");
        arraySetorBolaVeio.add("MDE");
        arraySetorBolaVeio.add("MDC");
        arraySetorBolaVeio.add("MDD");
        arraySetorBolaVeio.add("MOE");
        arraySetorBolaVeio.add("MOC");
        arraySetorBolaVeio.add("MOD");
        arraySetorBolaVeio.add("OE");
        arraySetorBolaVeio.add("OC");
        arraySetorBolaVeio.add("OD");
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
        arrayErros.add("bola passou");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayErros);
        mSpinErro.setAdapter(adapter5);
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
