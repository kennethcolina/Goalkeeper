package goalkeeper.matheus.goalkeeper.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import goalkeeper.matheus.goalkeeper.util.Mensagem;
import goalkeeper.matheus.goalkeeper.view.graph.RelatorioTaticoActivity;
import model.Partida;

public class CadastroPartida extends AppCompatActivity {
    Button mBtnCadastrarJogada;
    EditText mEditTextDescricao;
    EditText mEditTextData;
    Spinner mSpinnerGoleiros;
    DBManager mDb;
    ArrayList<String> goleiros;
    Partida mPartida;
    Button mBtnSalvarPartida;
    Button mBtnProxPartida;
    TextView mTxtInfosPartida;
    TextView mTxtdata;
    TextView mTxtdesc;
    TextView mTxtgoleiro;
    Button mBtnParciais;
    Button mBtnParciaisTatica;
    LinearLayout mLinerLayout;
    public static boolean mFinlizar = false;
    public static String historico = "";
    //boolean jaSdicionadaNessaPartida = false;
    int idGoleiro=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_partida);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = new DBManager(this);

        mEditTextDescricao = (EditText) findViewById(R.id.edit_txt_descricao);
        mEditTextData = (EditText) findViewById(R.id.edit_txt_dataPartida);
        mSpinnerGoleiros = (Spinner) findViewById(R.id.spinner_goleirosPartida);
        mBtnCadastrarJogada  = (Button) findViewById(R.id.btn_cadastrarJogada);
        mBtnSalvarPartida = (Button) findViewById(R.id.btn_salvarPartida);
        mBtnProxPartida = (Button) findViewById(R.id.btn_proxPartida);
        mTxtInfosPartida = (TextView) findViewById(R.id.infos_partida);
        mBtnParciais = (Button) findViewById(R.id.btn_parciaisPartida);
        mTxtdata = (TextView) findViewById(R.id.label_data);
        mTxtdesc = (TextView) findViewById(R.id.label_desc);
        mTxtgoleiro = (TextView) findViewById(R.id.label_goleiro);
        mLinerLayout = (LinearLayout) findViewById(R.id.dados_partida_cadastro);
        mBtnParciaisTatica = (Button) findViewById(R.id.btn_parciaisPartidaTatica);

        goleiros = new ArrayList<String>();
        goleiros.add("Selecione um goleiro");
        for (int i = 0; i < mDb.getGoleiros().size(); i++) {
            goleiros.add(mDb.getGoleiros().get(i).getId() + ": " + mDb.getGoleiros().get(i).getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, goleiros);
        mSpinnerGoleiros.setAdapter(adapter);


        mBtnCadastrarJogada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTipoJogada();
            }
        });

        mBtnSalvarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.cadastrarHistorico(historico, mDb.getMaxIdPartida());
                historico = "";
                finish();
            }
        });

        mBtnProxPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpinnerGoleiros.getSelectedItemId() > 0 && !TextUtils.isEmpty(mEditTextDescricao.getText().toString()) && !mEditTextData.getText().toString().endsWith(" ")
                        && !TextUtils.isEmpty(mEditTextData.getText().toString())) {
                    String desc = mEditTextDescricao.getText().toString();
                    String data = mEditTextData.getText().toString();
                    idGoleiro = Integer.parseInt(mSpinnerGoleiros.getSelectedItem().toString().substring(0, mSpinnerGoleiros.getSelectedItem().toString().indexOf(":")));

                    mPartida = new Partida(idGoleiro, data, desc);
                    //historico +="DADOS PARTIDA:\nPartida: "+mEditTextDescricao.getText().toString()+"\nGoleiro: "+mSpinnerGoleiros.getSelectedItem().toString()+"\nData: "+ mEditTextData.getText().toString()+"\n\n";
                    //Log.d("theus", ""+Integer.parseInt(idGoleiro));
                    mDb.cadastrarPartida(mPartida);

                    mEditTextDescricao.setVisibility(View.GONE);
                    mEditTextData.setVisibility(View.GONE);
                    mSpinnerGoleiros.setVisibility(View.GONE);
                    mBtnProxPartida.setVisibility(View.GONE);
                    mTxtdata.setVisibility(View.GONE);
                    mTxtdesc.setVisibility(View.GONE);
                    mTxtgoleiro.setVisibility(View.GONE);

                    mTxtInfosPartida.setText("Partida: "+mEditTextDescricao.getText().toString() +"\nData: " + mEditTextData.getText().toString() + "\nGoleiro: " +mSpinnerGoleiros.getSelectedItem().toString().substring(mSpinnerGoleiros.getSelectedItem().toString().indexOf(":")+2, mSpinnerGoleiros.getSelectedItem().toString().length()));
                    mTxtInfosPartida.setVisibility(View.VISIBLE);
                    mLinerLayout.setVisibility(View.VISIBLE);

                    mBtnSalvarPartida.setVisibility(View.VISIBLE);
                    mBtnCadastrarJogada.setVisibility(View.VISIBLE);
                    mBtnParciais.setVisibility(View.VISIBLE);
                    mBtnParciaisTatica.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }else{
                    Mensagem.alerta(v.getContext());
                }
            }
        });

        mBtnParciais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent parciais = new Intent(getApplicationContext(), VerParciais.class);
                startActivity(parciais);
            }
        });

        mBtnParciaisTatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tatica = new Intent(getApplicationContext(), RelatorioTaticoActivity.class);
                tatica.putExtra("TITLE", "Relatório Tático Parcial");
                tatica.putExtra("ID_GOLEIRO", idGoleiro);
                tatica.putExtra("ID_PARTIDA", mDb.getMaxIdPartida());
                startActivity(tatica);
            }
        });

        mEditTextData.addTextChangedListener(tw);
    }

    private void dialogTipoJogada() {
        Intent tipoJogada = new Intent(this, DialogTipoJogada.class);
        startActivity(tipoJogada);
    }

    TextWatcher tw = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "        ";
        private Calendar cal = Calendar.getInstance();
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                mEditTextData.setText(current);
                mEditTextData.setSelection(sel < current.length() ? sel : current.length());
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };

}
