package com.example.ecotrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.Map;

// Clase de acceso a la base de datos, se encarga de todas las operaciones con la base de datos (crud)
public class DbManager {

    private EcoTrackDbHelper dbHelper;
    private SQLiteDatabase database;

    //constructor
    public DbManager(Context context) {
        dbHelper = new EcoTrackDbHelper(context);
    }

    //abre la base de datos para escribir en ella
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    //cierra la coneion con la base de datos
    public void close() {
        dbHelper.close();
    }

    //metodos para insertar
    //inserta un nuevo registro de un nuevo viaje
    public void insertarViaje(String vehiculo, double distancia, double co2Generado) {
        open(); // Abrimos la BD

        ContentValues values = new ContentValues();
        values.put(EcoTrackDbHelper.COL_FECHA_VIAJE, System.currentTimeMillis()); // guarda la fecha actual
        values.put(EcoTrackDbHelper.COL_VEHICULO, vehiculo);
        values.put(EcoTrackDbHelper.COL_DISTANCIA, distancia);
        values.put(EcoTrackDbHelper.COL_CO2_GENERADO, co2Generado);

        database.insert(EcoTrackDbHelper.TABLA_VIAJES, null, values);

        close(); // cierra la BD
    }

    //inserta un nuevo registro de un nuevo habito de hogar
    public void insertarHabito(String habito, double co2Ahorrado) {
        open();

        ContentValues values = new ContentValues();
        values.put(EcoTrackDbHelper.COL_FECHA_HABITO, System.currentTimeMillis()); // Guarda la fecha actual
        values.put(EcoTrackDbHelper.COL_HABITO, habito);
        values.put(EcoTrackDbHelper.COL_CO2_AHORRADO, co2Ahorrado);

        database.insert(EcoTrackDbHelper.TABLA_HABITOS, null, values);

        close();
    }

    //metodos para leer
    //lee la BD, sumatodo el CO2 para la grafica del Dashboard y devuelve todos los viajes registrados

    public Map<String, Double> getProgresoSemanal() {
        open();

        double totalViajes = 0;
        double totalHogar = 0;

        //suma toodo el CO2 de la tabla viajes
        Cursor cursorViajes = database.rawQuery(
                "SELECT SUM(" + EcoTrackDbHelper.COL_CO2_GENERADO + ") FROM " + EcoTrackDbHelper.TABLA_VIAJES,
                null
        );
        if (cursorViajes.moveToFirst()) {
            totalViajes = cursorViajes.getDouble(0);
        }
        cursorViajes.close();

        //suma toodo el CO2 de la tabla HabitosHogar
        Cursor cursorHabitos = database.rawQuery(
                "SELECT SUM(" + EcoTrackDbHelper.COL_CO2_AHORRADO + ") FROM " + EcoTrackDbHelper.TABLA_HABITOS,
                null
        );
        if (cursorHabitos.moveToFirst()) {
            totalHogar = cursorHabitos.getDouble(0);
        }
        cursorHabitos.close();

        close();

        //devolver los resultados en un hashmap
        Map<String, Double> resultados = new HashMap<>();
        resultados.put("viajes", totalViajes);
        resultados.put("hogar", totalHogar);

        return resultados;
    }
}