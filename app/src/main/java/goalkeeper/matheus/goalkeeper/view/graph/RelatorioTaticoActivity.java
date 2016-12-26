
package goalkeeper.matheus.goalkeeper.view.graph;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import bd.DBManager;
import goalkeeper.matheus.goalkeeper.R;
import goalkeeper.matheus.goalkeeper.util.Constantes;

//horizontalBarChart

public class RelatorioTaticoActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    protected HorizontalBarChart mChart;
    private DBManager mDb;
    int idGoleiro=0;
    int idPartida=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatoriotatico);

        setTitle(getIntent().getStringExtra("TITLE"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDb = new DBManager(this);
        idGoleiro = getIntent().getIntExtra("ID_GOLEIRO", 1);
        idPartida = getIntent().getIntExtra("ID_PARTIDA", 2);


        mChart = (HorizontalBarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false);

        IAxisValueFormatter xAxisFormatter = new SetorAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //    xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(0f); // only intervals of 1 day
        xAxis.setLabelCount(21);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        setData();

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        mChart.setFitBars(false);
        mChart.animateY(2500);
        mChart.animateX(2500);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.NONE);
        l.setFormSize(0f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        }

    private void setData() {

        float barWidth = 1f; //grossura das barras
        float spaceForBar = 2f; //numero de setores (acho que nao eh isso)
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        BarEntry x= new  BarEntry(0 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_DE) , Constantes.SETOR_DE); //id,qtd,label
        BarEntry x1= new  BarEntry(1 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_ADE) ,Constantes.SETOR_ADE); //id,qtd,label
        BarEntry x2= new  BarEntry(2 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_ADC) ,Constantes.SETOR_ADC); //id,qtd,label
        BarEntry x3= new  BarEntry(3 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_PAD) ,Constantes.SETOR_PAD); //id,qtd,label
        BarEntry x4= new  BarEntry(4 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_ADD) ,Constantes.SETOR_ADD); //id,qtd,label
        BarEntry x5= new  BarEntry(5 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_DD) ,Constantes.SETOR_DD); //id,qtd,label
        BarEntry x6= new  BarEntry(6 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_MDE) ,Constantes.SETOR_MDE); //id,qtd,label
        BarEntry x7= new  BarEntry(7 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_MDC) ,Constantes.SETOR_MDC); //id,qtd,label
        BarEntry x8= new  BarEntry(8 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_MDD) ,Constantes.SETOR_MDD); //id,qtd,label
        BarEntry x9= new  BarEntry(9 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_EE) ,Constantes.SETOR_EE); //id,qtd,label
        BarEntry x10= new  BarEntry(10 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_ED) ,Constantes.SETOR_ED); //id,qtd,label

        BarEntry x11= new  BarEntry(11 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_MOE));
        BarEntry x12= new  BarEntry(12 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_MOC));
        BarEntry x13= new  BarEntry(13 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_MOD));
        BarEntry x14= new  BarEntry(14 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_OE));
        BarEntry x15= new  BarEntry(15 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_AOE));
        BarEntry x16= new  BarEntry(16 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_AOC));
        BarEntry x17= new  BarEntry(17 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_PAO));
        BarEntry x18= new  BarEntry(18 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_AOD));
        BarEntry x19= new  BarEntry(19 * spaceForBar, mDb.countSetor(idGoleiro,idPartida,Constantes.SETOR_OD));
        yVals1.add(x);
        yVals1.add(x1);
        yVals1.add(x2);
        yVals1.add(x3);
        yVals1.add(x4);
        yVals1.add(x5);
        yVals1.add(x6);
        yVals1.add(x7);
        yVals1.add(x8);
        yVals1.add(x9);
        yVals1.add(x10);
        yVals1.add(x11);
        yVals1.add(x12);
        yVals1.add(x13);
        yVals1.add(x14);
        yVals1.add(x15);
        yVals1.add(x16);
        yVals1.add(x17);
        yVals1.add(x18);
        yVals1.add(x19);

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Setor campo X Ocorrência jogada");
            set1.setColors(Color.parseColor("#00BCD4"),
                    Color.parseColor("#FF5722"),Color.parseColor("#795548"),
                    Color.parseColor("#9E9E9E"),Color.parseColor("#FFC107"),
                    Color.parseColor("#8BC34A"),Color.parseColor("#009688"),
                    Color.parseColor("#2196F3"),Color.parseColor("#9C27B0"),
                    Color.parseColor("#F44336"),Color.parseColor("#607D8B"));


            ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
            dataSets.add(set1);


            BarData data = new BarData(set1);;
            //data.setValueTextSize(10f);
         //   data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart.setData(data);
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            case R.id.ajuda1:{
                Dialog builder = new Dialog(this);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.campo_setores_mais);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();

                return true;
            }
            case R.id.actionToggleValues: {
                List<IBarDataSet> sets = mChart.getData()
                        .getDataSets();

                for (IBarDataSet iSet : sets) {

                    IBarDataSet set = (BarDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : mChart.getData().getDataSets())
                    ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("relatorioParcial" + System.currentTimeMillis(), 100)) {
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
