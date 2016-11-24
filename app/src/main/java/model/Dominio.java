package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class Dominio extends JogadaOfensiva {
    //nao usar onde a bola foi de jogada ofensiva
    private int idJogadaOfensiva;
    private String tipoDominio;

    public Dominio(int idPartida, int tempo, String setorBolaFoi, String primeiraBola, String segundaBola, int errou, String observacao) {
        super(idPartida, tempo, setorBolaFoi, primeiraBola, segundaBola, errou, observacao);
    }
}
