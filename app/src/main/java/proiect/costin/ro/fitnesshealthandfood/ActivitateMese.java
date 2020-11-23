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
 * Activitate care contine o lista a intrarilor masa.
 * Lista este populata de un adaptor personalizat.
 * Pentru introducerea unei noi intrari se apasa pe floatingActionButton-ul din dreapta jos.
 * Pentru editarea sau stergerea unei intrari se foloseste click pe respectiva intrare. Toate
 * operatiile se fac in baza de date. Transferul informatiilor se face prin schimb cu ajutorul
 * startActivityForResult. Activitatea contine si un meniu de navigatie.
 * Intrarile vor fi afisate in lista ordonat in functie de data intrarii
 *
 * */

public class ActivitateMese extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_MASA = 100;
    public static final int REQUEST_CODE_EDIT_MASA = 101;
    public final static String KEY_TO_EDIT_MASA = "keyMasaToBeEdited";

    public final static String OPKEY_INSERT_MASA = "opkeyInsertMasa";
    public final static String OPKEY_UPDATE_MASA = "opkeyUpdateMasa";
    public final static String OPKEY_DELETE_MASA = "opkeyDeleteMasa";
    
    LayoutInflater layoutInflater;
    private ListView lvMese;
    private List<Masa> listaMese = new ArrayList<>();

    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mese);

        DatabaseController controller = DatabaseController.getInstance(getApplicationContext());
        dataRepository = new DataRepository(controller);

        dataRepository.open();
        listaMese = dataRepository.findAllMese();
        dataRepository.close();

        initComponents();
    }

    private void initComponents() {
        FloatingActionButton fabAddMasa = findViewById(R.id.fab_add_masa);
        lvMese = findViewById(R.id.lv_mese);

        fabAddMasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (ActivitateMese.this, ActivitateMeseAdd.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_MASA);
            }
        });

        lvMese.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent
                        (ActivitateMese.this, ActivitateMeseEdit.class);
                intent.putExtra(KEY_TO_EDIT_MASA, listaMese.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_MASA);
            }
        });

        layoutInflater = getLayoutInflater();
        refreshList(listaMese);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_MASA
                && resultCode == RESULT_OK && data != null) {

            Masa masa = data.getParcelableExtra(ActivitateMeseAdd.KEY_ADD_MASA);
            dbOps(OPKEY_INSERT_MASA, masa);
        }
        else if(requestCode == REQUEST_CODE_EDIT_MASA
                && resultCode == RESULT_OK && data != null) {

            Masa masa = data.getParcelableExtra(ActivitateMeseEdit.KEY_EDIT_MASA);
            dbOps(OPKEY_UPDATE_MASA, masa);
        }
        else if(requestCode == REQUEST_CODE_EDIT_MASA
                && resultCode == RESULT_FIRST_USER && data != null) {

            Masa masa = data.getParcelableExtra(ActivitateMeseEdit.KEY_DELETE_MASA);
            dbOps(OPKEY_DELETE_MASA, masa);
        }
    }

    public void refreshList(List<Masa> listaMese) {
        AdapterMasa customAdapter = new AdapterMasa
                (ActivitateMese.this,
                        R.layout.lv_masa,
                        listaMese,
                        layoutInflater);
        lvMese.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    public void dbOps(String opKey, Masa masa) {
        if (masa != null) {
            listaMese.clear();
            dataRepository.open();
            switch (opKey) {
                case OPKEY_INSERT_MASA:
                    dataRepository.insertMasa(masa);
                    break;
                case OPKEY_UPDATE_MASA:
                    dataRepository.updateMasa(masa);
                    break;
                case OPKEY_DELETE_MASA:
                    dataRepository.deleteMasa(masa.getIdMasa());
                    break;
            }
            listaMese = dataRepository.findAllMese();
            dataRepository.close();
            refreshList(listaMese);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_add_masa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_masa_menu_principal: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateMese.this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_masa_menu_lista_fitness: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateMese.this, ActivitateFitness.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_masa_menu_rapoarte: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateMese.this, ActivitateRapoarte.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_masa_menu_utilizator: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateMese.this, ActivitateUtilizator.class);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.add_masa_menu_despre: {
                if(item.isEnabled()) {
                    Intent intent = new Intent(ActivitateMese.this, ActivitateDespre.class);
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
