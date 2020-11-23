package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

/*
*
* Clasa pentru afisarea si stocarea informatiilor despre utilizator.
* view-ul user_et_calorii este cel mai important. El stocheaza in SharedPreferences informatii
* despre necesarul caloric zilnic. Aceste informatii vor fi folosite in raportul 2 pentru stabilirea
* bilantului caloric. Daca acest camp nu este completat, bilantul se va calcula dupa o valoare
* standard de 2200 kcal/zi.
*
* prin intermediul butonului de loadImage se pot accesa resursele locale ale
* telefonului pentru incarcarea unei imagini (care va servi drept avatar pentru utilizator).
* Activitatea are si un buton pentru intoarcerea in meniul principal.
*
* Desi am salvat Uri-ul (sub forma String) in Shared preferences, in momentul cand aplicatia incearca
* sa recupereze imaginea din Storage prin intermediul Uri-ului primesc exceptie pentru lipsa
* permisiunii de acces a galeriei (am trecut android.permission.ACCESS_MEDIA_LOCATION in manifest)
*
* */

public class ActivitateUtilizator extends AppCompatActivity {

    private static final Integer RESULT_LOAD_IMG = 300;
    public final static String SP_NUME = "user_nume";
    public final static String SP_VARSTA = "user_varsta";
    public final static String SP_INALTIME = "user_inaltime";
    public final static String SP_GREUTATE = "user_greutate";
    public final static String SP_NECESAR = "user_necesar";
    public final static String SP_SEX = "user_sex";
//    public final static String SP_IMAGE = "user_image";

    private SharedPreferences sharedPref;

    private ImageView userImage;
    private EditText etNume;
    private EditText etVarsta;
    private EditText etInaltime;
    private EditText etGreutate;
    private EditText etNecesar;
    private EditText etSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_utilizator);

        initComponents();
        loadSharedPref();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPref();
    }

    private void initComponents() {
        ImageButton ibMain = findViewById(R.id.user_ib);
        userImage = findViewById(R.id.user_iv);
        Button loadImage = findViewById(R.id.user_btn);
        etVarsta = findViewById(R.id.user_et_varsta);
        etNume = findViewById(R.id.user_et_nume);
        etInaltime = findViewById(R.id.user_et_inaltime);
        etGreutate = findViewById(R.id.user_et_greutate);
        etNecesar = findViewById(R.id.user_et_calorii);
        etSex = findViewById(R.id.user_et_sex);

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        ibMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
// TODO
    private void loadSharedPref() {
        sharedPref = getSharedPreferences(ActivitateDespre.SHARED_P, Context.MODE_PRIVATE);
        etNume.setText(sharedPref.getString(SP_NUME, ""));
        etVarsta.setText(sharedPref.getString(SP_VARSTA, ""));
        etInaltime.setText(sharedPref.getString(SP_INALTIME, ""));
        etGreutate.setText(sharedPref.getString(SP_GREUTATE, ""));
        etNecesar.setText(sharedPref.getString(SP_NECESAR, ""));
        etSex.setText(sharedPref.getString(SP_SEX, ""));
//        if(sharedPref.contains(SP_IMAGE)){
//            try {
//                Uri imageUri = Uri.parse(sharedPref.getString(SP_IMAGE, ""));
//                try {
//                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                    userImage.setImageBitmap(selectedImage);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            getString(R.string.eroare_incarcarea_imaginii), Toast.LENGTH_LONG).show();
//                }
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void saveSharedPref() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SP_NUME, etNume.getText().toString());
        editor.putString(SP_VARSTA, etVarsta.getText().toString());
        editor.putString(SP_INALTIME, etInaltime.getText().toString());
        editor.putString(SP_GREUTATE, etGreutate.getText().toString());
        editor.putString(SP_NECESAR, etNecesar.getText().toString());
        editor.putString(SP_SEX, etSex.getText().toString());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                userImage.setImageBitmap(selectedImage);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString(SP_IMAGE, imageUri.toString());
//                editor.apply();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        getString(R.string.eroare_incarcarea_imaginii), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    getString( R.string.eroare_alegere_imagine),Toast.LENGTH_LONG).show();
        }
    }
}
