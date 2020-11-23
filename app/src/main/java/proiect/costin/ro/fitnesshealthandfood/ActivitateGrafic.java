package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/*
*
* Activitate accesibila prin intermediul Raportului 2. Foloseste data disponibila pentru acest raport
* pentru a trasa un grafic de bare care cuprinde calorii din mese pe perioada zilei.
* Graficul va avea 4 bare (mic-dejun, pranz, cina, gustari).
* Foloseste dependenta MPAndroidChart.
* Datele sunt preluate din baza de date a aplicatiei folosind o metoda proprie care returneaza
* un ArrayList<BarEntry> prin procesarea interogarii si calcularii datelor de pe axa Y in
* interiorul metodei. Daca nu sunt intrari la acea data se va afisa un mesaj corespunzator
* Activitatea contine si un buton pentru intoarcerea in ActivitateRapoarte
*
* */

public class ActivitateGrafic extends AppCompatActivity {

    // valoare pentru tematizarea graficului
    public static final int[] COLORFUL_COLORS = {
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53)
    };

    private BarChart chart;
    private TextView tvDescriere;

    List<Masa> mese = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_grafic);

        initComponents();
    }

    private void initComponents() {
        chart = findViewById(R.id.grafic_chart);
        tvDescriere = findViewById(R.id.grafic_tv_descriere);
        Button btnRapoarte = findViewById(R.id.grafic_btn_back);

        SharedPreferences sharedPref = getSharedPreferences(ActivitateRapoarte.SHARED_P, Context.MODE_PRIVATE);
        String dataAleasa = sharedPref.getString(ActivitateRapoarte.SP_DATA_RAPORT, "");

        ArrayList<BarEntry> dateIntrare = pregatireDate(dataAleasa);
        if(dateIntrare != null) {
            afiseazaGrafic(dateIntrare);
        }

        btnRapoarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateGrafic.this, ActivitateRapoarte.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<BarEntry> pregatireDate(String data) {
        if(mese == null) {
            int color = getResources().getColor(R.color.colorRed);
            tvDescriere.setTextColor(color);
            tvDescriere.setAllCaps(true);
            String dataString = String.format(getString(R.string.grafic_2_fara_mese), data);
            tvDescriere.setText(dataString);
            return null;
        }
        else {
            int color = getResources().getColor(R.color.colorGreen);
            tvDescriere.setTextColor(color);
            String dataString = String.format(getString(R.string.grafic_2_cu_mese), data);
            tvDescriere.setText(dataString);

            DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
            DataRepository dataRepository = new DataRepository(controller);
            dataRepository.open();
            ArrayList<BarEntry> valori = dataRepository.barChart(data);
            dataRepository.close();
            return valori;
        }
    }

    private void afiseazaGrafic(ArrayList<BarEntry> valori) {
        String descriere = getString(R.string.graph_description);
        BarDataSet barDataSet = new BarDataSet(valori, descriere);
        barDataSet.setColors(COLORFUL_COLORS);

        String[] perioade = getResources().getStringArray(R.array.chart_x_axis);
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(perioade));
        xAxis.setLabelCount(perioade.length);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        BarData data = new BarData(barDataSet);
        chart.animateY(1500);
        chart.setData(data);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.getDescription().setEnabled(false);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
    }
}
