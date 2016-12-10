package goalkeeper.matheus.goalkeeper;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bd.DBManager;
import model.Goleiro;

/**
 * Created by Matheus on 02/07/2016.
 */
public class Goleiros extends Fragment {
    View mView;
    ListView mListView;
    DBManager mDb;
    Goleiro mGoleiro;
    ArrayList<Goleiro> goleiros;
    TextView mtxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.goleiros, container, false);
        mDb = new DBManager(getActivity());

        mtxt = (TextView) mView.findViewById(R.id.txt_goleiros);

        RecyclerView rv = (RecyclerView) mView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        if (mDb.getGoleiros().isEmpty()) {
            mtxt.setVisibility(View.VISIBLE);
        } else {
            mtxt.setVisibility(View.GONE);

            RVAdapter adapter = new RVAdapter(mDb.getGoleiros(), getActivity());
            rv.setAdapter(adapter);
        }
        return mView;
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

        public class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView personName;
            TextView personAge;
            ImageView personPhoto;


            public PersonViewHolder(View itemView) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cv);
                personName = (TextView) itemView.findViewById(R.id.person_name);
                personAge = (TextView) itemView.findViewById(R.id.person_age);
                personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            }
        }

        List<Goleiro> goleiros;
        Context context;

        RVAdapter(List<Goleiro> goleiros, Context context) {
            this.goleiros = goleiros;
            this.context = context;
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
        public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
            final int id = goleiros.get(i).getId();

            personViewHolder.personName.setText(goleiros.get(i).getNome());
            personViewHolder.personAge.setText(goleiros.get(i).getDataNascimento());
            personViewHolder.personPhoto.setImageResource(R.drawable.user);
            personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent perfil = new Intent(view.getContext(), PerfilGoleiro.class);
                    perfil.putExtra("ID_GOLEIRO", id);
                    Log.i("siiiii", " " + id);

                    startActivity(perfil);//Start the same Activity
                    //mDb.teste();
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    public boolean confirmDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        return false;
    }
}

