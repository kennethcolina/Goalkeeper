package goalkeeper.matheus.goalkeeper.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by kenneth on 22/12/16.
 */
public class Mensagem {

    public static void alerta(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Ops");
        alertDialog.setMessage("Favor, preencher todos os campos antes de continuar.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public static void alertaPenalti(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("O setor de origem da bola não condiz com o tipo de finalização!\n" +
                "Ps: O pênalti é originado no setor \"ADC - Área Defensiva Centro\".");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public static void alertaFalta(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("O setor de origem da bola não condiz com o tipo de finalização!\n" +
                "Ps: Chutes de falta não podem ter origem na área defensiva.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void alertaDefSaidaEscanteio(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("O setor de origem da bola não condiz com o motivo da saída!\n" +
                "Ps: Selecione os setores EE ou ED para o motivo \"Escanteio\".");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();

    }

    public void alertaDefSaidaRolando(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("O setor de origem da bola não condiz com o motivo da saída!\n" +
                "Ps: Só selecione os setores EE ou ED para o motivo \"Escanteio\".");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void alertaGol(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("O setor destino da bola não condiz com gol!\n");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
