package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matheus on 03/07/2016.
 */
public class Partida {
    //infos
    private int id;
    private int idGoleiro;
    private String data;
    private String descricao;

   /* //ofensivas
    private ArrayList<TiroMeta> tirosMeta;
    private ArrayList<ReporVoleio> reporVoleios;
    private ArrayList<ReporMao> reporMaos;
    private ArrayList<Dominio> dominios;
    //defesas
    private ArrayList<DefPunho> defPunhos;
    private ArrayList<DefBase> defBases;
    private ArrayList<DefSobCabeca> defSobCabecas;
    private ArrayList<DefCaida> defCaidas;
    private ArrayList<DefPe> defPes;
    //saidas
    private ArrayList<CaraCara> caraCaras;
    private ArrayList<Cruzamento> cruzamentos;*/

    public Partida(int idGoleiro, String data, String descricao) {
        this.idGoleiro = idGoleiro;
        this.data = data;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGoleiro() {
        return idGoleiro;
    }

    public void setIdGoleiro(int idGoleiro) {
        this.idGoleiro = idGoleiro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
