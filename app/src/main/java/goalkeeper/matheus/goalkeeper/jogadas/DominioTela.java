package goalkeeper.matheus.goalkeeper.jogadas;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        btnCancelarJO = (Button) findViewById(R.id.btn_calcelJO);
        mSpinTipoDominio = (Spinner) findViewById(R.id.spinner_tipoDominio);
        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJO);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoiJO);
        mSpinPrimeiraBola = (Spinner) findViewById(R.id.spinner_primBola);
        mSpinSegundaBola = (Spinner) findViewById(R.id.spinner_segBola);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erroJO);
        mTextObservacao = (EditText) findViewById(R.id.edit_txt_observacao);
        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        btnCancelarJO = (Button) findViewById(R.id.btn_calcelJO);

        carregarValores();

        btnSalvarJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testePreenchimento() && mSpinTipoDominio.getSelectedItemId()>0){
                    //cadastrar a jogada
                    int idJogadaOfensiva=saveJO();
                    mDb.cadastrarDominio(idJogadaOfensiva,mSpinTipoDominio.getSelectedItem().toString());
                    if(errou==1)CadastroPartida.historico += "DOMÍNIO:\n"+
                            tempo+" minutos, domínio foi do tipo "+ mSpinTipoDominio.getSelectedItem().toString() + "\nObservação: "+observacao+ "\n\n";

                    if(observacao.isEmpty()) {
                        if (errou == 0) CadastroPartida.historico += "DOMÍNIO:\n" +
                                tempo + " minutos, domínio foi do tipo " + mSpinTipoDominio.getSelectedItem().toString() + "\nAcertou a jogada\n\n";
                    } else {
                        if (errou == 0) CadastroPartida.historico += "DOMÍNIO:\n" +
                                tempo + " minutos, domínio foi do tipo " + mSpinTipoDominio.getSelectedItem().toString() + "\nObservação: " + observacao + "\nAcertou a jogada\n\n";
                    }

                    finish();
                }else{
                    mensagem();
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
        mSpinSetorBolaFoi.setSelection(1);
        mSpinSetorBolaFoi.setVisibility(View.GONE);

        arrayPrimBola = new ArrayList<String>();
        arrayPrimBola.add("Selecione quem ganhou a primeira bola");
        arrayPrimBola.add("Companheiro");
        arrayPrimBola.add("Adversario");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayPrimBola);
        mSpinPrimeiraBola.setAdapter(adapter3);
        mSpinPrimeiraBola.setSelection(1);
        mSpinPrimeiraBola.setVisibility(View.GONE);

        arraySegBola = new ArrayList<String>();
        arraySegBola.add("Selecione quem ganhou a segunda bola");
        arraySegBola.add("Companheiro");
        arraySegBola.add("Adversario");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraySegBola);
        mSpinSegundaBola.setAdapter(adapter4);
        mSpinSegundaBola.setSelection(1);
        mSpinSegundaBola.setVisibility(View.GONE);
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

