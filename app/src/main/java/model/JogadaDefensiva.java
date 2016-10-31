package model;

/**
 * Created by Matheus on 03/07/2016.
 */
public class JogadaDefensiva {
    private int id;
    private int idPartida;
    private int tempo;
    private String setorBolaVeio;
    private String setorBolaFoi;
    private int errou;
    private String erro;
    private String tipoFinalizacao;
    private int gol;

    public JogadaDefensiva(int idPartida, int tempo, String setorBolaVeio, String setorBolaFoi, int errou, String erro, String tipoFinalizacao, int gol) {
        this.idPartida = idPartida;
        this.tempo = tempo;
        this.setorBolaVeio = setorBolaVeio;
        this.setorBolaFoi = setorBolaFoi;
        this.errou = errou;
        this.erro = erro;
        this.tipoFinalizacao = tipoFinalizacao;
        this.gol = gol;
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

    public String getSetorBolaVeio() {
        return setorBolaVeio;
    }

    public void setSetorBolaVeio(String setorBolaVeio) {
        this.setorBolaVeio = setorBolaVeio;
    }

    public String getSetorBolaFoi() {
        return setorBolaFoi;
    }

    public void setSetorBolaFoi(String setorBolaFoi) {
        this.setorBolaFoi = setorBolaFoi;
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

    public String getTipoFinalizacao() {
        return tipoFinalizacao;
    }

    public void setTipoFinalizacao(String tipoFinalizacao) {
        this.tipoFinalizacao = tipoFinalizacao;
    }

    public int isGol() {
        return gol;
    }

    public void setGol(int gol) {
        this.gol = gol;
    }
}
