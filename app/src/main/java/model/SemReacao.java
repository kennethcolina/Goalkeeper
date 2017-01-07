package model;

/**
 * Created by kenneth on 26/12/16.
 */
public class SemReacao extends JogadaDefensiva{
    private int idJogadaDefensiva;

    public SemReacao (int idPartida, int tempo, String setorBolaVeio, String setorBolaFoi, int errou, String observacao, String tipoFinalizacao, int gol){
        super(idPartida, tempo, setorBolaVeio,setorBolaFoi, errou, observacao, tipoFinalizacao, gol);
    }

}
