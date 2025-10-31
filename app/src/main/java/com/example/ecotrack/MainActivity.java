package com.example.ecotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;

public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;
    private Button btnRegistrarViaje;
    private Button btnRegistrarHogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = findViewById(R.id.pie_chart);
        btnRegistrarViaje = findViewById(R.id.btn_registrar_viaje);
        btnRegistrarHogar = findViewById(R.id.btn_registrar_hogar);

        //Logica

        //listener para el boton de Viaje
        btnRegistrarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicia actividad RegistroViajeActivity
                Intent intent = new Intent(MainActivity.this, RegistroViajeActivity.class);
                startActivity(intent);
            }
        });

        //listener para el boton de Hogar
        btnRegistrarHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inicia actividad RegistroHogarActivity
                Intent intent = new Intent(MainActivity.this, RegistroHogarActivity.class);
                startActivity(intent);
            }
        });

        //codigo para configurar la grafica)
    }
}