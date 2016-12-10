package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static String GOLEIRO =
            "CREATE TABLE IF NOT EXISTS Goleiro (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nome TEXT," +
            "  dataNascimento TEXT);";
    private static String Partida =
            "  CREATE TABLE IF NOT EXISTS Partida (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  data TEXT," +
            "  descricao TEXT," +
            "  idGoleiro INTEGER NOT NULL," +
            "  FOREIGN KEY(idGoleiro) REFERENCES Goleiro(id) ON DELETE CASCADE);";
    private static String JogadaOfensiva =
            "  CREATE TABLE IF NOT EXISTS JogadaOfensiva (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  tempo INTEGER," +
            "  setorBolaFoi TEXT NULL," +
            "  primeiraBola TEXT NULL," +
            "  segundaBola TEXT NULL," +
            "  errou INTEGER not null," +
            "  observacao TEXT," +
            "  idPartida INTEGER NOT NULL);" ;
    private static String TiroMeta =
            "  CREATE TABLE IF NOT EXISTS TiroMeta (" +
            "  tipoTiroMeta TEXT NOT NULL," +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);";
    private static String ReporMao =
            "  CREATE TABLE IF NOT EXISTS ReporMao (" +
            "  tipoComMao TEXT NULL," +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);" ;
    private static String Dominio =
            "  CREATE TABLE IF NOT EXISTS Dominio (" +
            "  tipoDominio TEXT NULL," +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);" ;
    private static String JogadaDefensiva =
            "  CREATE TABLE IF NOT EXISTS JogadaDefensiva (" +
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
            "  CREATE TABLE IF NOT EXISTS DefCaida (" +
            "  tipoDefesaCaida TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String ReporVoleio =
            "  CREATE TABLE IF NOT EXISTS ReporVoleio (" +
            "  idJogadaOfensiva INTEGER PRIMARY KEY);" ;
    private static String DefBase =
            "  CREATE TABLE IF NOT EXISTS DefBase (" +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);";
    private static String DefPe =
            "  CREATE TABLE IF NOT EXISTS DefPe (" +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String DefPunho =
            "  CREATE TABLE IF NOT EXISTS DefPunho (" +
            "  tipoDefesaPunho TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String DefSobreCabeca =
            "  CREATE TABLE IF NOT EXISTS DefSobreCabeca (" +
            "  tipoDefesa TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String DefSaida =
            "  CREATE TABLE IF NOT EXISTS DefSaida (" +
            "  tipoSaida TEXT NULL," +
            "  motivoSaida TEXT NULL," +
            "  idJogadaDefensiva INTEGER PRIMARY KEY);" ;
    private static String Historico =
            "  CREATE TABLE IF NOT EXISTS Historico (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  descricao TEXT NOT NULL," +
            "  idPartida INTEGER);";

    public DBHelper(Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GOLEIRO);
        db.execSQL(Partida);
        db.execSQL(DefBase);
        db.execSQL(DefCaida);
        db.execSQL(DefPe);
        db.execSQL(DefPunho);
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
