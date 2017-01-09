package bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import goalkeeper.matheus.goalkeeper.util.Constantes;
import model.Goleiro;
import model.JogadaDefensiva;
import model.JogadaOfensiva;
import model.Partida;

/**
 * Created by Matheus on 03/07/2016.
 */
public class DBManager {
    private static DBHelper dbHelper = null;

    public DBManager(Context context) {
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
            goleiros = new ArrayList<Goleiro>(); //é necessário?
            Goleiro goleiro = null;
            do {
                goleiro = new Goleiro(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                goleiros.add(goleiro);
            } while (cursor.moveToNext());
        }
        return goleiros; // na main, se o goleio for null escrever msg
    }

    public Goleiro getGoleiro(int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from goleiro where '" + idGoleiro + "' = Goleiro.id;";
        Cursor cursor = db.rawQuery(sql, null);
        Goleiro goleiro = null;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                goleiro = new Goleiro(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return goleiro;
    }

    public void AlteraGoleiro(Goleiro goleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", goleiro.getNome());
        values.put("dataNascimento", goleiro.getDataNascimento());
        String where = "Goleiro.id  ='" + goleiro.getId()+"'";
        db.update("goleiro", values, where, null);
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

    public void deletarPartida(int idPartida) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete("partida", "id='" + idPartida + "'", null);
    }

    /*
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
    */

    public void cadastrarJO(JogadaOfensiva jo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idPartida", jo.getIdPartida());
        valores.put("tempo", jo.getTempo());
        valores.put("observacao", jo.getErro());
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

    public int getQtdPartidasGoleiro(int idGoleiro) {
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
        return qtdPartidas;
    }


    public  int countSetor(int idGoleiro, int idPartida, String setor) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int qtdSetoresAtivados = 0;


        String sql = "select count(JogadaDefensiva.id) from JogadaDefensiva join partida on partida.id=idPartida where idGoleiro='" + idGoleiro + "' and idPartida='"+idPartida+"' and setorBolaVeio='"+setor+"';";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdSetoresAtivados=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return qtdSetoresAtivados;
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

        //DEFESA SAIDA
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida from JogadaDefensiva, DefSaida where DefSaida.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDefSaida= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDefSaida= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida, errou from JogadaDefensiva, DefSaida where DefSaida.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int DefSaidaErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
        }
        do {
            DefSaidaErrados = cursor.getInt(0);
        } while (cursor.moveToNext());

        lista.add("SAÍDA\nAcertou " + (qtdDefSaida - DefSaidaErrados) + " de " + qtdDefSaida + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //DEFESA BASE
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida from JogadaDefensiva, DefBase where DefBase.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDefBase= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDefBase= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida, errou from JogadaDefensiva, DefBase where DefBase.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int DefBaseErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DefBaseErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("DEFESA BASE\nAcertou " + (qtdDefBase - DefBaseErrados) + " de " + qtdDefBase + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //DEFESA CAÍDA
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida from JogadaDefensiva, DefCaida where DefCaida.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDefCaida= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDefCaida= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida, errou from JogadaDefensiva, DefCaida where DefCaida.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int DefCaidaErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DefCaidaErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("DEFESA CAÍDA\nAcertou " + (qtdDefCaida - DefCaidaErrados) + " de " + qtdDefCaida + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //DEFESA PÉ
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida from JogadaDefensiva, DefPe where DefPe.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDefPe= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDefPe= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida, errou from JogadaDefensiva, DefPe where DefPe.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int DefPeErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DefPeErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("DEFESA PÉ\nAcertou " + (qtdDefPe - DefPeErrados) + " de " + qtdDefPe + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //DEFESA PUNHO
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida from JogadaDefensiva, DefPunho where DefPunho.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDefPunho= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDefPunho= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida, errou from JogadaDefensiva, DefPunho where DefPunho.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int DefPunhoErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DefPunhoErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        lista.add("DEFESA PUNHO\nAcertou " + (qtdDefPunho - DefPunhoErrados) + " de " + qtdDefPunho + "\nAvaliado em " + qtdPartidas + " partida(s)\n");

        //DEFESA SOBRE CABECA
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida from JogadaDefensiva, DefSobreCabeca where DefSobreCabeca.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";
        int qtdDefSobreCabeca= 0;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdDefSobreCabeca= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        sql = "select count(*) from " +
                "(select id, JogadaDefensiva.idPartida, errou from JogadaDefensiva, DefSobreCabeca where DefSobreCabeca.idJogadaDefensiva=JogadaDefensiva.id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";
        int DefSobreCabecaErrados = 999999;
        cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DefSobreCabecaErrados = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        lista.add("DEFESA SOBRE CABEÇA\nAcertou " + (qtdDefSobreCabeca - DefSobreCabecaErrados) + " de " + qtdDefSobreCabeca + "\nAvaliado em " + qtdPartidas + " partida(s)\n");
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
        valores.put("observacao", jd.getErro());
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

    public String getDetalhesPartida(int idPartida) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select descricao from historico where idPartida='"+idPartida+"'";
        Cursor cursor = db.rawQuery(sql, null);
        String detalhe = "";

        if (cursor != null && cursor.moveToFirst()) {
            do {
                detalhe = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return detalhe;
    }

    public Partida getPartida(int idParida) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select data, descricao, idGoleiro from partida where id ='" + idParida + "'";
        Cursor cursor = db.rawQuery(sql, null);
        Partida partida = null;

        if(cursor != null && cursor.moveToFirst()) {
            do {
                partida = new Partida(cursor.getInt(2), cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return partida;
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

    public ArrayList<Partida> getPartidasTela(int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select id, data, descricao from partida where idGoleiro='"+idGoleiro+"'";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Partida> partidas = new ArrayList<Partida>();
        Partida partida;

        if (cursor != null && cursor.moveToFirst()) {
          //  partidas = new ArrayList<String>();
            do {
                partida = new Partida(cursor.getString(1), cursor.getString(2), cursor.getInt(0));
                partidas.add(partida);
            } while (cursor.moveToNext());
        }
        return partidas; // na main, se o goleio for null escrever msg
    }

    public void cadastrarDefBase(int idJogadaDefensiva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        db.insert("DefBase", null, valores);
    }

    public void cadastrarDefCaida(int idJogadaDefensiva,String s){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        valores.put("tipoDefesaCaida", s);
        db.insert("DefCaida", null, valores);
    }

    public void cadastrarDefPe(int idJogadaDefensiva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        db.insert("DefPe", null, valores);
    }

    public void cadastrarDefPunho(int idJogadaDefensiva, String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        valores.put("tipoDefesaPunho", s);
        db.insert("DefPunho", null, valores);
    }

    public void cadastrarNaoAgiu(int idJogadaDefensiva) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        db.insert("NaoAgiu", null, valores);
    }

    public void cadastrarDefSobreCabeca(int idJogadaDefensiva, String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        valores.put("tipoDefesa", s);
        db.insert("DefSobreCabeca", null, valores);
    }

    public void cadastrarDefSaida(int idJogadaDefensiva, String s, String s1) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idJogadaDefensiva", idJogadaDefensiva);
        valores.put("tipoSaida", s);
        valores.put("motivoSaida", s1);
        db.insert("DefSaida", null, valores);
    }

    public int countJogada(int idGoleiro, String jogada, boolean flagDefensiva) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tipoJogada = "JogadaDefensiva";
        if(flagDefensiva == false) tipoJogada = "JogadaOfensiva";

        jogada = Constantes.getNameTable(jogada);

        String sql = "select count(*) from " +
                "(select id, "+tipoJogada+".idPartida from "+tipoJogada+", "+jogada+" where "+jogada+".id"+tipoJogada+"="+tipoJogada+".id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida;";

        int qtd= 0;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtd= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return qtd;
    }

    public int errosJogada(int idGoleiro, String jogada, boolean flagDefensiva) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tipoJogada = "JogadaDefensiva";
        if(flagDefensiva == false) tipoJogada = "JogadaOfensiva";

        jogada = Constantes.getNameTable(jogada);

        String sql = "select count(*) from " +
                "(select id, "+tipoJogada+".idPartida, errou from "+tipoJogada+", "+jogada+" where "+jogada+".id"+tipoJogada+"="+tipoJogada+".id) " +
                "join " +
                "(select Partida.id as idPartidasGoleiro from partida where Partida.idGoleiro='" + idGoleiro + "') " +
                "on idPartidasGoleiro=idPartida where errou=1;";

        int qtd = 0;

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtd= cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return qtd;
    }

    public int getQtdPenaltiFoiGol (int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select count(*) from JogadaDefensiva where tipoFinalizacao = '" + Constantes.FINALIZACAO_PENALTI  + "' and gol = '" + 1 + "';";

        Cursor cursor = db.rawQuery(sql, null);
        int qtdGol = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdGol = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return qtdGol;
    }

    public int getQtdPenaltiFoiNoGol(int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select count(*) from JogadaDefensiva where tipoFinalizacao = '" + Constantes.FINALIZACAO_PENALTI +
                "' and (setorBolaFoi = '" + Constantes.SETOR_CID  + "' or setorBolaFoi = '" + Constantes.SETOR_CSE +
                "' or setorBolaFoi = '" + Constantes.SETOR_MI + "' or setorBolaFoi = '" + Constantes.SETOR_MS+
                "' or setorBolaFoi = '" + Constantes.SETOR_CSD + "' or setorBolaFoi = '" + Constantes.SETOR_CSE + "');";


        Cursor cursor = db.rawQuery(sql, null);
        int qtdFoiNoGol = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdFoiNoGol = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return qtdFoiNoGol;
    }

    public int getQtdPenaltiNaoFoiNoGol(int idGoleiro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select count(*) from JogadaDefensiva where tipoFinalizacao = '" + Constantes.FINALIZACAO_PENALTI +
                "' and (setorBolaFoi = '" + Constantes.SETOR_T  + "' or setorBolaFoi = '" + Constantes.SETOR_FMS +
                "' or setorBolaFoi = '" + Constantes.SETOR_FCID + "' or setorBolaFoi = '" + Constantes.SETOR_FCIE+
                "' or setorBolaFoi = '" + Constantes.SETOR_FCSD + "' or setorBolaFoi = '" + Constantes.SETOR_FCSE + "');";

        Cursor cursor = db.rawQuery(sql, null);
        int qtdNaoFoiNoGol = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                qtdNaoFoiNoGol = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return qtdNaoFoiNoGol;
    }

    /*
    public void teste() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from partida";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> partidas = new ArrayList<String>();

        if (cursor != null && cursor.moveToFirst()) {
            partidas = new ArrayList<String>();
            do {
                partidas.add("("+cursor.getInt(0)+") "+ cursor.getString(1) + " " + cursor.getString(2)+"\n");
                Log.i(cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
    }
    */
}
