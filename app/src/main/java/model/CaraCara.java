package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class CaraCara extends JogadaDefensiva{
    private int idJogadaDefensiva;
    private int tipoCaraCara;

    public CaraCara(int idPartida, int tempo, String setorBolaVeio, String setorBolaFoi, int errou, String observacao, String tipoFinalizacao, int gol) {
        super(idPartida, tempo, setorBolaVeio, setorBolaFoi, errou, observacao, tipoFinalizacao, gol);
    }
}
