package com.example.ecotrack;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroHogarActivity extends AppCompatActivity {

    //botones
    private Button btnReciclarOrganico;
    private Button btnReciclarInorganico;
    private Button btnReducirLuz;
    private Button btnDesconectar;
    private Button btnEvitarBotellas;
    private Button btnEvitarBolsas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layout -> xml
        setContentView(R.layout.activity_registro_hogar);

        btnReciclarOrganico = findViewById(R.id.btn_reciclar_organico);
        btnReciclarInorganico = findViewById(R.id.btn_reciclar_inorganico);
        btnReducirLuz = findViewById(R.id.btn_reducir_luz);
        btnDesconectar = findViewById(R.id.btn_reducir_desconectar);
        btnEvitarBotellas = findViewById(R.id.btn_evitar_botellas);
        btnEvitarBolsas = findViewById(R.id.btn_evitar_bolsas);

        //TODO: logica de los botones
        //TODO: deshabilitarlos despues de un clic y mostrar un toast)
    }
}