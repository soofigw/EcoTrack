package com.example.ecotrack;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroHogarActivity extends AppCompatActivity {

    //vistas
    private Button btnReciclaje;
    private Button btnElectricidad;
    private Button btnPlastico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //conexion del layout XML
        setContentView(R.layout.activity_registro_hogar);

        //conexion vistas con findViewById
        btnReciclaje = findViewById(R.id.btn_registrar_reciclaje);
        btnElectricidad = findViewById(R.id.btn_registrar_electricidad);
        btnPlastico = findViewById(R.id.btn_registrar_plastico);

        //logica para los 3 botones
    }
}