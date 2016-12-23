
package goalkeeper.matheus.goalkeeper.graph;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;

//RelatorioGeralActivity

public class RelatorioGeralActivity extends AppCompatActivity {

    private RadarChart mChart;
    private DBManager mDb;
    int idGoleiro=0;

    protected String[] mLabels = new String[] {
            "Acerto", "Erro"
    };

    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radarchart_noseekbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Análise Geral");
        idGoleiro = getIntent().getIntExtra("ID_GOLEIRO", 0);
        mDb = new DBManager(this);
        tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

      //  TextView tv = (TextView) findViewById(R.id.textView);
       // tv.setTypeface(tf);
       // tv.setTextColor(Color.WHITE);
       // tv.setBackgroundColor(Color.rgb(60, 65, 82));

        mChart = (RadarChart) findViewById(R.id.chart1);
        //mChart.setBackgroundColor(Color.rgb(60, 65, 82));

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.BLACK);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.BLACK);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData();

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"D. Base",
                    "D. Caída",
                    "D. Pé",
                    "D. Punho",
                    "D. Saída",
                    "D. Sobre Cabeça",
                    "Domínio",
                    "R. Mão",
                    "R. Voleio",
                    "T. Meta"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.BLACK);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(tf);
        yAxis.setLabelCount(10, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(tf);
        l.setXEntrySpace(17f);
        l.setYEntrySpace(15f);
        l.setTextColor(Color.BLACK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.radar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleRotate: {
                if (mChart.isRotationEnabled())
                    mChart.setRotationEnabled(false);
                else
                    mChart.setRotationEnabled(true);
                mChart.invalidate();
                break;
            }

            case R.id.actionSave: {
                if (mChart.saveToGallery("relatorioGeral" + System.currentTimeMillis(), 100)) {
                    Toast.makeText(getApplicationContext(), "Sucesso!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Falhou!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }

            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
        }
        return true;
    }

    public void setData() {

        float total = 0;
        float erros = 0;
        float acertos = 0;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        total = mDb.countJogada(idGoleiro,"Defesa Base", true);
        erros = mDb.errosJogada(idGoleiro,"Defesa Base", true);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Defesa Caída", true);
        erros = mDb.errosJogada(idGoleiro,"Defesa Caída", true);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Defesa Pé", true);
        erros = mDb.errosJogada(idGoleiro,"Defesa Pé", true);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Defesa Punho", true);
        erros = mDb.errosJogada(idGoleiro,"Defesa Punho", true);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Defesa Saída", true);
        erros = mDb.errosJogada(idGoleiro,"Defesa Saída", true);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Defesa Sobre Cabeça", true);
        erros = mDb.errosJogada(idGoleiro,"Defesa Sobre Cabeça", true);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Domínio", false);
        erros = mDb.errosJogada(idGoleiro,"Domínio", false);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Reposição Mão", false);
        erros = mDb.errosJogada(idGoleiro,"Reposição Mão", false);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Reposição Voleio", false);
        erros = mDb.errosJogada(idGoleiro,"Reposição Voleio", false);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        total = mDb.countJogada(idGoleiro,"Tiro Meta", false);
        erros = mDb.errosJogada(idGoleiro,"Tiro Meta", false);
        acertos = total - erros;
        entries1.add(new RadarEntry( (erros / total)*100 ));
        entries2.add(new RadarEntry( (acertos / total)*100 ));

        RadarDataSet set1 = new RadarDataSet(entries1, mLabels[1]);
        set1.setColor(Color.parseColor("#FF5722"));
        set1.setFillColor(Color.parseColor("#FF5722"));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2,mLabels[0]);
        set2.setColor(Color.parseColor("#00BCD4")); //borda
        set2.setFillColor(Color.parseColor("#00BCD4")); //preenchimento
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(tf);
        data.setValueTextSize(10f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);
        mChart.invalidate();
    }
}
