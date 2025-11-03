package com.example.ecotrack;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DbManager {

    private EcoTrackDbHelper dbHelper;
    private SQLiteDatabase database;

    public DbManager(Context context) {
        dbHelper = new EcoTrackDbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertarViaje(String vehiculo, double distancia, double co2Generado) {
        open();
        ContentValues values = new ContentValues();
        values.put(EcoTrackDbHelper.COL_FECHA_VIAJE, System.currentTimeMillis());
        values.put(EcoTrackDbHelper.COL_VEHICULO, vehiculo);
        values.put(EcoTrackDbHelper.COL_DISTANCIA, distancia);
        values.put(EcoTrackDbHelper.COL_CO2_GENERADO, co2Generado);
        database.insert(EcoTrackDbHelper.TABLA_VIAJES, null, values);
        close();
    }

    public void insertarHabito(String habito, double co2Ahorrado) {
        open();
        ContentValues values = new ContentValues();
        values.put(EcoTrackDbHelper.COL_FECHA_HABITO, System.currentTimeMillis());
        values.put(EcoTrackDbHelper.COL_HABITO, habito);
        values.put(EcoTrackDbHelper.COL_CO2_AHORRADO, co2Ahorrado);
        database.insert(EcoTrackDbHelper.TABLA_HABITOS, null, values);
        close();
    }

    //lee la bd y suma el co2 de los ultimos 7dias paea ponerlos en la grafica
    public Map<String, Double> getProgresoSemanal() {
        open();

        long sieteDiasEnMilis = TimeUnit.DAYS.toMillis(7);
        long timestampHace7Dias = System.currentTimeMillis() - sieteDiasEnMilis;

        String[] selectionArgs = { String.valueOf(timestampHace7Dias) };

        double totalViajes = 0;
        double totalHogar = 0;


        Cursor cursorViajes = database.rawQuery(
                "SELECT SUM(" + EcoTrackDbHelper.COL_CO2_GENERADO + ") FROM " + EcoTrackDbHelper.TABLA_VIAJES +
                        " WHERE " + EcoTrackDbHelper.COL_FECHA_VIAJE + " >= ?",
                selectionArgs
        );
        if (cursorViajes.moveToFirst()) {
            totalViajes = cursorViajes.getDouble(0);
        }
        cursorViajes.close();

        Cursor cursorHabitos = database.rawQuery(
                "SELECT SUM(" + EcoTrackDbHelper.COL_CO2_AHORRADO + ") FROM " + EcoTrackDbHelper.TABLA_HABITOS +
                        " WHERE " + EcoTrackDbHelper.COL_FECHA_HABITO + " >= ?",
                selectionArgs
        );
        if (cursorHabitos.moveToFirst()) {
            totalHogar = cursorHabitos.getDouble(0);
        }
        cursorHabitos.close();

        close();

        Map<String, Double> resultados = new HashMap<>();
        resultados.put("viajes", totalViajes);
        resultados.put("hogar", totalHogar);

        return resultados;
    }

    //historial detallado lee ambas tablas y las une en una sola lista
    public List<HistorialItem> getHistorialCompleto() {
        List<HistorialItem> listaItems = new ArrayList<>();
        open();

        Cursor cursorViajes = database.query(EcoTrackDbHelper.TABLA_VIAJES, null, null, null, null, null, null);

        while (cursorViajes.moveToNext()) {

            String vehiculo = cursorViajes.getString(cursorViajes.getColumnIndexOrThrow(EcoTrackDbHelper.COL_VEHICULO));
            double distancia = cursorViajes.getDouble(cursorViajes.getColumnIndexOrThrow(EcoTrackDbHelper.COL_DISTANCIA));
            long fecha = cursorViajes.getLong(cursorViajes.getColumnIndexOrThrow(EcoTrackDbHelper.COL_FECHA_VIAJE));
            double co2 = cursorViajes.getDouble(cursorViajes.getColumnIndexOrThrow(EcoTrackDbHelper.COL_CO2_GENERADO));


            String titulo = String.format("%s (%.1f km)", vehiculo, distancia);
            listaItems.add(new HistorialItem(titulo, fecha, co2, true));
        }
        cursorViajes.close();

        Cursor cursorHabitos = database.query(EcoTrackDbHelper.TABLA_HABITOS, null, null, null, null, null, null);

        while (cursorHabitos.moveToNext()) {
            String titulo = cursorHabitos.getString(cursorHabitos.getColumnIndexOrThrow(EcoTrackDbHelper.COL_HABITO));
            long fecha = cursorHabitos.getLong(cursorHabitos.getColumnIndexOrThrow(EcoTrackDbHelper.COL_FECHA_HABITO));
            double co2 = cursorHabitos.getDouble(cursorHabitos.getColumnIndexOrThrow(EcoTrackDbHelper.COL_CO2_AHORRADO));

            listaItems.add(new HistorialItem(titulo, fecha, co2, false));
        }
        cursorHabitos.close();

        close();
        //se ordena por el mas nuevo al inicio
        Collections.sort(listaItems, new Comparator<HistorialItem>() {
            @Override
            public int compare(HistorialItem o1, HistorialItem o2) {
                return Long.compare(o2.getFechaMillis(), o1.getFechaMillis());
            }
        });

        return listaItems;
    }
}