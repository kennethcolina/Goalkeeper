package goalkeeper.matheus.goalkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VerParciais extends AppCompatActivity {
    public static TextView listViewHistorico;
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_parciais);

        listViewHistorico = (TextView) findViewById(R.id.list_parciais_historico);

        listViewHistorico.setText(CadastroPartida.historico);


        btnVoltar = (Button) findViewById(R.id.btn_voltarParciais);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
