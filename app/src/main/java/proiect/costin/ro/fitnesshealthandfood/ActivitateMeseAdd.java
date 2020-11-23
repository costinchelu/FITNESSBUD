package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActivitateMeseAdd extends AppCompatActivity {

    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String KEY_ADD_MASA = "keyAddMasa";
    private Intent intent;

    private EditText etDenumireMasa;
    private RadioGroup rgPerioada;
    private EditText etCalorii;
    private Spinner spnPortii;
    private EditText etDataMesei;

/*
*
* Activitate pentru adaugarea unei intrari masa. Intrarile vor fi validate cu ajutorul metodei
* validare input. Data va fi convertita in String dupa un format predefinit. Aplicatia permite
* intrari noi cu anul curent sau anul precedent.
*
* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_mese_add);

        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        etDenumireMasa = findViewById(R.id.et_mese_add_denumire);
        rgPerioada = findViewById(R.id.rg_mese_add_perioada);
        etCalorii = findViewById(R.id.et_mese_add_calorii);
        spnPortii = findViewById(R.id.spn_mese_add_nr_portii);
        etDataMesei = findViewById(R.id.et_mese_add_data);
        Button btnSalveaza = findViewById(R.id.btn_mese_add_save);
        Button btnAnuleaza = findViewById(R.id.btn_mese_add_cancel);

        ArrayAdapter<CharSequence> spnPortiiAdapter =
                ArrayAdapter.createFromResource(
                        ActivitateMeseAdd.this,
                        R.array.spn_mese_add_nr_portii,
                        R.layout.support_simple_spinner_dropdown_item);
        spnPortii.setAdapter(spnPortiiAdapter);

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validareInput()) {
                    Masa masa = creareObiectMasa();
                    intent.putExtra(KEY_ADD_MASA, masa);
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

        return new Masa(numeMancare, perioadaMesei, nrCalorii, nrPortii, dataMesei);
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
