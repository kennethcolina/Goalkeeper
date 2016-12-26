package goalkeeper.matheus.goalkeeper.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import model.Goleiro;

/**
 * Created by kenneth on 02/12/16.
 */
public class AlteraGoleiro extends AppCompatActivity {

    private Button mSave;
    private int idGoleiro;
    private EditText mNome;
    private EditText mData;
    private Goleiro mGoleiro;
    private DBManager mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_goleiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSave = (Button) findViewById(R.id.btn_salvarGoleiro);
        mNome = (EditText) findViewById(R.id.edit_txt_nome_goleiro);
        mData = (EditText) findViewById(R.id.edit_txt_nascimento_goleiro);
        mData.addTextChangedListener(tw);

        mDb = new DBManager(this);
        idGoleiro = this.getIntent().getIntExtra("ID_GOLEIRO", 0);
        final Goleiro goleiro = mDb.getGoleiro(idGoleiro);
        mNome.setText(goleiro.getNome());
        mData.setText(goleiro.getDataNascimento());

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!TextUtils.isEmpty(mNome.getText().toString()) && !TextUtils.isEmpty(mData.getText().toString())) {
                   mGoleiro = new Goleiro(goleiro.getId(), mNome.getText().toString(), mData.getText().toString());
                   mDb.AlteraGoleiro(mGoleiro);
                   finish();
               }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                mData.setText(current);
                mData.setSelection(sel < current.length() ? sel : current.length());
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
