package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class DefCaida extends JogadaDefensiva{
    private int idJogadaDefensiva;
    private String tipoDefesaCaida;

    public DefCaida(int idPartida, int tempo, String setorBolaVeio, String setorBolaFoi, int errou, String observacao, String tipoFinalizacao, int gol) {
        super(idPartida, tempo, setorBolaVeio, setorBolaFoi, errou, observacao, tipoFinalizacao, gol);
    }
}
