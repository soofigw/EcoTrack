package com.example.ecotrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class RegistroHogarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnReciclarOrganico;
    private Button btnReciclarInorganico;
    private Button btnReducirLuz;
    private Button btnDesconectar;
    private Button btnEvitarBotellas;
    private Button btnEvitarBolsas;
    private DbManager dbManager;
    private CalculadoraCO2 calculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_hogar);


        dbManager = new DbManager(this);
        calculadora = new CalculadoraCO2();

        //conexion de las vistas con los botones
        btnReciclarOrganico = findViewById(R.id.btn_reciclar_organico);
        btnReciclarInorganico = findViewById(R.id.btn_reciclar_inorganico);
        btnReducirLuz = findViewById(R.id.btn_reducir_luz);
        btnDesconectar = findViewById(R.id.btn_reducir_desconectar);
        btnEvitarBotellas = findViewById(R.id.btn_evitar_botellas);
        btnEvitarBolsas = findViewById(R.id.btn_evitar_bolsas);

        //listeners para los botones
        btnReciclarOrganico.setOnClickListener(this);
        btnReciclarInorganico.setOnClickListener(this);
        btnReducirLuz.setOnClickListener(this);
        btnDesconectar.setOnClickListener(this);
        btnEvitarBotellas.setOnClickListener(this);
        btnEvitarBolsas.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String habitoNombre = "";
        Button botonPresionado = (Button) v;

        if (v.getId() == R.id.btn_reciclar_organico) {
            habitoNombre = "Reciclar Orgánico";
        } else if (v.getId() == R.id.btn_reciclar_inorganico) {
            habitoNombre = "Separar Inorgánicos";
        } else if (v.getId() == R.id.btn_reducir_luz) {
            habitoNombre = "Reducir Consumo (Luz)";
        } else if (v.getId() == R.id.btn_reducir_desconectar) {
            habitoNombre = "Desconectar Aparatos";
        } else if (v.getId() == R.id.btn_evitar_botellas) {
            habitoNombre = "Evitar Botellas (Termo)";
        } else if (v.getId() == R.id.btn_evitar_bolsas) {
            habitoNombre = "Evitar Bolsas (Tela)";
        }


        double co2Ahorrado = calculadora.getAhorroHabito(habitoNombre);

        dbManager.insertarHabito(habitoNombre, co2Ahorrado);

        String mensaje = String.format("¡Éxito! Ahorraste %.2f kg CO2", co2Ahorrado);
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        Animation popAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_pop);
        botonPresionado.startAnimation(popAnimation);

        botonPresionado.setEnabled(false);
        botonPresionado.setText(habitoNombre + " (¡Registrado!)");
    }
}