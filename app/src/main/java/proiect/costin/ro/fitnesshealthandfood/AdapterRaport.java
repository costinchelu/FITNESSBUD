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
 * Adapter pentru afisarea informatiilor in primul raport
 *
 * */

public class AdapterRaport extends ArrayAdapter<Fitness> {

    private Context context;
    private int resource;
    private List<Fitness> fitnessList;
    private LayoutInflater layoutInflater;

    public AdapterRaport(@NonNull Context context, int resource,
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
            addData(view, activitateFitness.getDataActivitatii());
        }
        return view;
    }

    private void addData(View view, Date dataActivitatii) {
        TextView textView = view.findViewById(R.id.lv_raport_data);
        if(dataActivitatii != null) {
            textView.setText(new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US).
                    format(dataActivitatii));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    // pentru metoda setText din adaptor nu am putut folosi valori din strings.xml asociate
    // cu valori proprii
    private void addCalorii(View view, Integer nrCalorii) {
        TextView textView = view.findViewById(R.id.lv_raport_calorii);
        if (nrCalorii != null) {
            textView.setText("Calorii consumate: " + String.valueOf(nrCalorii));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addDenumire(View view, String denumireSport) {
        TextView textView = view.findViewById(R.id.lv_raport_nume);
        if(denumireSport != null) {
            textView.setText(denumireSport);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }
}
