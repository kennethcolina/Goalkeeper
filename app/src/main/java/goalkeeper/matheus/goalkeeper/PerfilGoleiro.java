package goalkeeper.matheus.goalkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.graph.RelatorioGeralActivity;
import goalkeeper.matheus.goalkeeper.graph.RelatorioPorJogadaActivity;
import model.Goleiro;

/**
 * Created by kenneth on 08/12/16.
 */
public class PerfilGoleiro extends AppCompatActivity {

    int idGoleiro;
    TextView txtNome;
    TextView txtDataNascQtdPartidas;
    ImageView ivFoto;
    Button mBtnPartidas;
    Button mBtnRelatorio;
    Button mBtnRelatorioPorJogada;
    Button mBtnEditar;
    Button mBtnDeletar;
    DBManager mDb;
    Goleiro goleiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_goleiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivFoto = (ImageView) findViewById(R.id.foto_perfil);
        txtNome = (TextView) findViewById(R.id.txt_nome_perfil);
        txtDataNascQtdPartidas = (TextView) findViewById(R.id.txt_dataNasc_perfil);
        mBtnPartidas = (Button) findViewById(R.id.btn_partidas);
        mBtnRelatorio = (Button) findViewById(R.id.btn_relatorio);
        mBtnEditar = (Button) findViewById(R.id.btn_editar_goleiro);
        mBtnDeletar = (Button) findViewById(R.id.btn_deletar_goleiro);
        mBtnRelatorioPorJogada = (Button) findViewById(R.id.btn_por_jogada);

        mDb = new DBManager(this);
        idGoleiro = this.getIntent().getIntExtra("ID_GOLEIRO", 0);
        Log.i("- - - - -", "" + idGoleiro);

        goleiro = mDb.getGoleiro(idGoleiro);

        txtNome.setText(goleiro.getNome());
        if(mDb.getQtdPartidasGoleiro(idGoleiro) == 1)
            txtDataNascQtdPartidas.setText("Avaliado em: "+mDb.getQtdPartidasGoleiro(idGoleiro)+" partida\nData de nascimento: " +goleiro.getDataNascimento());
        else
            txtDataNascQtdPartidas.setText("Avaliado em: "+mDb.getQtdPartidasGoleiro(idGoleiro)+" partidas\nData de nascimento: " +goleiro.getDataNascimento());

        mBtnPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partidas = new Intent(getApplicationContext(), Partidas.class);
                partidas.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(partidas);
            }
        });
        mBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alteraGoleiro = new Intent(getApplicationContext(), AlteraGoleiro.class);
                alteraGoleiro.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(alteraGoleiro);
            }
        });
        mBtnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(goleiro);
            }
        });

        mBtnRelatorioPorJogada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent relatorioJogada = new Intent(getApplicationContext(), RelatorioPorJogadaActivity.class);
                relatorioJogada.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(relatorioJogada);
            }
        });
        mBtnRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent relatorio = new Intent(getApplicationContext(), RelatorioGeralActivity.class);
                relatorio.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(relatorio);
            }
        });
    }

    public void confirmDialog(Goleiro goleiro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
        .setTitle("Excluir goleiro")
        .setMessage("Você deseja excluir o goleiro \"" + goleiro.getNome() + "\"?")
        .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mDb.deletarGoleiro(idGoleiro);
                        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
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

    @Override
    protected void onDestroy() {super.onDestroy();}

    @Override
    public void onResume() {
      super.onResume();
        goleiro = mDb.getGoleiro(idGoleiro);
        txtNome.setText(goleiro.getNome());
        if(mDb.getQtdPartidasGoleiro(idGoleiro) == 1)
            txtDataNascQtdPartidas.setText("Avaliado em: "+mDb.getQtdPartidasGoleiro(idGoleiro)+" partida\nData de nascimento: " +goleiro.getDataNascimento());
        else
            txtDataNascQtdPartidas.setText("Avaliado em: "+mDb.getQtdPartidasGoleiro(idGoleiro)+" partidas\nData de nascimento: " +goleiro.getDataNascimento());
    }
}
