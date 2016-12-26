package goalkeeper.matheus.goalkeeper.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import model.Goleiro;
import model.Partida;

public class Detalhe extends AppCompatActivity {
    public static TextView txtDetalhes;
    TextView descricaoPartida;
    TextView nomeGoleiro;
    TextView dataPartida;
    DBManager mDb;
    Partida partida;
    Goleiro goleiro;
    int idPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = new DBManager(this);
        idPartida = getIntent().getIntExtra("ID_PARTIDA", 0);

        txtDetalhes = (TextView) findViewById(R.id.txt_detalhes);
        descricaoPartida = (TextView) findViewById(R.id.txt_detalhes_descricao);
        nomeGoleiro = (TextView) findViewById(R.id.txt_detalhes_goleiro);
        dataPartida = (TextView) findViewById(R.id.txt_detalhes_data);

        partida = mDb.getPartida(idPartida);
        goleiro = mDb.getGoleiro(partida.getIdGoleiro());

        txtDetalhes.setText(mDb.getDetalhesPartida(idPartida));
        descricaoPartida.setText("Partida: " + partida.getDescricao());
        nomeGoleiro.setText("Goleiro: " + goleiro.getNome());
        dataPartida.setText("Data: " + partida.getData() + "\n\n");
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
}
