package goalkeeper.matheus.goalkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VerParciais extends AppCompatActivity {
    public static TextView listViewHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_parciais);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewHistorico = (TextView) findViewById(R.id.list_parciais_historico);

        listViewHistorico.setText(CadastroPartida.historico);

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
