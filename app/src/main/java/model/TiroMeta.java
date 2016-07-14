package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class TiroMeta extends JogadaOfensiva{
    private String tipoTiroMeta;
    private int idJogadaOfensiva;

    public TiroMeta(int idPartida, int tempo, String setorBolaFoi, String primeiraBola, String segundaBola, int errou, String erro) {
        super(idPartida, tempo, setorBolaFoi, primeiraBola, segundaBola, errou, erro);
    }
}
