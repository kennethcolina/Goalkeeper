package goalkeeper.matheus.goalkeeper.jogadas;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import bd.DBManeger;
import goalkeeper.matheus.goalkeeper.R;
import model.JogadaDefensiva;
import model.JogadaOfensiva;

public class JogadaDefensivaTela extends AppCompatActivity {
    public static int idPartida;
    public static int tempo;
    public static String setorBolaVeio;
    public static String setorBolaFoi;
    public static int errou;
    public static String erro;
    public static String tipoFinalizacao;
    public static int gol;

    public int idJogadaDefensiva;

    public Spinner mSpinTempo;
    public Spinner mSpinSetorBolaVeio;
    public Spinner mSpinTipoFinalizacao;
    public Spinner mSpinSetorBolaFoi;
    public CheckBox mCheckGol;
    public CheckBox mCheckErrou;
    public Spinner mSpinErro;
    public Button btnSalvarJD;
    public Button btnCancelarJD;

    public boolean mPreenchido = false;
    public DBManeger mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogada_defensiva_tela);

        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJD);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoi);
        mSpinSetorBolaVeio = (Spinner) findViewById(R.id.spinner_setorBolaVeio);
        mSpinTipoFinalizacao = (Spinner) findViewById(R.id.spinner_tipoFinalizacao);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erro);
        mSpinErro = (Spinner) findViewById(R.id.spinner_erroJD);
        btnSalvarJD = (Button) findViewById(R.id.btn_salvarJD);
        btnCancelarJD = (Button) findViewById(R.id.btn_calcelJD);
        mCheckGol = (CheckBox) findViewById(R.id.check_gol);

        mDb = new DBManeger(this);
    }

    public boolean testePreenchimento() {
        if (mSpinTempo.getSelectedItemId() > 0 && mSpinSetorBolaFoi.getSelectedItemId() > 0 && mSpinSetorBolaVeio.getSelectedItemId() > 0 && mSpinTipoFinalizacao.getSelectedItemId() > 0) {
            if (mCheckErrou.isChecked() == true) {
                if (mSpinErro.getSelectedItemId() > 0) {
                    mPreenchido = true;
                } else {
                    mPreenchido = false;
                }
            } else {
                mPreenchido = true;
            }
        } else {
            mPreenchido = false;
        }
        return mPreenchido;
    }

    public void getDados() {
        tempo = Integer.parseInt(mSpinTempo.getSelectedItem().toString().substring(0, mSpinTempo.getSelectedItem().toString().indexOf("'")));
        setorBolaFoi = mSpinSetorBolaFoi.getSelectedItem().toString();
        setorBolaVeio = mSpinSetorBolaVeio.getSelectedItem().toString();
        tipoFinalizacao = mSpinTipoFinalizacao.getSelectedItem().toString();
        erro = mSpinErro.getSelectedItem().toString();
        if (mCheckErrou.isChecked()) errou = 1;
        else errou = 0;
        if (mCheckGol.isChecked()) gol = 1;
        else gol = 0;
    }

    public int saveJD() {
        if (testePreenchimento()) {
            getDados();
            idPartida = mDb.getMaxIdPartida();//procurar id da ultima partida inserida e colocar no idPartida
            Log.d("theus", "" + idPartida);
            JogadaDefensiva jd = new JogadaDefensiva(idPartida, tempo, setorBolaVeio, setorBolaFoi, errou, erro, tipoFinalizacao, gol);
            mDb.cadastrarJD(jd);
            idJogadaDefensiva = mDb.getMaxIdJogadaDefensiva();
            Log.d("theus", "" + idJogadaDefensiva);
            return idJogadaDefensiva;
        } else {
            return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jogada, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ajuda1) {
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
            imageView.setImageResource(R.drawable.campo_setores);
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            builder.show();

            return true;
        } else if (id == R.id.ajuda2) {
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
            imageView.setImageResource(R.drawable.gol_setores);
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
