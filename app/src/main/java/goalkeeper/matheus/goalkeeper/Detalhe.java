package goalkeeper.matheus.goalkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bd.DBManager;

public class Detalhe extends AppCompatActivity {
    public static TextView txtDetalhes;
    Button btnVoltar;
    DBManager mDb;
    int idPartida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        mDb = new DBManager(this);
        idPartida = getIntent().getIntExtra("ID_PARTIDA", 0);

        txtDetalhes = (TextView) findViewById(R.id.txt_detalhes);

        txtDetalhes.setText(mDb.getDetalhesPartida(idPartida));


        btnVoltar = (Button) findViewById(R.id.btn_voltarDetalhes);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
