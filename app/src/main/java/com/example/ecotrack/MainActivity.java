package com.example.ecotrack;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

//imports de la grafica
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //vistas
    private PieChart pieChart;
    private Button btnRegistrarViaje;
    private Button btnRegistrarHogar;
    private TextView textDesafio1;
    private TextView textDesafio2;

    //----------
    private DesafioManager desafioManager;
    private DbManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ini----
        desafioManager = new DesafioManager();
        dbManager = new DbManager(this); // ¡Inicializamos el DbManager!

        //conexion de las vistas con los botones
        pieChart = findViewById(R.id.pie_chart);
        btnRegistrarViaje = findViewById(R.id.btn_registrar_viaje);
        btnRegistrarHogar = findViewById(R.id.btn_registrar_hogar);
        textDesafio1 = findViewById(R.id.text_desafio_1);
        textDesafio2 = findViewById(R.id.text_desafio_2);

        //config
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


        cargarDesafios();
        setupPieChart();
    }


    //se llama al metodo cada q el usuario regresa para actualizar la grafica
    @Override
    protected void onResume() {
        super.onResume();
        //actualiza la grafi aqui
        cargarDatosDeLaGrafica();
    }

    private void cargarDesafios() {
        String[] desafios = desafioManager.getDosDesafiosRandom();
        textDesafio1.setText(desafios[0]);
        textDesafio2.setText(desafios[1]);
    }

    //estilo del piechart
    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(63f);
        pieChart.setEntryLabelTextSize(13f);
        pieChart.setEntryLabelColor(Color.BLACK);
    }




    //llama al dbmanager y obtiene los datos para crear la grafica
    private void cargarDatosDeLaGrafica() {
        Map<String, Double> datos = dbManager.getProgresoSemanal();
        double totalViajes = datos.get("viajes");
        double totalHogar = datos.get("hogar");

        //entradas para la grafi
        ArrayList<PieEntry> entries = new ArrayList<>();

        if (totalViajes > 0) {
            entries.add(new PieEntry((float) totalViajes, "Impacto (Viajes)"));
        }
        if (totalHogar > 0) {
            entries.add(new PieEntry((float) totalHogar, "Ahorro (Hogar)"));
        }

        //si aun no hay datos muestra unmensaje
        if (entries.isEmpty()) {
            pieChart.setCenterText("Aún no hay datos. ¡Registra una acción!");
            pieChart.setCenterTextSize(16f);
            pieChart.setData(null); //borra datos antiguos
            pieChart.invalidate(); //refresh
            return;
        }

        //dataset
        PieDataSet dataSet = new PieDataSet(entries, "Resumen Semanal C02");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getColor(R.color.lila_acento));
        colors.add(getColor(R.color.azul_turquesa));
        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        data.setValueFormatter(new PercentFormatter(pieChart));


        //carga los datos
        pieChart.setCenterText("");
        pieChart.setData(data);
        pieChart.invalidate();
    }
}