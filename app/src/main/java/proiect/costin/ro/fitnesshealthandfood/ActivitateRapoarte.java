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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/*
*
* Pagina de titlu pentru accesarea rapoartelor. Raportul 2 va rula dupa introducerea datei pentru
* care se face interogarea in baza de date. Data va fi validata cu un mecanism similar cu cel
* folosit la introducerea intrarii initiale.
* Rapoartele 1 si 2 sunt accesibile prin intermediul butoanelor.
* De asemenea exista si un meniu de navigare.
*
* */

public class ActivitateRapoarte extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public static final String SHARED_P = "MyPrefs";
    public final static String SP_DATA_RAPORT = "data_raport_2";

    private SharedPreferences sharedPref;

    EditText etDataRaport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_rapoarte);

        initComponents();
    }

    private void initComponents() {
        ImageButton btnBack = findViewById(R.id.btn_rapoarte_back_to_main);
        Button btnRaport1 = findViewById(R.id.btn_raport_1);
        Button btnRaport2 = findViewById(R.id.btn_raport_2);
        etDataRaport = findViewById(R.id.et_data_raport_2);

        sharedPref = getSharedPreferences(SHARED_P, Context.MODE_PRIVATE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRapoarte.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRaport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitateRapoarte.this, ActivitateRaport1.class);
                startActivity(intent);
            }
        });

        btnRaport2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    String dataIntrodusa = etDataRaport.getText().toString();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(SP_DATA_RAPORT, dataIntrodusa);
                    editor.apply();
                    Intent intent = new Intent(ActivitateRapoarte.this, ActivitateRaport2.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validareInput() {
        int anulCurent = Calendar.getInstance().get(Calendar.YEAR);
        String dataIntrodusa = etDataRaport.getText().toString();

        if(dataIntrodusa.trim().isEmpty() || !validareData(dataIntrodusa) ||
                dataIntrodusa.trim().length() != 10) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_data_activitate), Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            int anIntrodus = Integer.parseInt(dataIntrodusa.substring(6));
            int lunaIntrodusa = Integer.parseInt(dataIntrodusa.substring(3, 5));
            int ziIntrodusa = Integer.parseInt(dataIntrodusa.substring(0, 2));

            if(anIntrodus > anulCurent || (anIntrodus + 1) < anulCurent) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.validare_an_curent), Toast.LENGTH_LONG).show();
                return false;
            }
            else if(ziIntrodusa < 1 || ziIntrodusa > 31 || lunaIntrodusa < 1 || lunaIntrodusa > 12 ) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.validare_zi_luna), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    private boolean validareData(String inputData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            simpleDateFormat.parse(inputData);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_rapoarte_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_rapoarte_principal) {
            Intent intent = new Intent(ActivitateRapoarte.this, MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menu_rapoarte_utilizator) {
            Intent intent = new Intent(ActivitateRapoarte.this, ActivitateUtilizator.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.main_menu_despre) {
            Intent intent = new Intent(ActivitateRapoarte.this, ActivitateDespre.class);
            startActivity(intent);
        }

        return true;
    }
}
