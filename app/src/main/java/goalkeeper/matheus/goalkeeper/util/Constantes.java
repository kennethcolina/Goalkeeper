package goalkeeper.matheus.goalkeeper.util;

import java.util.ArrayList;

/**
 * Created by Matheus on 24/12/2016.
 */
public class Constantes {

    /*-------------------------LABELS---------------------------*/

    public static final String labelorigem= "origem";
    public static final String labelDestino = "destino";


    /*-------------------------TABELAS-BD---------------------------*/

    private static final String NAME_TABLE_GOLEIRO = "Goleiro";
    private static final String NAME_TABLE_PARTIDA = "Partida";
    private static final String NAME_TABLE_JOGADA_DEFENSIVA = "JogadaDefensiva";
    private static final String NAME_TABLE_JOGADA_OFENSIVA = "JogadaOfensiva";
    private static final String NAME_TABLE_HISTORICO = "Historico";

    private static String NAME_TABLE_TIRO_META = "TiroMeta";
    private static String NAME_TABLE_REPOR_MAO = "ReporMao";
    private static String NAME_TABLE_DOMINIO = "Dominio";
    private static String NAME_TABLE_DEFESA_CAIDA = "DefCaida";
    private static String NAME_TABLE_REPOR_VOLEIO = "ReporVoleio";
    private static String NAME_TABLE_DEFESA_BASE = "DefBase";
    private static String NAME_TABLE_DEFESA_PE = "DefPe";
    private static String NAME_TABLE_DEFESA_PUNHO = "DefPunho";
    private static String NAME_TABLE_DEFESA_SOBRE_CABECA = "DefSobreCabeca";
    private static String NAME_TABLE_DEFESA_SAIDA = "DefSaida";

    /*----------------------------DESCRICAO-BD----------------------------*/

    public static final String DESCRICAO_DEFESA_BASE = "Defesa Base";
    public static final String DESCRICAO_DEFESA_CAIDA = "Defesa com Caída";
    public static final String DESCRICAO_DEFESA_PE = "Defesa com os Pés";
    public static final String DESCRICAO_DEFESA_PUNHO = "Defesa com Punho";
    public static final String DESCRICAO_DEFESA_SAIDA = "Defesa com Saída";
    public static final String DESCRICAO_DEFESA_SOBRE_CABECA = "Defesa Sobre Cabeça";
    public static final String DESCRICAO_DOMINIO = "Domínio de bola";
    public static final String DESCRICAO_REPOR_MAO = "Reposição com as mãos";
    public static final String DESCRICAO_REPOR_VOLEIO = "Reposição com Voleio";
    public static final String DESCRICAO_TIRO_META = "Tiro de Meta";

    public static final String DESCRICAO_GOLEIRO = NAME_TABLE_GOLEIRO;
    public static final String DESCRICAO_PARTIDA = NAME_TABLE_PARTIDA;
    public static final String DESCRICAO_JOGADA_DEFENSIVA = NAME_TABLE_JOGADA_DEFENSIVA;
    public static final String DESCRICAO_JOGADA_OFENSIVA = NAME_TABLE_JOGADA_OFENSIVA;
    public static final String DESCRICAO_HISTORICO = NAME_TABLE_HISTORICO;

    /*----------------------------SETORES-GOL----------------------------*/

    public static final String SETOR_CID = "CID - Canto Inferior Direito";
    public static final String SETOR_CSD = "CSD - Canto Superior Direito";
    public static final String SETOR_MI = "MI - Meio Inferior";
    public static final String SETOR_MS = "MS - Meio Superior";
    public static final String SETOR_CIE = "CIE - Canto Inferior Esquerdo";
    public static final String SETOR_CSE = "CSE - Canto Superior Esquerdo";
    public static final String SETOR_T = "T - Trave";
    public static final String SETOR_FCID = "FCID - Fora em Canto Inferior Direito";
    public static final String SETOR_FCSD = "FCSD - Fora em Canto Superior Direito";
    public static final String SETOR_FMS = "FMS - Fora em Meio Superior";
    public static final String SETOR_FCIE = "FCIE - Fora em Canto Inferior Esquerdo";
    public static final String SETOR_FCSE = "FCSE - Fora em Canto Superior Esquerdo";

