package com.example.ecotrack;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistorial;
    private DbManager dbManager;
    private HistorialAdapter adapter;
    private List<HistorialItem> listaDeHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historial);
        dbManager = new DbManager(this);
        recyclerViewHistorial = findViewById(R.id.recycler_view_historial);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        listaDeHistorial = dbManager.getHistorialCompleto();
        adapter = new HistorialAdapter(this, listaDeHistorial);
        recyclerViewHistorial.setAdapter(adapter);
        recyclerViewHistorial.setLayoutManager(new LinearLayoutManager(this));
    }
}