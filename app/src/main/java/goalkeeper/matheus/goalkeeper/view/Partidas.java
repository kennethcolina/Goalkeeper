package goalkeeper.matheus.goalkeeper.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import goalkeeper.matheus.goalkeeper.view.graph.RelatorioTaticoActivity;
import model.Partida;

/**
 * Created by kenneth on 06/12/16.
 */
public class Partidas extends AppCompatActivity {
    private int idGoleiro;
    private TextView mtxt;
    private DBManager mDb;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = new DBManager(this);
        idGoleiro = getIntent().getIntExtra("ID_GOLEIRO", 0);
        mtxt = (TextView) findViewById(R.id.txt_partidas);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        if (mDb.getPartidasTela(idGoleiro).isEmpty()) {
            mtxt.setVisibility(View.VISIBLE);
        } else {
            mtxt.setVisibility(View.GONE);

            RVAdapter adapter = new RVAdapter(mDb.getPartidasTela(idGoleiro), this);
            rv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
            TextView matchDelete;
            TextView matchInfo;
            TextView matchTatica;

            public MatchViewHolder(View itemView) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cv);
                matchDescription = (TextView) itemView.findViewById(R.id.txt_descricao);
                matchDate = (TextView) itemView.findViewById(R.id.txt_data_partida);
                personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
                matchDelete = (TextView) itemView.findViewById(R.id.match_delete);
                matchInfo = (TextView) itemView.findViewById(R.id.match_info);
                matchTatica = (TextView) itemView.findViewById(R.id.match_tatica);
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
            final Partida partida = partidas.get(i);

            matchViewHolder.matchDescription.setText(partida.getDescricao());
            matchViewHolder.matchDate.setText(partida.getData());
           // matchViewHolder.personPhoto.setImageResource(R.drawable.user);
            matchViewHolder.matchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                confirmDialog(partida, idGoleiro);
                }
            });
            matchViewHolder.matchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
               Intent detalhe = new Intent(view.getContext(), Detalhe.class);
                detalhe.putExtra("ID_PARTIDA", partida.getId());
                //detalhe.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(detalhe);
                }
            });
            matchViewHolder.matchTatica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent tatica = new Intent(view.getContext(), RelatorioTaticoActivity.class);
                    tatica.putExtra("TITLE", "Relatório Tático");
                    tatica.putExtra("ID_GOLEIRO", idGoleiro);
                    tatica.putExtra("ID_PARTIDA", partida.getId());
                    startActivity(tatica);
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    public void confirmDialog(final Partida partida, final int idGoleiro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
        .setTitle("Excluir partida")
        .setMessage("Você deseja excluir a partida \"" + partida.getDescricao() + "\"?")
        .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mDb.deletarPartida(partida.getId());
                Intent refresh = new Intent(getApplicationContext(), Partidas.class);
                refresh.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(refresh);
                finish();
            }
        })
        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        })
        .show();
    }
}