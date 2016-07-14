package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class DefBase extends JogadaDefensiva{
    private int idJogadaDefensiva;

    public DefBase(int idPartida, int tempo, String setorBolaVeio, String setorBolaFoi, int errou, String erro, String tipoFinalizacao, int gol) {
        super(idPartida, tempo, setorBolaVeio, setorBolaFoi, errou, erro, tipoFinalizacao, gol);
    }
}
