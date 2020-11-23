package proiect.costin.ro.fitnesshealthandfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import java.util.List;

/*
*
* Adapter pentru afisarea informatiilor din JSON in listView
*
* */

public class JsonAdapter extends ArrayAdapter<JsonItem> {

    private Context context;
    private int resource;
    private List<JsonItem> jsonItems;
    private LayoutInflater layoutInflater;

    public JsonAdapter(@NonNull Context context,
                         int resource,
                         List<JsonItem> jsonItems,
                         LayoutInflater layoutInflater) {
        super(context, resource, jsonItems);
        this.context = context;
        this.resource = resource;
        this.jsonItems = jsonItems;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        JsonItem jsonItem = jsonItems.get(position);
        if(jsonItem != null) {
            JsonCompozitie compozitie = jsonItem.getCompozitie();
            String carbohidrati = compozitie.getCarbohidrati();
            String grasimi = compozitie.getGrasimi();
            String proteine = compozitie.getProteine();

            addCarbohidrati(view, carbohidrati);
            addGrasimi(view, grasimi);
            addProteine(view, proteine);
            addImagine(view, jsonItem.getImagine());
            addNume(view, jsonItem.getNume());
            addPortie(view, jsonItem.getPortie());
            addCalorii(view, jsonItem.getCalorii());
        }
        return view;
    }

    // pentru metoda setText din adaptor nu am putut folosi valori din strings.xml asociate
    // cu valori proprii (nici cu String.format() )
    private void addCalorii(View view, String calorii) {
        TextView textView = view.findViewById(R.id.lv_json_tv_calorii);
        if (calorii != null && !calorii.trim().isEmpty()) {
            textView.setText("Calorii per portie: " + calorii);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addPortie(View view, String portie) {
        TextView textView = view.findViewById(R.id.lv_json_tv_portie);
        if (portie != null && !portie.trim().isEmpty()) {
            textView.setText("Portie: " + portie);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addNume(View view, String nume) {
        TextView textView = view.findViewById(R.id.lv_json_tv_nume);
        if (nume != null && !nume.trim().isEmpty()) {
            textView.setText(nume);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addImagine(View view, String imagine) {
        ImageView imageView = view.findViewById(R.id.lv_json_image);
        if(imagine != null) {
            Picasso.get().load(imagine).into(imageView);
        }
    }

    private void addProteine(View view, String proteine) {
        TextView textView = view.findViewById(R.id.lv_json_tv_proteine);
        if (proteine != null && !proteine.trim().isEmpty()) {
            textView.setText("Proteine: " + proteine);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addGrasimi(View view, String grasimi) {
        TextView textView = view.findViewById(R.id.lv_json_tv_lipide);
        if (grasimi != null && !grasimi.trim().isEmpty()) {
            textView.setText("Grasimi: " + grasimi);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }

    private void addCarbohidrati(View view, String carbohidrati) {
        TextView textView = view.findViewById(R.id.lv_json_tv_carbo);
        if (carbohidrati != null && !carbohidrati.trim().isEmpty()) {
            textView.setText("Carbohidrati: " + carbohidrati);
        } else {
            textView.setText(R.string.adapter_no_content);
        }
    }


}