    /*----------------------------SETORES-CAMPO----------------------------*/

    public static final String SETOR_DE = "DE - Defensivo Esquerdo";
    public static final String SETOR_ADE = "ADE - Área Defensiva Esquerda";
    public static final String SETOR_ADC = "ADC - Área Defensiva Centro";
    public static final String SETOR_PAD = "PAD - Pequena Área Defensiva";
    public static final String SETOR_ADD = "ADD - Área Defensiva Direita";
    public static final String SETOR_DD = "DD - Defensivo Direito";
    public static final String SETOR_MDE = "MDE - Meio Defensivo Esquerdo";
    public static final String SETOR_MDC = "MDC - Meio Defensivo Centro";
    public static final String SETOR_MDD = "MDD - Meio Defensivo Direito";
    public static final String SETOR_MOE = "MOE - Meio Ofensivo Esquerdo";
    public static final String SETOR_MOC = "MOC - Meio Ofensivo Centro";
    public static final String SETOR_MOD = "MOD - Meio Ofensivo Direita";
    public static final String SETOR_OE = "OE - Ofensivo Esquerdo";
    public static final String SETOR_AOE = "AOE - Área Ofensiva Esquerda";
    public static final String SETOR_AOC = "AOC - Área Ofensiva Centro";
    public static final String SETOR_PAO = "PAO - Pequena Área Ofensiva";
    public static final String SETOR_AOD = "AOD - Área Ofensiva Direita";
    public static final String SETOR_OD = "OD - Ofensivo direito";

    public static final String SETOR_EE = "EE - Escanteio Esquerdo";
    public static final String SETOR_ED = "ED - Escanteio Direito";


    /*----------------------------TIPOS-FINALIZACAO----------------------------*/

    public static final String FINALIZACAO_B_ROLANDO = "chute bola rolando";
    public static final String FINALIZACAO_FALTA = "chute de falta";
    public static final String FINALIZACAO_PENALTI = "pênalti";
    public static final String FINALIZACAO_CABECEIO = "cabeceio";

    /*----------------------------METODOS----------------------------*/

    public static String getNameTable(String descricao){
        switch (descricao) {
            case DESCRICAO_DEFESA_BASE: {
                return NAME_TABLE_DEFESA_BASE;
            }
            case DESCRICAO_DEFESA_CAIDA: {
                return NAME_TABLE_DEFESA_CAIDA;
            }
            case DESCRICAO_DEFESA_PE: {
                return NAME_TABLE_DEFESA_PE;
            }
            case DESCRICAO_DEFESA_PUNHO: {
                return NAME_TABLE_DEFESA_PUNHO;
            }
            case DESCRICAO_DEFESA_SAIDA: {
                return NAME_TABLE_DEFESA_SAIDA;
            }
            case DESCRICAO_DEFESA_SOBRE_CABECA: {
                return NAME_TABLE_DEFESA_SOBRE_CABECA;
            }
            case DESCRICAO_DOMINIO: {
                return NAME_TABLE_DOMINIO;
            }
            case DESCRICAO_REPOR_MAO: {
                return NAME_TABLE_REPOR_MAO;
            }
            case DESCRICAO_REPOR_VOLEIO: {
                return NAME_TABLE_REPOR_VOLEIO;
            }
            case DESCRICAO_TIRO_META: {
                return NAME_TABLE_TIRO_META;
            }


            case DESCRICAO_JOGADA_DEFENSIVA: {
                return NAME_TABLE_JOGADA_DEFENSIVA;
            }
            case DESCRICAO_JOGADA_OFENSIVA: {
                return NAME_TABLE_JOGADA_OFENSIVA;
            }
            case DESCRICAO_HISTORICO: {
                return NAME_TABLE_HISTORICO;
            }
            case DESCRICAO_GOLEIRO: {
                return NAME_TABLE_GOLEIRO;
            }
            case DESCRICAO_PARTIDA: {
                return NAME_TABLE_PARTIDA;
            }
            default:
                return "";
        }
    }


    private static ArrayList<String> listJogdas;

