package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class JogadaOfensiva {

    private int id;
    private int idPartida;
    private int tempo;
    private String setorBolaFoi;
    private String primeiraBola;
    private String segundaBola;
    private int errou;
    private String erro;

    public JogadaOfensiva(int idPartida, int tempo, String setorBolaFoi, String primeiraBola, String segundaBola, int errou, String erro) {
        this.idPartida = idPartida;
        this.tempo = tempo;
        this.setorBolaFoi = setorBolaFoi;
        this.primeiraBola = primeiraBola;
        this.segundaBola = segundaBola;
        this.errou = errou;
        this.erro = erro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getSetorBolaFoi() {
        return setorBolaFoi;
    }

    public void setSetorBolaFoi(String setorBolaFoi) {
        this.setorBolaFoi = setorBolaFoi;
    }

    public String getPrimeiraBola() {
        return primeiraBola;
    }

    public void setPrimeiraBola(String primeiraBola) {
        this.primeiraBola = primeiraBola;
    }

    public String getSegundaBola() {
        return segundaBola;
    }

    public void setSegundaBola(String segundaBola) {
        this.segundaBola = segundaBola;
    }

    public int isErrou() {
        return errou;
    }

    public void setErrou(int errou) {
        this.errou = errou;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
