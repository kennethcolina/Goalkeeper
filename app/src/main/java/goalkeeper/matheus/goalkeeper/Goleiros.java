package goalkeeper.matheus.goalkeeper;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<Goleiro> goleiros;
    TextView mtxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.goleiros, container, false);
        mDb = new DBManeger(getActivity());

        mtxt = (TextView) mView.findViewById(R.id.txt_goleiros);

        RecyclerView rv = (RecyclerView)mView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        if (mDb.getGoleiros().isEmpty()){
            mtxt.setVisibility(View.VISIBLE);
        }else {
            mtxt.setVisibility(View.GONE);

            RVAdapter adapter = new RVAdapter(mDb.getGoleiros(),getActivity());
            rv.setAdapter(adapter);

        }

        return mView;


    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{



        public class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView personName;
            TextView personAge;
            ImageView personPhoto;
            ImageButton personDelete;
            ImageButton personEdit;

            PersonViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                personName = (TextView)itemView.findViewById(R.id.person_name);
                personAge = (TextView)itemView.findViewById(R.id.person_age);
                personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
                personDelete = (ImageButton)itemView.findViewById(R.id.person_delete);
                personEdit = (ImageButton)itemView.findViewById(R.id.person_edit);
            }
        }

        List<Goleiro> goleiros;
        Context context;

        RVAdapter(List<Goleiro> goleiros,Context context){
            this.goleiros=goleiros;
            this.context=context;
        }


        @Override
        public int getItemCount() {
            return goleiros.size();
        }
        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goleiro, viewGroup, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
            final int id = goleiros.get(i).getId();
            personViewHolder.personName.setText(goleiros.get(i).getNome());
            personViewHolder.personAge.setText(goleiros.get(i).getDataNascimento());
            personViewHolder.personPhoto.setImageResource(R.drawable.user);
            personViewHolder.personDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(id+" - delete");
                }
            });
            personViewHolder.personEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(id+" - edit");
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }
}