package com.example.ecotrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroViajeActivity extends AppCompatActivity {

    //vistas
    private EditText editDistancia;
    private Spinner spinnerVehiculo;
    private TextView textAbrirMaps;
    private Button btnRegistrar;
    private TextView textResultadoReal;
    private TextView textRecomendacion;
    private DbManager dbManager;
    private CalculadoraCO2 calculadora;

    //datos spinner
    private final String[] opcionesVehiculo = {
            "Selecciona un vehículo...                                     ▾",
            "Auto",
            "Transporte Público",
            "Moto",
            "Bicicleta",
            "Caminar"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_viaje);


        dbManager = new DbManager(this);
        calculadora = new CalculadoraCO2();

        editDistancia = findViewById(R.id.edit_distancia);
        spinnerVehiculo = findViewById(R.id.spinner_vehiculo);
        textAbrirMaps = findViewById(R.id.text_abrir_maps);
        btnRegistrar = findViewById(R.id.btn_registrar);
        textResultadoReal = findViewById(R.id.text_resultado_real);
        textRecomendacion = findViewById(R.id.text_recomendacion);


        setupMapsButton();
        setupSpinner();
        setupRegistrarButton();
    }

    //listener para el link de googlemaps
    private void setupMapsButton() {
        textAbrirMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    //opciones spinner
    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner_item,
                opcionesVehiculo
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehiculo.setAdapter(adapter);
    }

    private void setupRegistrarButton() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String distanciaStr = editDistancia.getText().toString();
                String vehiculo = spinnerVehiculo.getSelectedItem().toString();

                if (TextUtils.isEmpty(distanciaStr)) {
                    editDistancia.setError("Ingresa una distancia");
                    return;
                }
                if (vehiculo.equals(opcionesVehiculo[0])) { //si no selecciona nada
                    Toast.makeText(RegistroViajeActivity.this, "Por favor, selecciona un vehículo", Toast.LENGTH_SHORT).show();
                    return;
                }

                double distancia = Double.parseDouble(distanciaStr);


                double impactoReal = calculadora.calcularImpactoViaje(vehiculo, distancia);
                String recomendacion = calculadora.getRecomendacionViaje(vehiculo, distancia);

                //resultados
                textResultadoReal.setText(String.format("Tu impacto: %.2f kg CO2", impactoReal));
                textRecomendacion.setText(recomendacion);

                dbManager.insertarViaje(vehiculo, distancia, impactoReal);

                //mensaje d exito
                Toast.makeText(RegistroViajeActivity.this, "¡Viaje registrado con éxito!", Toast.LENGTH_SHORT).show();


                // finish();
            }
        });
    }
}