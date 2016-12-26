package goalkeeper.matheus.goalkeeper.view.graph;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class SetorAxisValueFormatter implements IAxisValueFormatter {

    private BarLineChartBase<?> chart;

    public SetorAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int x = ((int) value) / 2; //valor dado como space (numero de barras) na classe RelatorioTaticoActivity
        switch (x) {
            case 0:
                return "DE";
            case 1:
                return "ADE";
            case 2:
                return "ADC";
            case 3:
                return "PAD";
            case 4:
                return "ADD";
            case 5:
                return "DD";
            case 6:
                return "MDE";
            case 7:
                return "MDC";
            case 8:
                return "MDD";
            case 9:
                return "EE";
            case 10:
                return "ED";

            case 11:
                return "MOE";
            case 12:
                return "MOC";
            case 13:
                return "MOD";
            case 14:
                return  "OE";
            case 15:
                return "AOE";
            case 16:
                return  "AOC";
            case 17:
                return  "PAO";
            case 18:
                return "AOD";
            case 19:
                return "OD";
            default:
                return "Setor";
        }
    }
}