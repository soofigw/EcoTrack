package com.example.ecotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView; // ¡Importante!
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;

public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;
    private Button btnRegistrarViaje;
    private Button btnRegistrarHogar;

    //textviews para los desafios
    private TextView textDesafio1;
    private TextView textDesafio2;
    private DesafioManager desafioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //conexion de vistas
        pieChart = findViewById(R.id.pie_chart);
        btnRegistrarViaje = findViewById(R.id.btn_registrar_viaje);
        btnRegistrarHogar = findViewById(R.id.btn_registrar_hogar);

        textDesafio1 = findViewById(R.id.text_desafio_1);
        textDesafio2 = findViewById(R.id.text_desafio_2);

        desafioManager = new DesafioManager();


        //botones config
        btnRegistrarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroViajeActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrarHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroHogarActivity.class);
                startActivity(intent);
            }
        });

        //desafio semanal aqui
        cargarDesafios();

        //TODO: code para la grafica
    }

    //llama al desafiomanager para tener 2 desafios random
    private void cargarDesafios() {
        String[] desafios = desafioManager.getDosDesafiosRandom();

        textDesafio1.setText(desafios[0]);
        textDesafio2.setText(desafios[1]);
    }
}