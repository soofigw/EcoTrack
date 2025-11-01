package com.example.ecotrack; // (Asegúrate que sea tu nombre de paquete)

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit; // ¡Importante para el cálculo de tiempo!

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

    // --- MÉTODOS PARA INSERTAR (Sin cambios) ---

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

    // --- MÉTODO PARA LEER (¡Aquí está el cambio!) ---

    /**
     * Lee la BD y suma el CO2 de los ÚLTIMOS 7 DÍAS para la gráfica.
     * @return un Mapa con el total de "viajes" y "hogar".
     */
    public Map<String, Double> getProgresoSemanal() {
        open(); // Abrimos la BD (para leer)

        // --- ¡AQUÍ ESTÁ LA LÓGICA NUEVA! ---
        // 1. Calcular el "timestamp" de hace 7 días.
        long sieteDiasEnMilis = TimeUnit.DAYS.toMillis(7);
        long timestampHace7Dias = System.currentTimeMillis() - sieteDiasEnMilis;

        // 2. Convertir el timestamp a String para la consulta SQL
        String[] selectionArgs = { String.valueOf(timestampHace7Dias) };
        // --- FIN DE LA LÓGICA NUEVA ---

        double totalViajes = 0;
        double totalHogar = 0;

        // 3. Sumar el CO2 de Viajes DE LOS ÚLTIMOS 7 DÍAS
        Cursor cursorViajes = database.rawQuery(
                "SELECT SUM(" + EcoTrackDbHelper.COL_CO2_GENERADO + ") FROM " + EcoTrackDbHelper.TABLA_VIAJES +
                        " WHERE " + EcoTrackDbHelper.COL_FECHA_VIAJE + " >= ?", // ¡El filtro WHERE!
                selectionArgs // El argumento ("?") es el timestamp de hace 7 días
        );
        if (cursorViajes.moveToFirst()) {
            totalViajes = cursorViajes.getDouble(0);
        }
        cursorViajes.close();

        // 4. Sumar el CO2 de Habitos DE LOS ÚLTIMOS 7 DÍAS
        Cursor cursorHabitos = database.rawQuery(
                "SELECT SUM(" + EcoTrackDbHelper.COL_CO2_AHORRADO + ") FROM " + EcoTrackDbHelper.TABLA_HABITOS +
                        " WHERE " + EcoTrackDbHelper.COL_FECHA_HABITO + " >= ?", // ¡El filtro WHERE!
                selectionArgs
        );
        if (cursorHabitos.moveToFirst()) {
            totalHogar = cursorHabitos.getDouble(0);
        }
        cursorHabitos.close();

        close(); // Cerramos la BD

        // 5. Devolver los resultados
        Map<String, Double> resultados = new HashMap<>();
        resultados.put("viajes", totalViajes);
        resultados.put("hogar", totalHogar);

        return resultados;
    }
}