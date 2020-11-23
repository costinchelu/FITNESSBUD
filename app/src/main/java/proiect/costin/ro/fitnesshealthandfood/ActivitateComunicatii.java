package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
*
* Contine un ListView care afiseaza date provenite din comunicatia cu un server JSON printr-un
* thread care ruleaza in background. Pentru afisarea imaginilor care se obtin din
* link-uri (valoare json) am folosit dependenta picasso.
* In onPostExecute (invocata dupa ce s-au efectuat activitatile de download in fundal) sirul
* de caractere primit este procesat in JsonParser, iar obiectele formate sunt afisate in listView
* prin intermediul unui adapter personalizat (JsonAdapter)*
*
* */

public class ActivitateComunicatii extends AppCompatActivity{

    private static final String URL = "https://json.extendsclass.com/bin/04cdc61a2bfd";

    private List<JsonItem> jsonItems = new ArrayList<>();
    //private ArrayAdapter<JsonItem> adaterJson;
    private JsonAdapter customAdapter;

    private static final String TAG = "ActivitateComunicatii";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_comunicatii);

        final ListView listView = findViewById(R.id.lv_comunicatii);
        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                jsonItems = JsonParser.parseJson(s);
                LayoutInflater layoutInflater = getLayoutInflater();
                customAdapter = new JsonAdapter
                        (ActivitateComunicatii.this,
                        R.layout.lv_json, jsonItems,
                        layoutInflater);
                listView.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();
            }
        }.execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_comunicatii_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_comunicatii_principal) {
            Intent intent = new Intent(ActivitateComunicatii.this, MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menu_comunicatii_utilizator) {
            Intent intent = new Intent(ActivitateComunicatii.this, ActivitateUtilizator.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_comunicatii_despre) {
            Intent intent = new Intent(ActivitateComunicatii.this, ActivitateDespre.class);
            startActivity(intent);
        }

        return true;
    }
}
