package goalkeeper.matheus.goalkeeper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import bd.DBManager;

public class PartidaDetalhes extends Fragment {
    View mView;
    DBManager mDb;
    Spinner mSpinnerGoleiros;
    ListView mListPartidas;
    ArrayList<String> goleiros;
    ArrayList<String> partidas;
    int idGoleiro=0;
    public static String detalhe="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_partida_detalhes, container, false);

        mDb = new DBManager(getActivity());

        mSpinnerGoleiros = (Spinner) mView.findViewById(R.id.spinner_goleirosDetalhesPartidas);
        mListPartidas = (ListView) mView.findViewById(R.id.list_partidas);

        goleiros = new ArrayList<String>();
        goleiros.add("Selecione um goleiro");
        for (int i = 0; i < mDb.getGoleiros().size(); i++) {
            goleiros.add(mDb.getGoleiros().get(i).getId() + ": " + mDb.getGoleiros().get(i).getNome());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_dropdown_item, goleiros);
        mSpinnerGoleiros.setAdapter(adapter);


        mSpinnerGoleiros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(id>0){
                    idGoleiro = Integer.parseInt(mSpinnerGoleiros.getSelectedItem().toString().substring(0, mSpinnerGoleiros.getSelectedItem().toString().indexOf(":")));
                    partidas = new ArrayList<String>();
                    partidas = mDb.getPartidas(idGoleiro);

                    ArrayAdapter<String> adapter1;
                    adapter1 = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_list_item_1, partidas);
                    mListPartidas.setAdapter(adapter1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        mListPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detalhe = mDb.getDetalhesPartida(partidas.get(position).toString().substring(1,partidas.get(position).toString().indexOf(")")));
                        Intent detalhe = new Intent(getActivity(), Detalhe.class);
                startActivity(detalhe);
            }
        });

        return mView;

    }
}
