package goalkeeper.matheus.goalkeeper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class IniciarAvaliacao extends Fragment {
    View mView;
    Button mBtnIniciarPartida;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_iniciar_avaliacao, container, false);
        mBtnIniciarPartida = (Button) mView.findViewById(R.id.btn_iniciarAvaliacao);

        mBtnIniciarPartida.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cadastrarPartida = new Intent(getActivity(), CadastroPartida.class);
                startActivity(cadastrarPartida);

            }
        });

        return mView;
    }
}
