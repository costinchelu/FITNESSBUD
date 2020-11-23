package proiect.costin.ro.fitnesshealthandfood;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*
 *
 * Activitate pentru afisarea unei locatii predefinite intr-un fragment GoogleMaps.
 *
 * Accesul la aceasta activitate se face prin navigare din meniul principal. Afisarea este
 * predefinita pe pozitie, directie, unghi, nivel de zoom
 *
 * Activitatea este accesibila prin meniul navigational din MainActivity
 *
 * */

public class ActivitateMap extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // marker pentru locatie - setat pe locatia CSIE
        LatLng CSIE = new LatLng(44.447828, 26.099265);
        googleMap.addMarker(new MarkerOptions().position(CSIE).title("Facultatea de Cibernetica, Statistica si Informatica Economica"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CSIE, 18));

        // o particularizare a afisarii folosind un nivel de zoom, bearing si tilt
        CameraPosition pozitie = new CameraPosition.Builder()
                .target(CSIE)
                .zoom(18)
                .bearing(-90)
                .tilt(60)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pozitie));
    }
}
