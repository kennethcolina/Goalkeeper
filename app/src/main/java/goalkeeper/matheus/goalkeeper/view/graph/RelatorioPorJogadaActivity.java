
package goalkeeper.matheus.goalkeeper.view.graph;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import goalkeeper.matheus.goalkeeper.util.Constantes;

import java.util.ArrayList;

//RelatorioPorJogadaActivity

public class RelatorioPorJogadaActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    private PieChart mChartDefBase;
    private PieChart mChartDefCaida;
    private PieChart mChartDefPe;
    private PieChart mChartDefPunho;
    private PieChart mChartDefSaida;
    private PieChart mChartDefSobreCabeca;
    private PieChart mChartDominio;
    private PieChart mChartReporMao;
    private PieChart mChartReporVoleio;
    private PieChart mChartTiroMeta;
    private DBManager mDb;
    int idGoleiro=0;

    protected String[] mLabels = new String[] {
            "Acerto", "Erro"
    };
    
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorioporjogada);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Análise Individual de Ações");
        idGoleiro = getIntent().getIntExtra("ID_GOLEIRO", 0);
        mDb = new DBManager(this);
        tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

        mChartDefBase = (PieChart) findViewById(R.id.chart1);
        configurar(mChartDefBase, Constantes.DESCRICAO_DEFESA_BASE, true);

        mChartDefCaida = (PieChart) findViewById(R.id.chart2);
        configurar(mChartDefCaida, Constantes.DESCRICAO_DEFESA_CAIDA, true);

        mChartDefPe= (PieChart) findViewById(R.id.chart3);
        configurar(mChartDefPe, Constantes.DESCRICAO_DEFESA_PE, true);

        mChartDefPunho = (PieChart) findViewById(R.id.chart4);
        configurar(mChartDefPunho, Constantes.DESCRICAO_DEFESA_PUNHO, true);

        mChartDefSaida= (PieChart) findViewById(R.id.chart5);
        configurar(mChartDefSaida, Constantes.DESCRICAO_DEFESA_SAIDA, true);

        mChartDefSobreCabeca= (PieChart) findViewById(R.id.chart6);
        configurar(mChartDefSobreCabeca, Constantes.DESCRICAO_DEFESA_SOBRE_CABECA, true);

        mChartDominio= (PieChart) findViewById(R.id.chart7);
        configurar(mChartDominio, Constantes.DESCRICAO_DOMINIO, false);

        mChartReporMao= (PieChart) findViewById(R.id.chart8);
        configurar(mChartReporMao, Constantes.DESCRICAO_REPOR_MAO, false);

        mChartReporVoleio = (PieChart) findViewById(R.id.chart9);
        configurar(mChartReporVoleio, Constantes.DESCRICAO_REPOR_VOLEIO, false);

        mChartTiroMeta = (PieChart) findViewById(R.id.chart10);
        configurar(mChartTiroMeta, Constantes.DESCRICAO_TIRO_META, false);
    }

    private void configurar(PieChart chart, String jogada, boolean flaDefensiva) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf"));

        int total = mDb.countJogada(idGoleiro, jogada, flaDefensiva);
        chart.setCenterText(txtCenter(jogada, total));

        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);

        setData(chart, jogada, flaDefensiva, total);



        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }
    private void setData(PieChart chart, String jogada, boolean flagDefensiva, int total) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        int erros = 0;

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        erros = mDb.errosJogada(idGoleiro, jogada, flagDefensiva);
        entries.add(new PieEntry((total-erros), mLabels[0]));
        entries.add(new PieEntry(erros, mLabels[1]));

        PieDataSet dataSet = new PieDataSet(entries, jogada);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : new int []{Color.parseColor("#00BCD4"),Color.parseColor("#FF5722")})
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    private SpannableString txtCenter(String jogada, int total) {
        jogada = jogada.replace(" ","\n");
        SpannableString s = new SpannableString(jogada+"\nTotal: "+total);
        s.setSpan(new RelativeSizeSpan(1.2f), 0, jogada.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, jogada.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, jogada.length(), 0);
        s.setSpan(new RelativeSizeSpan(1f), jogada.length(), s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), jogada.length(), s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), jogada.length(), s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            case R.id.actionSave: {
                if (mChartDefBase.saveToGallery("DefBase" + System.currentTimeMillis(), 100)&&
                        mChartDefCaida.saveToGallery("DefCaida" + System.currentTimeMillis(), 100)&&
                        mChartDefPe.saveToGallery("DefPe" + System.currentTimeMillis(), 100)&&
                        mChartDefPunho.saveToGallery("DefPunho" + System.currentTimeMillis(), 100)&&
                        mChartDefSaida.saveToGallery("DefSaida" + System.currentTimeMillis(), 100)&&
                        mChartDefSobreCabeca.saveToGallery("DefSobreCabeca" + System.currentTimeMillis(), 100)&&
                        mChartDominio.saveToGallery("Dominio" + System.currentTimeMillis(), 100)&&
                        mChartReporMao.saveToGallery("ReporMao" + System.currentTimeMillis(), 100)&&
                        mChartReporVoleio.saveToGallery("ReporVoleio" + System.currentTimeMillis(), 100)&&
                        mChartTiroMeta.saveToGallery("TiroMeta" + System.currentTimeMillis(), 100)) {
                    Toast.makeText(getApplicationContext(), "Sucesso!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Falhou!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }
}
