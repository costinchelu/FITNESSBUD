package proiect.costin.ro.fitnesshealthandfood;

import android.content.Context;
import android.content.res.Resources;
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
 * Adapter pentru afisarea informatiilor in lista de mese
 *
 * */

public class AdapterMasa extends ArrayAdapter<Masa> {

    private Context context;
    private int resource;
    private List<Masa> mese;
    private LayoutInflater layoutInflater;

    public AdapterMasa(@NonNull Context context, int resource,
                       @NonNull List<Masa> mese, LayoutInflater layoutInflater) {
//        super(context, resource, mese);
        super(context, resource, mese);
        this.context = context;
        this.resource = resource;
        this.mese = mese;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Masa masa = mese.get(position);
        if(masa != null) {
            addNume(view, masa.getNumeMancare());
            addPerioada(view, masa.getPerioadaMesei());
            addCalorii(view, masa.getNrCalorii());
            addPortii(view, masa.getNrPortii());
            addData(view, masa.getDataMesei());
        }
        return view;
    }

    private void addData(View view, Date dataMesei) {
        TextView textView = view.findViewById(R.id.lv_masa_data);
        if(dataMesei != null) {
            textView.setText(new SimpleDateFormat(ActivitateMeseAdd.DATE_FORMAT, Locale.US)
            .format(dataMesei));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    // pentru metoda setText din adaptor nu am putut folosi valori din strings.xml asociate
    // cu valori proprii
    private void addPortii(View view, Float nrPortii) {
        TextView textView = view.findViewById(R.id.lv_masa_portii);
        if(nrPortii != null) {
            textView.setText("Nr. portii = " + String.valueOf(nrPortii));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addCalorii(View view, Integer nrCalorii) {
        TextView textView = view.findViewById(R.id.lv_masa_calorii);
        if(nrCalorii != null) {
            textView.setText("Nr. calorii = " + String.valueOf(nrCalorii));
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addPerioada(View view, String perioadaMesei) {
        TextView textView = view.findViewById(R.id.lv_masa_perioada);
        if(perioadaMesei != null && !perioadaMesei.trim().isEmpty()) {
            textView.setText(perioadaMesei);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addNume(View view, String numeMancare) {
        TextView textView = view.findViewById(R.id.lv_masa_nume);
        if(numeMancare != null && !numeMancare.trim().isEmpty()) {
            textView.setText(numeMancare);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }
}
