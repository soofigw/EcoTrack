package com.example.ecotrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroViajeActivity extends AppCompatActivity {

    private EditText editDistancia;
    private Spinner spinnerVehiculo;
    private TextView textAbrirMaps;
    private Button btnRegistrar;
    private TextView textResultadoReal;
    private TextView textRecomendacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_viaje);

        editDistancia = findViewById(R.id.edit_distancia);
        spinnerVehiculo = findViewById(R.id.spinner_vehiculo);
        textAbrirMaps = findViewById(R.id.text_abrir_maps);
        btnRegistrar = findViewById(R.id.btn_registrar);
        textResultadoReal = findViewById(R.id.text_resultado_real);
        textRecomendacion = findViewById(R.id.text_recomendacion);

        //listener para abrir maps
        textAbrirMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent para abrir Google Maps
                Uri gmmIntentUri = Uri.parse("google.navigation:q=");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(RegistroViajeActivity.this, "Abriendo Google Maps...", Toast.LENGTH_LONG).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com"));
                    startActivity(browserIntent);
                }
            }
        });

        //TODO: Configurar el Spinner (spinnerVehiculo)

        //TODO: Configurar el boton "Registrar Viaje"
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logica
            }
        });
    }
}