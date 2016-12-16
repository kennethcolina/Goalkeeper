package goalkeeper.matheus.goalkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bd.DBManager;
import model.Goleiro;

/**
 * Created by kenneth on 08/12/16.
 */
public class PerfilGoleiro extends AppCompatActivity {

    int idGoleiro;
    TextView txtNome;
    TextView txtDataNasc;
    ImageView ivFoto;
    Button mBtnPartidas;
    Button mBtnRelatorio;
    Button mBtnEditar;
    Button mBtnDeletar;
    Button mBtnVoltar;
    DBManager mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_goleiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionBar.setDisplayShowHomeEnabled(true);

        ivFoto = (ImageView) findViewById(R.id.foto_perfil);
        txtNome = (TextView) findViewById(R.id.txt_nome_perfil);
        txtDataNasc = (TextView) findViewById(R.id.txt_dataNasc_perfil);
        mBtnPartidas = (Button) findViewById(R.id.btn_partidas);
        mBtnRelatorio = (Button) findViewById(R.id.btn_relatorio);
        mBtnEditar = (Button) findViewById(R.id.btn_editar_goleiro);
        mBtnDeletar = (Button) findViewById(R.id.btn_deletar_goleiro);
        mBtnVoltar = (Button) findViewById(R.id.btn_voltar_perfil);

        mDb = new DBManager(this);
        idGoleiro = this.getIntent().getIntExtra("ID_GOLEIRO", 0);
        Log.i("- - - - -", "" + idGoleiro);

        final Goleiro goleiro = mDb.getGoleiro(idGoleiro);

        txtNome.setText(goleiro.getNome());
        txtDataNasc.setText(goleiro.getDataNascimento());

        mBtnPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partidas = new Intent(getApplicationContext(), Partidas.class);
                partidas.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(partidas);
                //  finish();
            }
        });
        mBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alteraGoleiro = new Intent(getApplicationContext(), AlteraGoleiro.class);
                alteraGoleiro.putExtra("ID_GOLEIRO", idGoleiro);
                startActivity(alteraGoleiro);
                finish();
            }
        });
        mBtnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(goleiro);
            }
        });
        mBtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    public void onBackPressed() {}
}
