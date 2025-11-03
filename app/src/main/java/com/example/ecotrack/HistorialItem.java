package com.example.ecotrack;

public class HistorialItem {

    private String titulo;
    private long fechaMillis;
    private double co2;
    private boolean esViaje;


    public HistorialItem(String titulo, long fechaMillis, double co2, boolean esViaje) {
        this.titulo = titulo;
        this.fechaMillis = fechaMillis;
        this.co2 = co2;
        this.esViaje = esViaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public long getFechaMillis() {
        return fechaMillis;
    }

    public double getCo2() {
        return co2;
    }

    public boolean esViaje() {
        return esViaje;
    }
}