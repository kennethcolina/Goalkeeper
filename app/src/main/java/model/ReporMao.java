package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class ReporMao extends JogadaOfensiva{
    private int idJogadaOfensiva;
    private String TipoComMao;

    public ReporMao(int idPartida, int tempo, String setorBolaFoi, String primeiraBola, String segundaBola, int errou, String erro) {
        super(idPartida, tempo, setorBolaFoi, primeiraBola, segundaBola, errou, erro);
    }
}
