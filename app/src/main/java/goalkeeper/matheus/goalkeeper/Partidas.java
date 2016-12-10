package goalkeeper.matheus.goalkeeper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import bd.DBManager;
import model.Partida;

/**
 * Created by kenneth on 06/12/16.
 */
public class Partidas extends AppCompatActivity {
    private int idGoleiro;
    private DBManager mDb;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDb = new DBManager(this);
        idGoleiro = getIntent().getIntExtra("ID_GOLEIRO", 0);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(mDb.getPartidasTela(idGoleiro), this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MatchViewHolder> {

        public class MatchViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView matchDescription;
            TextView matchDate;
            ImageView personPhoto;
            ImageButton matchDelete;
            ImageButton matchInfo;

            public MatchViewHolder(View itemView) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cv);
                matchDescription = (TextView) itemView.findViewById(R.id.txt_descricao);
                matchDate = (TextView) itemView.findViewById(R.id.txt_data_partida);
                personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
                matchDelete = (ImageButton) itemView.findViewById(R.id.match_delete);
                matchInfo = (ImageButton) itemView.findViewById(R.id.match_info);
            }
        }

        List<Partida> partidas;
        Context context;

        RVAdapter(List<Partida> partidas, Context context) {
            this.partidas = partidas;
            this.context = context;
        }

        @Override
        public int getItemCount() {
        return partidas.size();
    }

        @Override
        public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partida, viewGroup, false);
            MatchViewHolder mvh = new MatchViewHolder(v);
            return mvh;
        }

        @Override
        public void onBindViewHolder(final MatchViewHolder matchViewHolder, final int i) {
            final int idPartida = partidas.get(i).getId();

            matchViewHolder.matchDescription.setText(partidas.get(i).getDescricao());
            matchViewHolder.matchDate.setText(partidas.get(i).getData());
            matchViewHolder.personPhoto.setImageResource(R.drawable.user);
            matchViewHolder.matchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    mDb.deletarPartida(idPartida);
                    Intent refresh = new Intent(view.getContext(), Partidas.class);
                    refresh.putExtra("ID_GOLEIRO", idGoleiro);
                    startActivity(refresh);
                    finish();
                }
            });
            matchViewHolder.matchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    Intent detalhe = new Intent(view.getContext(), Detalhe.class);
                    detalhe.putExtra("ID_PARTIDA", idPartida);
                    startActivity(detalhe);
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}