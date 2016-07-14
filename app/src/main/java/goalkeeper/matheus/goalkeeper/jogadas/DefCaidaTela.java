package goalkeeper.matheus.goalkeeper.jogadas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import goalkeeper.matheus.goalkeeper.R;
import model.JogadaDefensiva;

public class DefCaidaTela extends JogadaDefensivaTela {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_caida_tela);
    }
}
