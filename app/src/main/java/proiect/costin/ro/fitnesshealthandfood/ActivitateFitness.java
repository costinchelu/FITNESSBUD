package proiect.costin.ro.fitnesshealthandfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * Activitate care contine o lista a intrarilor fitness.
 * Lista este populata de un adaptor personalizat.
 * Pentru introducerea unei noi intrari se apasa pe floatingActionButton-ul din dreapta jos.
 * Pentru editarea sau stergerea unei intrari se foloseste click pe respectiva intrare. Toate
 * operatiile se fac in baza de date. Transferul informatiilor se face prin schimb cu ajutorul
 * startActivityForResult. Activitatea contine si un meniu de navigatie.
 * Intrarile vor fi afisate in lista ordonat in functie de data intrarii
 *
 * */

public class ActivitateFitness extends AppCompatActivity {

    public final static  int REQUEST_CODE_ADD_FITNESS = 200;
    public final static int REQUEST_CODE_EDIT_FITNESS = 201;
    public final static String KEY_TO_EDIT_FITNESS = "keyFitnessToBeEdited";

    public final static String OPKEY_INSERT_FITNESS = "opkeyInsertFitness";
    public final static String OPKEY_UPDATE_FITNESS = "opkeyUpdateFitness";
    public final static String OPKEY_DELETE_FITNESS = "opkeyDeleteFitness";

    LayoutInflater layoutInflater;
    private ListView lvFitness;
    private List<Fitness> listaFitness = new ArrayList<>();

    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fitness);

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        dataRepository = new DataRepository(controller);
        dataRepository.open();
        listaFitness = dataRepository.findAllFitness();
        dataRepository.close();

        initComponents();
    }

    private void initComponents() {
        FloatingActionButton fabAddFitness = findViewById(R.id.fab_add_fitness);
        lvFitness = findViewById(R.id.lv_fitness);

        fabAddFitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (ActivitateFitness.this, ActivitateFitnessAdd.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_FITNESS);
            }
        });

        lvFitness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent
                        (ActivitateFitness.this, ActivitateFitnessEdit.class);
                intent.putExtra(KEY_TO_EDIT_FITNESS, listaFitness.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_FITNESS);
            }
        });

        layoutInflater = getLayoutInflater();
        refreshList(listaFitness);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_FITNESS
                && resultCode == RESULT_OK && data != null) {

            Fitness fitness = data.getParcelableExtra(ActivitateFitnessAdd.KEY_ADD_FITNESS);
            dbOps(OPKEY_INSERT_FITNESS, fitness);
        }
        else if(requestCode == REQUEST_CODE_EDIT_FITNESS
                && resultCode == RESULT_OK && data != null) {

            Fitness fitness = data.getParcelableExtra(ActivitateFitnessEdit.KEY_EDIT_FITNESS);
            dbOps(OPKEY_UPDATE_FITNESS, fitness);
        }
        else if(requestCode == REQUEST_CODE_EDIT_FITNESS
                && resultCode == RESULT_FIRST_USER && data != null) {

            Fitness fitness = data.getParcelableExtra(ActivitateFitnessEdit.KEY_DELETE_FITNESS);
            dbOps(OPKEY_DELETE_FITNESS, fitness);
        }
    }

    public void refreshList(List<Fitness> listaFitness) {
        AdapterFitness customAdapter = new AdapterFitness
                (ActivitateFitness.this,
                        R.layout.lv_fitness,
                        listaFitness,
                        layoutInflater);
        lvFitness.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    public  void dbOps(String opKey, Fitness fitness) {
        if(fitness != null) {
            listaFitness.clear();
            dataRepository.open();
            switch (opKey) {
                case OPKEY_INSERT_FITNESS:
                    dataRepository.insertFitness(fitness);
                    break;
                case OPKEY_UPDATE_FITNESS:
                    dataRepository.updateFitness(fitness);
                    break;
                case OPKEY_DELETE_FITNESS:
                    dataRepository.deleteFitness(fitness.getIdFitness());
                    break;
            }
            listaFitness = dataRepository.findAllFitness();
            dataRepository.close();
            refreshList(listaFitness);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_add_fitness_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_fitness_menu_principal: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateFitness.this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_fitness_menu_lista_mese: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateFitness.this, ActivitateMese.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_fitness_menu_rapoarte: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateFitness.this, ActivitateRapoarte.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_fitness_menu_utilizator: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateFitness.this, ActivitateUtilizator.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_fitness_menu_despre: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateFitness.this, ActivitateDespre.class);
                    startActivity(intent);
                }
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
