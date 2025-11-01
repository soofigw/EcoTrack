package com.example.ecotrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DesafioManager {

    //desafios posibles
    private static final List<String> TODOS_LOS_DESAFIOS = Arrays.asList(
            "¡Registra 3 hábitos de 'Reciclar' esta semana!",
            "Evita plásticos de un solo uso por 2 días seguidos.",
            "Registra 1 viaje en 'Bici' o 'Caminando'.",
            "Mantén tu consumo eléctrico bajo (registra 'Reducir Electricidad' 3 veces).",
            "Usa una bolsa de tela (registra 'Evitar Bolsas').",
            "¡Registra un viaje en 'Transporte Público'!"
    );

    // obtiene 2 desafios aleatorios de la lista principal, luego regresa un array de String con 2 desafios
    public String[] getDosDesafiosRandom() {
        //lista
        List<String> listaParaBarajar = new ArrayList<>(TODOS_LOS_DESAFIOS);

        //barajar lista (shuffle)
        Collections.shuffle(listaParaBarajar);

        //regresa los primeros 2 elem de la lista barajada
        return new String[] {
                listaParaBarajar.get(0),
                listaParaBarajar.get(1)
        };
    }
}