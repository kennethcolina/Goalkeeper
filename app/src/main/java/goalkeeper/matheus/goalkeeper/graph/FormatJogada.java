package goalkeeper.matheus.goalkeeper.graph;

import com.github.mikephil.charting.data.PieEntry;

/**
 * Created by Matheus on 23/12/2016.
 */
public class FormatJogada {
    String jogada;

    public static String format(String jogada) {
        switch (jogada) {
            case "Defesa Base": {
                return "DefBase";
            }
            case "Defesa Caída": {
                return "DefCaida";
            }
            case "Defesa Pé": {
                return "DefPe";
            }
            case "Defesa Punho": {
                return "DefPunho";
            }
            case "Defesa Saída": {
                return "DefSaida";
            }
            case "Defesa Sobre Cabeça": {
                return "DefSobreCabeca";
            }
            case "Domínio": {
                return "Dominio";
            }
            case "Reposição Mão": {
                return "ReporMao";
            }
            case "Reposição Voleio": {
                return "ReporVoleio";
            }
            case "Tiro Meta": {
                return "TiroMeta";
            }
            default:
                return "TiroMeta";
        }
    }
}
