package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
*
* Activitate accesibila prin click pe listView-ul de mese. La pornirea activitatii,
* se vor incarca campurile corespunzatoare cu datele acelei intrari.
* Fata de activitatea ActivitateMeseAdd, avem si un buton de stergere a intrarii.
* Butonul de stergere porneste un mesaj de confirmare a stergerii. Inainte de salvare (sau stergere)
* este necesara validarea datelor similar cu actiunea de inserare din ActivitateMeseAdd
*
* */

public class ActivitateMeseEdit extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_EDIT_MASA = "keyEditMasa";
    public final static String KEY_DELETE_MASA = "keyDeleteMasa";

    private Intent intent;

    private EditText etDenumireMasa;
    private RadioGroup rgPerioada;
    private EditText etCalorii;
    private Spinner spnPortii;
    private EditText etDataMesei;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_mese_edit);

        initComponents();
        intent = getIntent();

        if(intent.hasExtra(ActivitateMese.KEY_TO_EDIT_MASA)) {
            Masa masa = intent.getParcelableExtra(ActivitateMese.KEY_TO_EDIT_MASA);
            if(masa != null) {
                updateUI(masa);
                id = masa.getIdMasa();
            }
        }
    }

    private void initComponents() {
        etDenumireMasa = findViewById(R.id.et_mese_edit_denumire);
        rgPerioada = findViewById(R.id.rg_mese_edit_perioada);
        etCalorii = findViewById(R.id.et_mese_edit_calorii);
        spnPortii = findViewById(R.id.spn_mese_edit_nr_portii);
        etDataMesei = findViewById(R.id.et_mese_edit_data);
        Button btnSalveaza = findViewById(R.id.btn_mese_edit_save);
        Button btnAnuleaza = findViewById(R.id.btn_mese_edit_cancel);
        Button btnSterge = findViewById(R.id.btn_mese_edit_sterge);

        ArrayAdapter<CharSequence> spnPortiiAdapter =
                ArrayAdapter.createFromResource(
                        getApplicationContext(),
                        R.array.spn_mese_add_nr_portii,
                        R.layout.support_simple_spinner_dropdown_item);
        spnPortii.setAdapter(spnPortiiAdapter);


        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Masa masa = creareObiectMasa();
                    intent.putExtra(KEY_EDIT_MASA, masa);
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

    // crearea dialogului pentru confirmarea stergerii
    private AlertDialog dialogStergere()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_sterge))
                .setMessage(getString(R.string.alerta_stergere_dialog))

                .setPositiveButton(getString(R.string.btn_sterge), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(validareInput()) {
                            Masa masa = creareObiectMasa();
                            intent.putExtra(KEY_DELETE_MASA, masa);
                            setResult(RESULT_FIRST_USER, intent);
                            finish();
                        }
                    }
                })

                .setNegativeButton(getString(R.string.btn_anuleaza), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();

        return alertDialog;
    }

    // functie pentru update al widgeturilor cu datele intrarii
    private void updateUI(Masa masa) {
        if(masa.getNumeMancare() != null) {
            etDenumireMasa.setText(masa.getNumeMancare());
        }
        if(masa.getDataMesei() != null) {
            etDataMesei.setText(new SimpleDateFormat(
                    DATE_FORMAT, Locale.US).format(masa.getDataMesei()));
        }
        if(masa.getNrCalorii() != null) {
            etCalorii.setText(String.valueOf(masa.getNrCalorii()));
        }
        if(masa.getPerioadaMesei() != null) {
            addPerioada(masa);
        }
        if(masa.getNrPortii() != null) {
            addNrPortii(masa);
        }
    }

    private void addNrPortii(Masa masa) {
        String[] spnArr = getResources().getStringArray(R.array.spn_values);
        String value = masa.getNrPortii().toString();
        String searchValue = prelucrareNrPortii(value);
        for (int i = 0; i < spnArr.length; i++) {
            if (spnArr[i].equalsIgnoreCase(searchValue)) {
                spnPortii.setSelection(i);
            }
        }
    }

    private String prelucrareNrPortii(String value) {
        if(Character.toString(value.charAt(2)).equals("0")) {
            return value.substring(0, 1);
        }
        else if(Character.toString(value.charAt(2)).equals("5")) {
            return value;
        }
        return "1";
    }

    private void addPerioada(Masa masa) {
        switch (masa.getPerioadaMesei()) {
            case "Mic dejun" : {
                rgPerioada.check(R.id.perioada_edit_micdejun);
                break;
            }
            case "Pranz" : {
                rgPerioada.check(R.id.perioada_edit_pranz);
                break;
            }
            case "Cina" : {
                rgPerioada.check(R.id.perioada_edit_cina);
                break;
            }
            case "Gustare" : {
                rgPerioada.check(R.id.perioada_edit_gustare);
                break;
            }
        }
    }

    private Masa creareObiectMasa() {
        String numeMancare = etDenumireMasa.getText().toString();
        RadioButton optiuneRB = findViewById(rgPerioada.getCheckedRadioButtonId());
        String perioadaMesei = optiuneRB.getText().toString();
        Integer nrCalorii = Integer.parseInt(etCalorii.getText().toString());
        Float nrPortii = Float.parseFloat(spnPortii.getSelectedItem().toString());
        Date dataMesei = null;
        try {
            dataMesei = new SimpleDateFormat
                    (DATE_FORMAT, Locale.US).parse(etDataMesei.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Masa(numeMancare, perioadaMesei, nrCalorii, nrPortii, dataMesei, id);
    }

    private boolean validareInput() {
        int anulCurent = Calendar.getInstance().get(Calendar.YEAR);
        String dataIntrodusa = etDataMesei.getText().toString();
        String caloriiIntroduse = etCalorii.getText().toString();

        if(etDenumireMasa.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.validare_nume_activitate), Toast.LENGTH_LONG).show();
            return false;
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
