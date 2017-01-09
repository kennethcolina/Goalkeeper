package goalkeeper.matheus.goalkeeper.view.jogadas;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import model.JogadaOfensiva;

public class JogadaOfensivaTela extends AppCompatActivity {

    public  int idPartida = 1;
    public int idJogadaOfensiva = 0;
    public int tempo;
    public String setorBolaFoi;
    public String primeiraBola;
    public String segundaBola;
    public int errou;
    public String observacao;

    public boolean mPreenchido = false;
    public DBManager mDb;

    public Spinner mSpinTempo;
    public Spinner mSpinSetorBolaFoi;
    public Spinner mSpinPrimeiraBola;
    public LinearLayout mLinearSegBola;
    public LinearLayout mLinearPrimBola;
    public Spinner mSpinSegundaBola;
    public CheckBox mCheckErrou;
    public EditText mTextObservacao;
    public Button btnSalvarJO;
    public Button mBtnSetorCampo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogada_ofensiva_tela);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        mDb = new DBManager(this);
    }

    public boolean testePreenchimento(){
        if( mSpinTempo.getSelectedItemId()>0 && mSpinSetorBolaFoi.getSelectedItemId()>0 && mSpinPrimeiraBola.getSelectedItemId()>0 && mSpinSegundaBola.getSelectedItemId()>0){
            if(mCheckErrou.isChecked() == true) {
                if (!mTextObservacao.getText().toString().isEmpty()) {
                    mPreenchido = true;
                } else {
                    mPreenchido = false;
                }
            }else{
                mPreenchido = true;
            }
        }else{
            mPreenchido=false;
        }
        return mPreenchido;
    }

    public void getDados() {
        tempo = Integer.parseInt(mSpinTempo.getSelectedItem().toString().substring(0,mSpinTempo.getSelectedItem().toString().indexOf("'")));
        setorBolaFoi = mSpinSetorBolaFoi.getSelectedItem().toString();
        primeiraBola = mSpinPrimeiraBola.getSelectedItem().toString();
        segundaBola = mSpinSegundaBola.getSelectedItem().toString();
        observacao = mTextObservacao.getText().toString();
        if(mCheckErrou.isChecked())errou =1 ; else errou = 0;
    }

    public int saveJO(){
        if(testePreenchimento()) {
            getDados();
            idPartida = mDb.getMaxIdPartida();//procurar id da ultima partida inserida e colocar no idPartida
            Log.d("theus",""+idPartida);
            JogadaOfensiva jo = new JogadaOfensiva(idPartida, tempo, setorBolaFoi, primeiraBola, segundaBola, errou, observacao);
            mDb.cadastrarJO(jo);
            idJogadaOfensiva = mDb.getMaxIdJogadaOfensiva();
            Log.d("theus",""+idJogadaOfensiva);
            return idJogadaOfensiva;
        }else{
            return 0;
        }
    }

    public void imagemAjuda(int setor){
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(setor);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
