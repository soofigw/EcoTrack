package com.example.ecotrack;

import java.util.HashMap;
import java.util.Map;

// clase de calculo de CO2 y ahorros
public class CalculadoraCO2 {

    // viajes (en kg de CO2 por km)
    private static final double FACTOR_AUTO_GASOLINA = 0.18;
    private static final double FACTOR_TRANSPORTE_PUBLICO = 0.08; // (camion)
    private static final double FACTOR_MOTO = 0.10;
    private static final double FACTOR_BICI = 0.0;
    private static final double FACTOR_CAMINAR = 0.0;

    //ahorros de Hogar (en kg de CO2 fijos por accion)
    //estos valores son estimaciones para el proyecto, si despues llevo este proyecto mas formal se verificaran aun mas los valores
    private static final double AHORRO_RECICLAR_ORGANICO = 0.6;
    private static final double AHORRO_RECICLAR_INORGANICO = 0.4;
    private static final double AHORRO_REDUCIR_LUZ = 0.3;
    private static final double AHORRO_DESCONECTAR = 0.2;
    private static final double AHORRO_EVITAR_BOTELLAS = 0.15;
    private static final double AHORRO_EVITAR_BOLSAS = 0.1;





    // calcula el impacto de CO2 de un viaje específico
    public double calcularImpactoViaje(String vehiculo, double km) {
        switch (vehiculo) {
            case "Auto (Gasolina)":
                return km * FACTOR_AUTO_GASOLINA;
            case "Transporte Público":
                return km * FACTOR_TRANSPORTE_PUBLICO;
            case "Motocicleta":
                return km * FACTOR_MOTO;
            case "Bicicleta":
            case "Caminar":
                return 0.0; //es cero pk no tiene impacto
            default:
                return 0.0;
        }
    }




    //calcula el ahorro de CO2 de un habito de hogar especifico
    public double getAhorroHabito(String habito) {
        switch (habito) {
            case "Reciclar Orgánico":
                return AHORRO_RECICLAR_ORGANICO;
            case "Separar Inorgánicos":
                return AHORRO_RECICLAR_INORGANICO;
            case "Reducir Consumo (Luz)":
                return AHORRO_REDUCIR_LUZ;
            case "Desconectar Aparatos":
                return AHORRO_DESCONECTAR;
            case "Evitar Botellas (Termo)":
                return AHORRO_EVITAR_BOTELLAS;
            case "Evitar Bolsas (Tela)":
                return AHORRO_EVITAR_BOLSAS;
            default:
                return 0.0;
        }
    }



    //recomendacion en base al viaje actual dando una alternativa ecologica
    public String getRecomendacionViaje(String vehiculoActual, double km) {
        double impactoActual = calcularImpactoViaje(vehiculoActual, km);
        double impactoAlternativo;
        String nombreAlternativo;

        //si el usuario usa una alternativa sostenible, se regresa un mensaje de felicitacion
        if (vehiculoActual.equals("Bicicleta") || vehiculoActual.equals("Caminar")) {
            return "¡Felicidades! Elegiste la opción más ecológica.";
        }

        //si se uso auto o moto, la recom es el transporte publico
        if (vehiculoActual.equals("Auto (Gasolina)") || vehiculoActual.equals("Motocicleta")) {
            impactoAlternativo = calcularImpactoViaje("Transporte Público", km);
            nombreAlternativo = "Transporte Público";
        } else {

            //si usa transporte publico se le recomienda la bicicleta
            impactoAlternativo = calcularImpactoViaje("Bicicleta", km);
            nombreAlternativo = "Bicicleta";
        }

        double ahorro = impactoActual - impactoAlternativo;


        String impactoFormateado = String.format("%.2f", impactoActual);
        String ahorroFormateado = String.format("%.2f", ahorro);
        String altFormateada = String.format("%.2f", impactoAlternativo);

        if (ahorro > 0) {
            return String.format("Tu viaje generó %.2f kg de CO2. ¡Usando %s para este viaje generarias tan solo %.2f kg (un ahorro de %.2f kg)!",
                    impactoActual, nombreAlternativo, impactoAlternativo, ahorro);
        } else {
            return String.format("¡Buen trabajo! Tu viaje generó %.2f kg de CO2.", impactoActual);
        }
    }
}