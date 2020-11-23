package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
*
* Al doilea raport calculeaza bilantul zilei aleasa in ActivitateRapoarte. Am ales stocarea
* acestei date in SharedPreferences, dar se putea face transferul datei si prin intermediul
* startActivityForIntent. Listele de activitati (mese si fitness) vor fi incarcate din
*  baza de date. Dupa calcularea bilantului, acesta va fi afisat, impreuna cu alte date,
* in textView-uri
*
*
* Afisarea raportului bilant se face personalizat, astfel ca mesajul de depasire a necesarului
* zilnic va fi modificat prin schimbarea culorii (in rosu, fata de verde). Pentru situatiile
* cand ziua aleasa nu are intrari (mese ori activitati fizice0 se va afisa un mesaj corespunzator
*
* Activitatea are doua butoane. Unul pentru intoarcerea in ActivitateRapoarte iar celalalt pentru
* accesarea ActivitateGrafic.
*
* */

public class ActivitateRaport2 extends AppCompatActivity {

    List<Fitness> listaFitness = new ArrayList<>();
    List<Masa> listaMese = new ArrayList<>();

    TextView tvData;
    TextView tvNecesar;
    TextView tvCaloriiMese;
    TextView tvCaloriiFitness;
    TextView tvBilant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_raport2);

        initComponents();
    }

    private void initComponents() {
        tvData = findViewById(R.id.raport_2_tv_data);
        tvNecesar = findViewById(R.id.raport_2_tv_necesar);
        tvCaloriiMese = findViewById(R.id.raport_2_tv_calorii_mese);
        tvCaloriiFitness = findViewById(R.id.raport_2_tv_calorii_fitness);
        tvBilant = findViewById(R.id.raport_2_tv_bilant);
        Button btnBack = findViewById(R.id.raport_2_btn_back);
        Button btnGrafic = findViewById(R.id.raport_2_btn_grafic);

        SharedPreferences sharedPref = getSharedPreferences(ActivitateRapoarte.SHARED_P, Context.MODE_PRIVATE);
        String dataAleasa = sharedPref.getString(ActivitateRapoarte.SP_DATA_RAPORT, "");

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        DataRepository  dataRepository = new DataRepository(controller);

        dataRepository.open();
        listaFitness = dataRepository.bilantFitness(dataAleasa);
        listaMese = dataRepository.bilantMese(dataAleasa);
        dataRepository.close();

        Float caloriiMese = calculeazaCaloriiMese(listaMese);
        int caloriiFitness = calculeazaCaloriiFitness(listaFitness);
        String extractNecesar = sharedPref.getString(ActivitateUtilizator.SP_NECESAR, "2200");
        Integer necesarZilnic;
        try {
            necesarZilnic = Integer.valueOf(extractNecesar);
        } catch (NumberFormatException e) {
            necesarZilnic = 2200;
            e.printStackTrace();
        }

        Float bilant = necesarZilnic - caloriiMese + caloriiFitness;
        afiseazaRaport(dataAleasa, caloriiMese, caloriiFitness, necesarZilnic, bilant);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRaport2.this, ActivitateRapoarte.class);
                startActivity(intent);
            }
        });

        btnGrafic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRaport2.this, ActivitateGrafic.class);
                startActivity(intent);
            }
        });
    }

    private void afiseazaRaport(String data, Float caloriiMese, int caloriiFitness,
                                Integer necesarZilnic, Float bilant) {

        if(caloriiMese == 0 && caloriiFitness == 0) {
            int color = getResources().getColor(R.color.colorRed);
            tvData.setTextColor(color);
            tvData.setAllCaps(true);
            String dataString = String.format(getString(R.string.raport_2_activitati_inexistente), data);
            tvData.setText(dataString);
        } else {
            String dataString = String.format(getString(R.string.raport_2_afisare_data), data);
            String necesarString = String.format(getString(R.string.raport_2_afisare_necesar), necesarZilnic);
            String caloriiMString = String.format(getString(R.string.raport_2_afisare_calorii_mese), caloriiMese.intValue());
            String caloriiFString = String.format(getString(R.string.raport_2_afisare_calorii_fitness), caloriiFitness);

            tvData.setText(dataString);
            tvNecesar.setText(necesarString);
            tvCaloriiMese.setText(caloriiMString);
            tvCaloriiFitness.setText(caloriiFString);

            if(bilant > 0) {
                String bilantStringA = String.format(getString(R.string.raport_2_calorii_economisite), bilant.intValue());
                int color = getResources().getColor(R.color.colorGreen);
                tvBilant.setTextColor(color);
                tvBilant.setText(bilantStringA);
            } else {
                bilant *= -1;
                String bilantStringB = String.format(getString(R.string.raport_2_calorii_surplus), bilant.intValue());
                int color = getResources().getColor(R.color.colorRed);
                tvBilant.setTextColor(color);
                tvBilant.setAllCaps(true);
                tvBilant.setText(bilantStringB);
            }
        }
    }

    private int calculeazaCaloriiFitness(List<Fitness> listaFitness) {
        int output = 0;
        if(listaFitness != null) {
            for(int i = 0; i < listaFitness.size(); i++) {
                output += listaFitness.get(i).getNrCalorii();
            }
        }
        return output;
    }

    private float calculeazaCaloriiMese(List<Masa> listaMese) {
        float output = 0;
        if(listaMese != null) {
            for(int i = 0; i < listaMese.size(); i++) {
                float nrPortii = listaMese.get(i).getNrPortii();
                int caloriiPerPortie = listaMese.get(i).getNrCalorii();
                float calculCuPortii = nrPortii * caloriiPerPortie;
                output += calculCuPortii;
            }
        }
        return output;
    }

}
