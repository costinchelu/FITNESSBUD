package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
 * Activitate accesibila prin click pe listView-ul de fitness. La pornirea activitatii,
 * se vor incarca campurile corespunzatoare cu datele acelei intrari.
 * Fata de activitatea ActivitateFitnessAdd, avem si un buton de stergere a intrarii.
 * Butonul de stergere porneste un mesaj de confirmare a stergerii. Inainte de salvare (sau stergere)
 * este necesara validarea datelor similar cu actiunea de inserare din ActivitateFitnessAdd
 *
 * */

public class ActivitateFitnessEdit extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_EDIT_FITNESS = "keyEditFitness";
    public final static String KEY_DELETE_FITNESS = "keyDeleteFitness";

    private Intent intent;

    private EditText etDenumireFitness;
    private EditText etMinuteFitness;
    private EditText etCaloriiFitness;
    private EditText etDataFitness;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_fitness_edit);

        initComponents();
        intent = getIntent();

        if(intent.hasExtra(ActivitateFitness.KEY_TO_EDIT_FITNESS)) {
            Fitness fitness = intent.getParcelableExtra(ActivitateFitness.KEY_TO_EDIT_FITNESS);
            if(fitness != null) {
                updateUI(fitness);
                id = fitness.getIdFitness();
            }
        }
    }

    private void initComponents() {
        etDenumireFitness = findViewById(R.id.et_fitness_edit_denumire);
        etMinuteFitness = findViewById(R.id.et_fitness_edit_timp);
        etCaloriiFitness = findViewById(R.id.et_fitness_edit_calorii);
        etDataFitness = findViewById(R.id.et_fitness_edit_data);
        Button btnSalveaza = findViewById(R.id.btn_fitness_edit_save);
        Button btnAnuleaza = findViewById(R.id.btn_fitness_edit_cancel);
        Button btnSterge = findViewById(R.id.btn_fitness_edit_sterge);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Fitness fitness = creareObiectFitness();
                    intent.putExtra(KEY_EDIT_FITNESS, fitness);
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

        btnSterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog optiuneStergere = dialogStergere();
                optiuneStergere.show();
            }
        });
    }

    private AlertDialog dialogStergere()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_sterge))
                .setMessage(getString(R.string.alerta_stergere_dialog))

                .setPositiveButton
                        (getString(R.string.btn_sterge), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(validareInput()) {
                            Fitness fitness = creareObiectFitness();
                            intent.putExtra(KEY_DELETE_FITNESS,fitness);
                            setResult(RESULT_FIRST_USER, intent);
                            finish();
                        }
                    }
                })

                .setNegativeButton
                        (getString(R.string.btn_anuleaza), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();

        return alertDialog;
    }

    private Fitness creareObiectFitness() {
        String denumireActivitate = etDenumireFitness.getText().toString();
        Integer minuteActivitate = Integer.parseInt(etMinuteFitness.getText().toString());
        Integer caloriiActivitate = Integer.parseInt(etCaloriiFitness.getText().toString());
        Date dataActivitate = null;
        try {
            dataActivitate = new SimpleDateFormat
                    (DATE_FORMAT, Locale.US).parse(etDataFitness.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Fitness(denumireActivitate, caloriiActivitate, minuteActivitate, dataActivitate, id);
    }

    private void updateUI(Fitness fitness) {
        if(fitness.getDenumireSport() != null) {
            etDenumireFitness.setText(fitness.getDenumireSport());
        }
        if(fitness.getDataActivitatii() != null) {
            etDataFitness.setText
                    (new SimpleDateFormat(DATE_FORMAT, Locale.US).format(fitness.getDataActivitatii()));
        }
        if(fitness.getNrCalorii() != null) {
            etCaloriiFitness.setText(String.valueOf(fitness.getNrCalorii()));
        }
        if(fitness.getNrMinute() != null) {
            etMinuteFitness.setText(String.valueOf(fitness.getNrMinute()));
        }
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
