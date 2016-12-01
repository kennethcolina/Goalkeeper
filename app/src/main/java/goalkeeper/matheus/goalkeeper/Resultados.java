package goalkeeper.matheus.goalkeeper;

import android.app.Fragment;
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

/**
 * Created by Matheus on 02/07/2016.
 */
public class Resultados extends Fragment {
    View mView;
    DBManager mDb;
    Spinner mSpinnerGoleiros;
    ListView mListResultados;
    ArrayList<String> goleiros;
    ArrayList<String> results;
    int idGoleiro=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.resultados, container, false);

        mDb = new DBManager(getActivity());

        mSpinnerGoleiros = (Spinner) mView.findViewById(R.id.spinner_goleirosResultados);
        mListResultados = (ListView) mView.findViewById(R.id.list_resultados);

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
                    results = new ArrayList<String>();
                    results = mDb.getResultsGoleiro(idGoleiro);

                    ArrayAdapter<String> adapter1;
                    adapter1 = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_list_item_1, results);
                    mListResultados.setAdapter(adapter1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return mView;

    }
}
