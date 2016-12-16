package goalkeeper.matheus.goalkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class  FinalizarPartida extends AppCompatActivity {
    Button mBtnContinuar;
    Button mBtnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_partida);

        mBtnCancelar = (Button) findViewById(R.id.btn_naoFinalizar);
        mBtnContinuar = (Button) findViewById(R.id.btn_Finalizar);

        mBtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroPartida.mFinlizar = true;
                finish();
            }
        });

        mBtnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroPartida.mFinlizar = true;
                finish();
            }
        });
    }
}
