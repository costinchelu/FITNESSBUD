package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Map;

/*
* TEMA
* Aplicatie mobila pentru monitorizarea activitatilor personale (greutate, ore, durate si continut
* mese zilnice, efort fizic depus etc.)
*
* screenshoturi disponibile in google docs:
* https://docs.google.com/document/d/1_X8bhtxrBORltVnvNPVXoeRnVmluWQJFUK2lrZAm-Jc/edit?usp=sharing
*
* */

/*
* MainActivity
*
* Reprezinta activitatea root din care se pot accesa celelate  activitati.
* Unele activitati pot fi accesate prin intermediul butoanelor (listele, rapoarte)
* Celelalte activitati (Utilizator, Harta, Despre) sunt accesate prin intermediul meniului
* (Kebab Menu, Vertical Elipsis sau Navigation vertical menu)
* Pentru aceasta activitate am folosit doar startActivity (nefiind nevoie de transfer de informatii)
*
*
* (Nota): design-ul aplicatiei a fost conceput pentru display
* 1080x1920: 420dp (am folosit emulator Pixel XL). Pentru display cu rezolutie mai mica, pozitia
* widgeturilor se poate modifica.
*
* */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        Button lMasa = findViewById(R.id.btn_add_masa);
        Button lFitness = findViewById(R.id.btn_add_fitness);
        Button lJson = findViewById(R.id.btn_acces_json);
        Button rapoarte = findViewById(R.id.btn_rapoarte);

        lMasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateMese.class);
                startActivity(intent);
            }
        });

        lFitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateFitness.class);
                startActivity(intent);
            }
        });

        lJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateComunicatii.class);
                startActivity(intent);
            }
        });

        rapoarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitateRapoarte.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // poate fi implementata si cu operatorul conditional case
        // (pe care l-am folosit atunci cand am avut mai multe optiuni (ActivitateMese)
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_menu_map) {
            Intent intent = new Intent(getApplicationContext(), ActivitateMap.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.main_menu_utilizator) {
            Intent intent = new Intent(getApplicationContext(), ActivitateUtilizator.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.main_menu_despre) {
            Intent intent = new Intent(getApplicationContext(), ActivitateDespre.class);
            startActivity(intent);
        }

        return true;
    }
}
/*
BAREM

1. Definirea a minim șase activități, populate cu controale vizuale corespunzătoare; (0.5 p.)

    15 activitati


2. Utilizarea de controale variate (Button, TextView, EditView, CheckBox, Spinner, ProgressBar,
SeekBar, Switch, RatingBar, ImageView, DatePicker sau TimePicker); (1 p.)

    am utilizat: Button, TextView, EditView, CheckBox, Spinner, RatingBar, ImageView, ImageButton,
    BarChart, MapFragment


3. Utilizarea a minim un formular de introducere a datelor; (0.5 p.)

    activitate_despre (rating in SharedPreferences)
    activitate_fitness_add
    activitate_fitness_edit
    activitate_mese_add
    activitate_mese_edit
    activitate_rapoarte (data pentru raport2 in SharedPreferences)
    activitate_utilizator (datele utilizatorului in SharedPreferences)


4. Transferul de parametri (obiecte proprii și primitive) între minim două activități; (0.5 p.)

    transferul dintre activitati cu startActivityForResult se face:
        ActivitateMese - ActivitateMeseAdd
        ActivitateMese - ActivitateMeseEdit
        ActivitateFitness - ActivitateFitnessAdd
        ActivitateFitness - ActivitateFitnessEdit
        ActivitateUtilizator - photoPicker


5. Implementarea unui adaptor personalizat (cel puțin trei controale vizuale); (0.5 p.)

    sunt 4 adaptori personalizati: AdapterMasa, AdapterFitness, JsonAdapter (pentru afisarea
    personalizata in ActivitateComunicatii a informatiilor primite de la serverul JSON) si
    AdapterRaport pentru afisarea personalizata a listei in ActivitateRaport1. Toti adaptorii au
    cel putin trei controale vizuale.


6. Implementarea și utilizarea unor operații asincrone; (1 p.)

    Se utilizeaza pentru afisarea unei liste de informatii in ActivitateComunicatii


7. Utilizarea claselor pentru accesul la resurse externe (din rețea); (0.5 p.)

    Am folosit clasa JsonItem si JsonCompozitie pentru parsarea informatieo JSON de forma
    {
      "imagine": "https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/chickenjalfrezi_91772_16x9.jpg",
      "nume": "orez curry cu pui",
      "portie": "350g",
      "calorii": "290 kcal",
      "compozitie": {
        "carbohidrati": "39g",
        "grasimi": "2.7g",
        "proteine": "24g"
      }
    },


8. Prelucrarea de fișiere JSON. Fișierele trebuie să conțină cel puțin 3 noduri dispuse pe niveluri
diferite. Fiecare nod trebuie sa aibă cel puțin 3 atribute; (0.5 p.)

    fisierul este preluat de la adresa https://api.myjson.com/bins/d0ar4


9. Utilizarea fișierelor de preferințe; (0.5 p.)

    fisierele de preferinte au fost utilizate atat pentru stocarea de informatii cat si pentru
    recuperarea informatiilor in: ActivitateUtilizator, ActivitateDespre, ActivitateGrafic,
    ActivitateRapoarte, ActivitateRaport2


10. Crearea unei baze de date SQLite cu minim două tabele cu legături între ele. Implementarea
operațiilor de tip DDL; (0.5 p.)

    Baza de date contine 2 tabele: mese si fitness. Tabelele nu au legaturi intre ele.


11. Implementarea operațiilor de tip DML. Pentru fiecare tabela să se implementeze cel puțin o clasă
de acces la date care să includă metode de inserare, modificare, ștergere și selecție. Toate
metodele trebuie apelate; (1 p.)

    Au fost incluse metode de inserare, modificare, stergere si diverse metode de selectie.


12. Definirea a minim două rapoarte (afișarea pe ecranul dispozitivului mobil, într-o activitate) a
informațiilor preluate din baza de date. Rapoartele sunt diferite din punct de vedere al structurii;
(0.5 p.)

    Sunt definite 2 rapoarte accesate prin intermediul ActivitateRapoarte: afisarea activitatilor
    fizice din luna curenta si afisarea bilantului energetic pe o data aleasa de utilizator


13. Utilizarea de elemente de grafică bidimensională, cu valori preluate din baza de date (1 p.)
14. Prelucrarea elementelor de tip imagine din diferite surse (imagini statice, preluate prin rețea,
încărcate din galeria dispozitivului mobil, preluate din baze de date); Imaginile trebuie preluate
din minim două surse; (0.5 p.)

    Activitate grafic afiseaza un bar chart cu date culese din baza de date (datele reprezinta suma
    caloriilor pe fiecare masa (mic-dejun, pranz, cina, gustari) la o data aleasa de utilizator.


15. Stilizarea aplicației mobile (de exemplu, se creează o temă nouă în fișierul styles.xml sau
modificați fontul, culorile componentelor vizuale); (0.75 p)

    Am implementat modificari ale componentelor vizuale si ale fontului si am definit valori noi
    in colors.xml, dimens.xml. Am definit AppThemeWarm ca si tema a aplicatiei in fisierul styles.xml
    precum si iv_logo pentru logo-ul aplicatiei in meniul principal.


16. Implementarea unei funcționalități pe bază de Google Maps; (0.25 p.)

    Prin intermediul ActivitateMap disponibila dinn meniul activitatii principale, am oferit accesul
    la o locatie predefinita. Locatia Facultatii de Cibernetica, Statistica si Informatica Economica


 */