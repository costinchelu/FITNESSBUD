package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 *
 * Activitate pentru adaugarea unei intrari fitness. Intrarile vor fi validate cu ajutorul metodei
 * validare input. Data va fi convertita in String dupa un format predefinit. Aplicatia permite
 * intrari noi cu anul curent sau anul precedent.
 *
 * */

public class ActivitateFitnessAdd extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_ADD_FITNESS = "keyAddFitness";
    private Intent intent;

    private EditText etDenumireFitness;
    private EditText etMinuteFitness;
    private EditText etCaloriiFitness;
    private EditText etDataFitness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_fitness_add);

        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        etDenumireFitness = findViewById(R.id.et_fitness_add_denumire);
        etMinuteFitness = findViewById(R.id.et_fitness_add_timp);
        etCaloriiFitness = findViewById(R.id.et_fitness_add_calorii);
        etDataFitness = findViewById(R.id.et_fitness_add_data);
        Button btnSalveaza = findViewById(R.id.btn_fitness_add_save);
        Button btnAnuleaza = findViewById(R.id.btn_fitness_add_cancel);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Fitness fitness = creareObiectFitness();
                    intent.putExtra(KEY_ADD_FITNESS, fitness);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnAnuleaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private Fitness creareObiectFitness() {
        String denumireActivitate = etDenumireFitness.getText().toString();
        Integer minuteActivitate = Integer.parseInt(etMinuteFitness.getText().toString());
        Integer caloriiActivitate = Integer.parseInt(etCaloriiFitness.getText().toString());
        Date dataActivitate = null;
        try {
            dataActivitate = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(etDataFitness.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Fitness(denumireActivitate, caloriiActivitate, minuteActivitate, dataActivitate);
    }

    private boolean validareInput() {
        int anulCurent = Calendar.getInstance().get(Calendar.YEAR);
        String dataIntrodusa = etDataFitness.getText().toString();
        String caloriiIntroduse = etCaloriiFitness.getText().toString();

        if(etDenumireFitness.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_nume_activitate), Toast.LENGTH_LONG).show();
        }

        if(caloriiIntroduse.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_numar_calorii), Toast.LENGTH_LONG).show();
            return false;
        }
        else if(Integer.parseInt(caloriiIntroduse) < 1) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_numar_pozitiv_calorii), Toast.LENGTH_LONG).show();
            return false;
        }

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


}
