package com.example.ecotrack; // (Asegúrate que sea tu nombre de paquete)

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroViajeActivity extends AppCompatActivity {

    // 1. Declarar las vistas
    private EditText editDistancia;
    private Spinner spinnerVehiculo;
    private EditText editPasajeros;
    private Button btnRegistrar;
    private TextView textResultadoReal;
    private TextView textRecomendacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //conexion layout XML
        setContentView(R.layout.activity_registro_viaje);

        //conexion vistas con findViewById
        editDistancia = findViewById(R.id.edit_distancia);
        spinnerVehiculo = findViewById(R.id.spinner_vehiculo);
        editPasajeros = findViewById(R.id.edit_pasajeros);
        btnRegistrar = findViewById(R.id.btn_registrar);
        textResultadoReal = findViewById(R.id.text_resultado_real);
        textRecomendacion = findViewById(R.id.text_recomendacion);

        //logica para llenar el spinner y para el boton registrar
    }
}