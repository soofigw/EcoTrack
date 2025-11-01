package com.example.ecotrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//plano de la bdd sqlite
public class EcoTrackDbHelper extends SQLiteOpenHelper {

    //si en el futuro se cambian las tablas, incrementa la version y el onUpgrade() se llamara.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EcoTrack.db";



    //Tabla Viajes
    public static final String TABLA_VIAJES = "Viajes";
    public static final String COL_ID_VIAJE = "id";
    public static final String COL_FECHA_VIAJE = "fecha"; //long (num) para guardar la fecha
    public static final String COL_VEHICULO = "tipo_vehiculo"; // "Auto", "Bus", "Bici"
    public static final String COL_DISTANCIA = "distancia_km"; // 10.5
    public static final String COL_CO2_GENERADO = "kg_co2_generados"; // 1.8

    //tabla habitosHogar
    public static final String TABLA_HABITOS = "HabitosHogar";
    public static final String COL_ID_HABITO = "id";
    public static final String COL_FECHA_HABITO = "fecha";
    public static final String COL_HABITO = "nombre_habito"; // "Reciclar organico", "evitar botellas"
    public static final String COL_CO2_AHORRADO = "kg_co2_ahorrados"; // 0.6



    private static final String SQL_CREAR_TABLA_VIAJES =
            "CREATE TABLE " + TABLA_VIAJES + " (" +
                    COL_ID_VIAJE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_FECHA_VIAJE + " INTEGER," +
                    COL_VEHICULO + " TEXT," +
                    COL_DISTANCIA + " REAL," +
                    COL_CO2_GENERADO + " REAL)";

    //com tabla HabitosHogar
    private static final String SQL_CREAR_TABLA_HABITOS =
            "CREATE TABLE " + TABLA_HABITOS + " (" +
                    COL_ID_HABITO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_FECHA_HABITO + " INTEGER," +
                    COL_HABITO + " TEXT," +
                    COL_CO2_AHORRADO + " REAL)";

    public EcoTrackDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //llama una sola vez, la primera vez que la app intenta acceder a la base de datos. aqui es donde se ejecuta el SQL para crear las tablas.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR_TABLA_VIAJES);
        db.execSQL(SQL_CREAR_TABLA_HABITOS);
    }

    //llama si actualiza la version de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLA_VIAJES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_HABITOS);

        onCreate(db);
    }
}