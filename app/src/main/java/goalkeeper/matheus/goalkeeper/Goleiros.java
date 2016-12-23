package goalkeeper.matheus.goalkeeper;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bd.DBManager;
import model.Goleiro;

/**
 * Created by Matheus on 02/07/2016.
 */
public class Goleiros extends Fragment {
    View mView;
    DBManager mDb;
    TextView mtxt;
    RVAdapter mAdapter;
    RecyclerView mRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.goleiros, container, false);
        mDb = new DBManager(getActivity());

        FloatingActionButton myFab = (FloatingActionButton)  mView.findViewById(R.id.cadastrarGoleiroFab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cadastroGoleiro = new Intent(getActivity(), CadastroGoleiro.class);
                startActivity(cadastroGoleiro);

            }
        });


        mtxt = (TextView) mView.findViewById(R.id.txt_goleiros);

        mRv = (RecyclerView) mView.findViewById(R.id.rv);
        mRv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(llm);

        if (mDb.getGoleiros().isEmpty()) {
            mtxt.setVisibility(View.VISIBLE);
        } else {
            mtxt.setVisibility(View.GONE);

            mAdapter = new RVAdapter(mDb.getGoleiros(), getActivity());
            mRv.setAdapter(mAdapter);
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mDb.getGoleiros().isEmpty()) {
            mtxt.setVisibility(View.VISIBLE);
        } else {
            mtxt.setVisibility(View.GONE);

            mAdapter = new RVAdapter(mDb.getGoleiros(), getActivity());
            mRv.setAdapter(mAdapter);
        }
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
            int qtdPartidas = mDb.getQtdPartidasGoleiro(id);

            personViewHolder.personName.setText(goleiros.get(i).getNome());
            if(qtdPartidas == 1)
                personViewHolder.personAge.setText("Avaliado em: "+qtdPartidas+" partida\nData de nascimento: " +goleiros.get(i).getDataNascimento());
            else
                personViewHolder.personAge.setText("Avaliado em: "+qtdPartidas+" partidas\nData de nascimento: " +goleiros.get(i).getDataNascimento());
            // personViewHolder.personPhoto.setImageResource(R.drawable.user);
            personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent perfil = new Intent(view.getContext(), PerfilGoleiro.class);
                    perfil.putExtra("ID_GOLEIRO", id);
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
}

