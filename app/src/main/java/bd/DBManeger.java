package bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bd.DBHelper;
import model.Goleiro;
import model.JogadaDefensiva;
import model.JogadaOfensiva;
import model.Partida;

/**
 * Created by Matheus on 03/07/2016.
 */
public class DBManeger {
    private static DBHelper dbHelper = null;

    public DBManeger(Context context) {
        if (dbHelper == null) dbHelper = new DBHelper(context);
    }

    public void cadastrarGoleiro(Goleiro goleiro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", goleiro.getNome());
        valores.put("dataNascimento", goleiro.getDataNascimento());
        db.insert("goleiro", null, valores);
    }

    public ArrayList<Goleiro> getGoleiros() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from goleiro";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Goleiro> goleiros = new ArrayList<Goleiro>();

        if (cursor != null && cursor.moveToFirst()) {
            goleiros = new ArrayList<Goleiro>();
            Goleiro goleiro = null;
            do {
                goleiro = new Goleiro(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                goleiros.add(goleiro);
            } while (cursor.moveToNext());
        }
        return goleiros; // na main, se o goleio for null escrever msg
    }

    public void deletarGoleiro(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("goleiro", "id='" + id + "'", null);
    }

    public void cadastrarPartida(Partida mPartida) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("data", mPartida.getData());
        valores.put("idGoleiro", mPartida.getIdGoleiro());
        valores.put("descricao", mPartida.getDescricao());
        db.insert("Partida", null, valores);
    }

    public void deletarPartida() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select MAX(id) from partida";
        Cursor cursor = db.rawQuery(sql, null);
        int id = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        db.delete("partida", "id='" + id + "'", null);
        //db.delete("JogadaOfensiva", "idPartida='"+id+"'", null);
        //db.delete("JogadaDefensiva", "idPartida='"+id+"'", null);
    }

    public void cadastrarJO(JogadaOfensiva jo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idPartida", jo.getIdPartida());
        valores.put("tempo", jo.getTempo());
        valores.put("erro", jo.getErro());
        valores.put("errou", jo.isErrou());
        valores.put("setorBolaFoi", jo.getSetorBolaFoi());
        valores.put("primeiraBola", jo.getSegundaBola());
        valores.put("segundaBola", jo.getPrimeiraBola());
        db.insert("JogadaOfensiva", null, valores);
    }

    public int getMaxIdPartida() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select MAX(id) from partida";
        Cursor cursor = db.rawQuery(sql, null);
        int id = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return id;
    }

    public void cadastrarTiroMeta(int idJogadaOfensiva, String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaOfensiva", idJogadaOfensiva);
        valores.put("tipoTiroMeta", s);
        db.insert("tiroMeta", null, valores);
    }

    public int getMaxIdJogadaOfensiva() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select MAX(id) from JogadaOfensiva";
        Cursor cursor = db.rawQuery(sql, null);
        int id = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return id;
    }

    public ArrayList<String> getResultsGoleiro(int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select count(*) from partida where idGoleiro='" + idGoleiro + "';";
        Cursor cursor = db.rawQuery(sql, null);
        int qtdPartidas = 0;

        //tiro de meta
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdPartidas = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        sql = "select count(*) from " +
                "(select id as idTiroMeta, JogadaOfensiva.idPartida from JogadaOfensiva, TiroMeta  where TiroMeta.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdTirosMeta = 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdTirosMeta = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id as idTiroMeta, JogadaOfensiva.idPartida, errou from JogadaOfensiva, TiroMeta  where TiroMeta.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int qtdTirosMetaErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdTirosMetaErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        ArrayList<String> lista = new ArrayList<>();
        lista.add("TIRO DE META\nAcertou " + (qtdTirosMeta - qtdTirosMetaErrados) + " de " + qtdTirosMeta + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //repor mao
        sql = "select count(*) from " +
                "(select id as idrepormao, JogadaOfensiva.idPartida from JogadaOfensiva, ReporMao  where ReporMao.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdReporMao = 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdReporMao = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id as idrepormao, JogadaOfensiva.idPartida, errou from JogadaOfensiva, ReporMao where ReporMao.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int qtdReporMaoErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdReporMaoErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("REPOSIÇÃO MÃO\nAcertou " + (qtdReporMao - qtdReporMaoErrados) + " de " + qtdReporMao + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //repor voleio
        sql = "select count(*) from " +
                "(select id as idrepormao, JogadaOfensiva.idPartida from JogadaOfensiva, ReporVoleio  where ReporVoleio.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdReporVoleio= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdReporVoleio= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id as idrepormao, JogadaOfensiva.idPartida, errou from JogadaOfensiva, ReporVoleio where ReporVoleio.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int qtdReporVoleioErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdReporVoleioErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("REPOSIÇÃO VOLEIO\nAcertou " + (qtdReporVoleio - qtdReporVoleioErrados) + " de " + qtdReporVoleio + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //dominio
        sql = "select count(*) from " +
                "(select id as idrepormao, JogadaOfensiva.idPartida from JogadaOfensiva, Dominio where Dominio.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDominio= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDominio= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id as idrepormao, JogadaOfensiva.idPartida, errou from JogadaOfensiva, Dominio where Dominio.idJogadaOfensiva=JogadaOfensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int qtdDominioErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDominioErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("DOMÍNIO\nAcertou " + (qtdDominio - qtdDominioErrados) + " de " + qtdDominio + "\nAvaliado em " + qtdPartidas + " partida(s)\n");



        return lista;
    }

    public void cadastrarHistorico(String historico, int idPartida) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao", historico);
        valores.put("idPartida", idPartida);
        db.insert("historico", null, valores);
    }

    public void cadastrarDominio(int idJogadaOfensiva, String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaOfensiva", idJogadaOfensiva);
        valores.put("tipoDominio", s);
        db.insert("dominio", null, valores);
    }

    public void cadastrarReporMao(int idJogadaOfensiva, String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaOfensiva", idJogadaOfensiva);
        valores.put("tipoComMao", s);
        db.insert("ReporMao", null, valores);
    }

    public void cadastrarReporVoleio(int idJogadaOfensiva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaOfensiva", idJogadaOfensiva);
        db.insert("ReporVoleio", null, valores);
    }

    public void cadastrarJD(JogadaDefensiva jd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idPartida", jd.getIdPartida());
        valores.put("tempo", jd.getTempo());
        valores.put("erro", jd.getErro());
        valores.put("errou", jd.isErrou());
        valores.put("setorBolaFoi", jd.getSetorBolaFoi());
        valores.put("setorBolaVeio", jd.getSetorBolaVeio());
        valores.put("tipoFinalizacao", jd.getTipoFinalizacao());
        valores.put("gol", jd.isGol());
        db.insert("JogadaDefensiva", null, valores);
    }

    public int getMaxIdJogadaDefensiva() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select MAX(id) from jogadaDefensiva";
        Cursor cursor = db.rawQuery(sql, null);
        int id = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return id;
    }

    public void cadastrarDefBase(int idJogadaDefensiva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        db.insert("DefBase", null, valores);
    }

    public String getDetalhesPartida(String idPartida) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select descricao from historico where idPartida='"+idPartida+"'";
        Cursor cursor = db.rawQuery(sql, null);
        String detalhe = idPartida;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                detalhe = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return detalhe;
    }

    public ArrayList<String> getPartidas(int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select id, descricao, data from partida where idGoleiro='"+idGoleiro+"'";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> partidas = new ArrayList<String>();

        if (cursor != null && cursor.moveToFirst()) {
            partidas = new ArrayList<String>();
            do {
                partidas.add("("+cursor.getInt(0)+") "+ cursor.getString(1) + " " + cursor.getString(2)+"\n");
            } while (cursor.moveToNext());
        }
        return partidas; // na main, se o goleio for null escrever msg
    }
}
