package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class Cruzamento extends JogadaDefensiva{
    private boolean escanteio;
    private String tipoCruzamento; //incluir falta
    private String tipoSaida; //saiu
    private boolean acertouBarreira;
    private String erroCruzamento;
    private int idJogadaDefensiva;

    public Cruzamento(int idPartida, int tempo, String setorBolaVeio, String setorBolaFoi, int errou, String erro, String tipoFinalizacao, int gol) {
        super(idPartida, tempo, setorBolaVeio, setorBolaFoi, errou, erro, tipoFinalizacao, gol);
    }
}
