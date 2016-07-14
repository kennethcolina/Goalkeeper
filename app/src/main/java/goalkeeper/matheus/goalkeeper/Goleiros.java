package goalkeeper.matheus.goalkeeper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bd.DBManeger;
import model.Goleiro;

/**
 * Created by Matheus on 02/07/2016.
 */
public class Goleiros extends Fragment {
    View mView;
    ListView mListView;
    DBManeger mDb;
    Goleiro mGoleiro;
    ArrayList<String> goleiros;
    TextView mtxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.goleiros, container, false);
        mDb = new DBManeger(getActivity());

        mtxt = (TextView) mView.findViewById(R.id.txt_goleiros);

        if (mDb.getGoleiros().isEmpty()){
            mtxt.setVisibility(View.VISIBLE);
        }else {
            mtxt.setVisibility(View.GONE);

            goleiros = new ArrayList<String>();
            for (int i = 0; i < mDb.getGoleiros().size(); i++) {
                goleiros.add(mDb.getGoleiros().get(i).getNome() + " - " + mDb.getGoleiros().get(i).getDataNascimento());
            }

            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_list_item_1, goleiros);
            mListView = (ListView) mView.findViewById(R.id.list_goleiros);
            mListView.setAdapter(adapter);
        }

        return mView;


    }
}
