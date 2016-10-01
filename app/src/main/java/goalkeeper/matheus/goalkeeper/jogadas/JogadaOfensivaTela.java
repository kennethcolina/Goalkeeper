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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import bd.DBManeger;
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
    public String erro;

    public boolean mPreenchido = false;
    public DBManeger mDb;

    public Spinner mSpinTempo;
    public Spinner mSpinSetorBolaFoi;
    public Spinner mSpinPrimeiraBola;
    public Spinner mSpinSegundaBola;
    public CheckBox mCheckErrou;
    public Spinner mSpinErro;
    public Button btnSalvarJO;
    public Button btnCancelarJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogada_ofensiva_tela);

        mSpinTempo = (Spinner) findViewById(R.id.spinner_tempoJO);
        mSpinSetorBolaFoi = (Spinner) findViewById(R.id.spinner_setorBolaFoiJO);
        mSpinPrimeiraBola = (Spinner) findViewById(R.id.spinner_primBola);
        mSpinSegundaBola = (Spinner) findViewById(R.id.spinner_segBola);
        mCheckErrou = (CheckBox) findViewById(R.id.check_erroJO);
        mSpinErro = (Spinner) findViewById(R.id.spinner_erroJO);
        btnSalvarJO = (Button) findViewById(R.id.btn_salvarJO);
        btnCancelarJO = (Button) findViewById(R.id.btn_calcelJO);

        mDb = new DBManeger(this);
    }

    public boolean testePreenchimento(){
        if( mSpinTempo.getSelectedItemId()>0 && mSpinSetorBolaFoi.getSelectedItemId()>0 && mSpinPrimeiraBola.getSelectedItemId()>0 && mSpinSegundaBola.getSelectedItemId()>0){
            if(mCheckErrou.isChecked() == true) {
                if (mSpinErro.getSelectedItemId() > 0) {
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

    public void getDados(){
        tempo = Integer.parseInt(mSpinTempo.getSelectedItem().toString().substring(0,mSpinTempo.getSelectedItem().toString().indexOf("'")));
        setorBolaFoi = mSpinSetorBolaFoi.getSelectedItem().toString();
        primeiraBola = mSpinPrimeiraBola.getSelectedItem().toString();
        segundaBola = mSpinSegundaBola.getSelectedItem().toString();
        erro = mSpinErro.getSelectedItem().toString();
        if(mCheckErrou.isChecked())errou =1 ; else errou = 0;
    }

    public int saveJO(){
        if(testePreenchimento()) {
            getDados();
            idPartida = mDb.getMaxIdPartida();//procurar id da ultima partida inserida e colocar no idPartida
            Log.d("theus",""+idPartida);
            JogadaOfensiva jo = new JogadaOfensiva(idPartida, tempo, setorBolaFoi, primeiraBola, segundaBola, errou, erro);
            mDb.cadastrarJO(jo);
            idJogadaOfensiva = mDb.getMaxIdJogadaOfensiva();
            Log.d("theus",""+idJogadaOfensiva);
            return idJogadaOfensiva;
        }else{
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
        }else if (id == R.id.ajuda2) {
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