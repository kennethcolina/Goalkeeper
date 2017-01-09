package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import goalkeeper.matheus.goalkeeper.util.Constantes;

/**
 * Created by Matheus on 03/07/2016.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static int VERSION = 1;
    private static String BD_NAME= "bd";

    //habilita o funcionamento da foreign key
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    private static String Goleiro =
            "CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_GOLEIRO)+" (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nome TEXT," +
            "  dataNascimento TEXT);";
    private static String Partida =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_PARTIDA)+" (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  data TEXT," +
            "  descricao TEXT," +
            "  idGoleiro INTEGER NOT NULL," +
            "  FOREIGN KEY(idGoleiro) REFERENCES "+Constantes.getNameTable(Constantes.DESCRICAO_GOLEIRO)+"(id) ON DELETE CASCADE);";
    private static String JogadaOfensiva =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_JOGADA_OFENSIVA)+" (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  tempo INTEGER," +
            "  setorBolaFoi TEXT NULL," +
            "  primeiraBola TEXT NULL," +
            "  segundaBola TEXT NULL," +
            "  errou INTEGER not null," +
            "  observacao TEXT," +
            "  idPartida INTEGER NOT NULL);" ;
    private static String TiroMeta =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_TIRO_META)+" (" +
            "  tipoTiroMeta TEXT NOT NULL," +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);";
    private static String ReporMao =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_REPOR_MAO)+" (" +
            "  tipoComMao TEXT NULL," +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);" ;
    private static String Dominio =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DOMINIO)+" (" +
            "  tipoDominio TEXT NULL," +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);" ;
    private static String JogadaDefensiva =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_JOGADA_DEFENSIVA)+" (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  tempo INTEGER NULL," +
            "  setorBolaFoi TEXT NULL," +
            "  setorBolaVeio TEXT NULL," +
            "  errou INTEGER not NULL," +
            "  observacao TEXT," +
            "  idPartida INTEGER NOT NULL," +
            "  tipoFinalizacao TEXT NULL," +
            "  gol INTEGER);" ;
    private static String DefCaida =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DEFESA_CAIDA)+" (" +
            "  tipoDefesaCaida TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String ReporVoleio =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_REPOR_VOLEIO)+" (" +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);" ;
    private static String DefBase =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DEFESA_BASE)+" (" +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);";
    private static String DefPe =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DEFESA_PE)+" (" +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String DefPunho =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DEFESA_PUNHO)+" (" +
            "  tipoDefesaPunho TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String NaoAgiu =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_NAO_AGIU)+" (" +
                    "  idJogadaDefensiva INTEGER PRIMARY KEY);";
    private static String DefSobreCabeca =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DEFESA_SOBRE_CABECA)+" (" +
            "  tipoDefesa TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String DefSaida =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_DEFESA_SAIDA)+" (" +
            "  tipoSaida TEXT NULL," +
            "  motivoSaida TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String Historico =
            "  CREATE TABLE IF NOT EXISTS "+Constantes.getNameTable(Constantes.DESCRICAO_HISTORICO)+" (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  descricao TEXT NOT NULL," +
            "  idPartida INTEGER);";

    public DBHelper(Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Goleiro);
        db.execSQL(Partida);
        db.execSQL(DefBase);
        db.execSQL(DefCaida);
        db.execSQL(DefPe);
        db.execSQL(DefPunho);
        db.execSQL(NaoAgiu);
        db.execSQL(DefSobreCabeca);
        db.execSQL(Dominio);
        db.execSQL(JogadaDefensiva);
        db.execSQL(ReporMao);
        db.execSQL(JogadaOfensiva);
        db.execSQL(ReporVoleio);
        db.execSQL(TiroMeta);
        db.execSQL(DefSaida);
        db.execSQL(Historico);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
