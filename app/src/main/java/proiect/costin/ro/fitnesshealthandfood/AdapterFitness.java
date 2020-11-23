package proiect.costin.ro.fitnesshealthandfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 *
 * Adapter pentru afisarea informatiilor in lista de activitati fizice
 *
 * */

public class AdapterFitness extends ArrayAdapter<Fitness> {

    private Context context;
    private int resource;
    private List<Fitness> fitnessList;
    private LayoutInflater layoutInflater;

    public AdapterFitness(@NonNull Context context, int resource,
                          @NonNull List<Fitness> fitnessList, LayoutInflater layoutInflater) {
        super(context, resource, fitnessList);
        this.context = context;
        this.resource = resource;
        this.fitnessList = fitnessList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Fitness activitateFitness = fitnessList.get(position);
        if(activitateFitness != null) {
            addDenumire(view, activitateFitness.getDenumireSport());
            addCalorii(view, activitateFitness.getNrCalorii());
            addTimp(view, activitateFitness.getNrMinute());
            addData(view, activitateFitness.getDataActivitatii());
        }
        return view;
    }

    private void addData(View view, Date dataActivitatii) {
        TextView textView = view.findViewById(R.id.lv_fitness_data);
        if(dataActivitatii != null) {
            textView.setText(new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US).
                    format(dataActivitatii));
        } else {
          textView.setText(R.string.adapter_no_content);
        }
    }

    // pentru metoda setText din adaptor nu am putut folosi valori din strings.xml asociate
    // cu valori proprii. In mod normal foloseam String s = String.format(getString(...)), dar in
    // acest context nu functioneaza
    private void addTimp(View view, Integer nrMinute) {
        TextView textView = view.findViewById(R.id.lv_fitness_timp);
        if(nrMinute != null) {
            textView.setText("Minute activitate: " + String.valueOf(nrMinute));

        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addCalorii(View view, Integer nrCalorii) {
        TextView textView = view.findViewById(R.id.lv_fitness_calorii);
        if (nrCalorii != null) {
            textView.setText("Calorii consumate: " + String.valueOf(nrCalorii));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addDenumire(View view, String denumireSport) {
        TextView textView = view.findViewById(R.id.lv_fitness_nume);
        if(denumireSport != null) {
            textView.setText(denumireSport);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }
}