    public static ArrayList<String> getListJogdas(){
        listJogdas = new ArrayList<>();
        listJogdas.add(Constantes.DESCRICAO_DEFESA_BASE);
        listJogdas.add(Constantes.DESCRICAO_DEFESA_CAIDA);
        listJogdas.add(Constantes.DESCRICAO_DEFESA_PE);
        listJogdas.add(Constantes.DESCRICAO_DEFESA_PUNHO);
        listJogdas.add(Constantes.DESCRICAO_DEFESA_SAIDA);
        listJogdas.add(Constantes.DESCRICAO_DEFESA_SOBRE_CABECA);
        listJogdas.add(Constantes.DESCRICAO_DOMINIO);
        listJogdas.add(Constantes.DESCRICAO_REPOR_MAO);
        listJogdas.add(Constantes.DESCRICAO_REPOR_VOLEIO);
        listJogdas.add(Constantes.DESCRICAO_TIRO_META);

        return listJogdas;
    }

    public static ArrayList<String> getListSetoresGol(){
        ArrayList<String> arraySetorBolaFoi = new ArrayList<String>();

        arraySetorBolaFoi.add("Selecione o setor onde a bola foi no gol");
        arraySetorBolaFoi.add(Constantes.SETOR_CID);
        arraySetorBolaFoi.add(Constantes.SETOR_CSD);
        arraySetorBolaFoi.add(Constantes.SETOR_MI);
        arraySetorBolaFoi.add(Constantes.SETOR_MS);
        arraySetorBolaFoi.add(Constantes.SETOR_CIE);
        arraySetorBolaFoi.add(Constantes.SETOR_CSE);
        arraySetorBolaFoi.add(Constantes.SETOR_T);
        arraySetorBolaFoi.add(Constantes.SETOR_FCID);
        arraySetorBolaFoi.add(Constantes.SETOR_FCSD);
        arraySetorBolaFoi.add(Constantes.SETOR_FMS);
        arraySetorBolaFoi.add(Constantes.SETOR_FCIE);
        arraySetorBolaFoi.add(Constantes.SETOR_FCSE);

        return arraySetorBolaFoi;
    }

    public static ArrayList<String> getListSetoresCampo(String label){
        ArrayList<String> arraySetorBolaVeio = new ArrayList<String>();

        arraySetorBolaVeio.add("Selecione o setor "+label+" da bola");
        arraySetorBolaVeio.add(SETOR_DE);
        arraySetorBolaVeio.add(SETOR_ADE);
        arraySetorBolaVeio.add(SETOR_ADC);
        arraySetorBolaVeio.add(SETOR_PAD);
        arraySetorBolaVeio.add(SETOR_ADD);
        arraySetorBolaVeio.add(SETOR_DD);
        arraySetorBolaVeio.add(SETOR_MDE);
        arraySetorBolaVeio.add(SETOR_MDC);
        arraySetorBolaVeio.add(SETOR_MDD);
        arraySetorBolaVeio.add(SETOR_MOE);
        arraySetorBolaVeio.add(SETOR_MOC);
        arraySetorBolaVeio.add(SETOR_MOD);
        arraySetorBolaVeio.add(SETOR_OE);
        arraySetorBolaVeio.add(SETOR_AOE);
        arraySetorBolaVeio.add(SETOR_AOC);
        arraySetorBolaVeio.add(SETOR_PAO);
        arraySetorBolaVeio.add(SETOR_AOD);
        arraySetorBolaVeio.add(SETOR_OD);

        return arraySetorBolaVeio;
    }

    public static ArrayList<String> getListTiposFinalizacao(){
        ArrayList<String> arrayTipoFinalizacao = new ArrayList<String>();

        arrayTipoFinalizacao.add("Selecione o tipo de finalização");
        arrayTipoFinalizacao.add(FINALIZACAO_B_ROLANDO);
        arrayTipoFinalizacao.add(FINALIZACAO_FALTA);
        arrayTipoFinalizacao.add(FINALIZACAO_PENALTI);
        arrayTipoFinalizacao.add(FINALIZACAO_CABECEIO);

        return arrayTipoFinalizacao;
    }
}
