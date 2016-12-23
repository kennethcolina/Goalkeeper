
package goalkeeper.matheus.goalkeeper.graph;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import goalkeeper.matheus.goalkeeper.R;

import java.util.ArrayList;

public class BarChartActivity1 extends AppCompatActivity implements
        OnChartValueSelectedListener {

    protected BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart1);
        setTitle("Relatório Tático");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mChart = (BarChart) findViewById(R.id.chart1);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new SetorAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
    //    xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
    //    leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
    //    rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
       //  l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

      /*  LegendEntry legendaminha1 = new LegendEntry("MDC", Legend.LegendForm.SQUARE, 15f,15f,null, Color.BLACK);
        LegendEntry legendaminha2 = new LegendEntry("MED", Legend.LegendForm.SQUARE, 15f,15f,null, Color.BLUE);
        LegendEntry legendaminha3 = new LegendEntry("ABC", Legend.LegendForm.SQUARE, 15f,15f,null, Color.RED);
        LegendEntry legendaminha4 = new LegendEntry("KDE", Legend.LegendForm.SQUARE, 15f,15f,null, Color.GRAY);
        LegendEntry legendaminha5 = new LegendEntry("PO", Legend.LegendForm.SQUARE, 15f,15f,null, Color.GREEN);
        LegendEntry legendaminha6 = new LegendEntry("PA", Legend.LegendForm.SQUARE, 15f,15f,null, Color.YELLOW);
        LegendEntry legendaminha7 = new LegendEntry("DC", Legend.LegendForm.SQUARE, 15f,15f,null, Color.CYAN);
        LegendEntry legendaminha8 = new LegendEntry("LKP", Legend.LegendForm.SQUARE, 15f,15f,null, Color.DKGRAY);
        LegendEntry legendaminha9 = new LegendEntry("PQ", Legend.LegendForm.SQUARE, 15f,15f,null, Color.MAGENTA);
        LegendEntry legendaminha10 = new LegendEntry("PPO", Legend.LegendForm.SQUARE, 15f,15f,null, Color.LTGRAY);
        l.setCustom(new LegendEntry[] {legendaminha1,legendaminha2,legendaminha3,legendaminha4,legendaminha5,legendaminha6,legendaminha7,legendaminha8,legendaminha9,legendaminha10});

*/
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(4, 50);


        // mChart.setDrawLegend(false);
    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        BarEntry x= new  BarEntry(0, 2,"A");
        BarEntry x1= new  BarEntry(1, 5,"D");
        BarEntry x2= new  BarEntry(2, 2,"A");
        BarEntry x3= new  BarEntry(3, 15,"D");
        BarEntry x4= new  BarEntry(4, 2,"A");
        BarEntry x5= new  BarEntry(5, 5,"D");
        BarEntry x6= new  BarEntry(6, 1,"A");
        BarEntry x7= new  BarEntry(7, 0,"D");
        BarEntry x8= new  BarEntry(8, 2,"A");
        BarEntry x9= new  BarEntry(9, 3,"D");
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

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Setores do Campo");
            //set1.setColors(ColorTemplate.MATERIAL_COLORS);
            set1.setColors(Color.BLUE,
                    Color.RED,
                    Color.GRAY,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.DKGRAY,
                    Color.MAGENTA,
                    Color.LTGRAY);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
        //    data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

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
        MPPointF position = mChart.getPosition(e, AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() { }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
