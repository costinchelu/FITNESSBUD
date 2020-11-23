package proiect.costin.ro.fitnesshealthandfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

/*
*
* Activitatea poate fi accesata prin intermediul meniului. Contine un rating bar care stocheaza
* rating-ul intr-un Shared Preference
*
* */

public class ActivitateDespre extends AppCompatActivity {

    private final static String SHARED_P_FEEDBACK = "ratingBarFeedback";
    public static final String SHARED_P = "MyPrefs";

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_despre);

        initComponents();
    }

    private void initComponents() {
        ImageButton ibMain = findViewById(R.id.despre_ib);
        RatingBar ratingBar = findViewById(R.id.despre_rb);

        sharedPref = getSharedPreferences(SHARED_P, Context.MODE_PRIVATE);
        float ratingValue = sharedPref.getFloat(SHARED_P_FEEDBACK, -1);

        if(ratingValue != -1) {
            ratingBar.setRating(ratingValue);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat(SHARED_P_FEEDBACK, rating);
                editor.apply();
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
}
