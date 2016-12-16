package goalkeeper.matheus.goalkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bd.DBManager;
import model.Goleiro;
import model.Partida;

public class Detalhe extends AppCompatActivity {
    public static TextView txtDetalhes;
    TextView dadosPartida;
    TextView descricaoPartida;
    TextView nomeGoleiro;
    TextView dataPartida;
    Button btnVoltar;
    DBManager mDb;
    Partida partida;
    Goleiro goleiro;
    int idPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        mDb = new DBManager(this);
        idPartida = getIntent().getIntExtra("ID_PARTIDA", 0);

        txtDetalhes = (TextView) findViewById(R.id.txt_detalhes);
        dadosPartida = (TextView) findViewById(R.id.txt_dados_partida);
        descricaoPartida = (TextView) findViewById(R.id.txt_detalhes_descricao);
        nomeGoleiro = (TextView) findViewById(R.id.txt_detalhes_goleiro);
        dataPartida = (TextView) findViewById(R.id.txt_detalhes_data);

        partida = mDb.getPartida(idPartida);
        goleiro = mDb.getGoleiro(partida.getIdGoleiro());

        txtDetalhes.setText(mDb.getDetalhesPartida(idPartida));
        descricaoPartida.setText("Partida: " + partida.getDescricao());
        nomeGoleiro.setText("Goleiro: " + goleiro.getNome());
        dataPartida.setText("Data: " + partida.getData() + "\n\n");

        btnVoltar = (Button) findViewById(R.id.btn_voltarDetalhes);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
