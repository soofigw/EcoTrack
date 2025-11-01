package com.example.ecotrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    //splash duracion
    private static final int SPLASH_DURATION = 2000; // 2 seg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // oculta la barra de accion
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //handler para retrasar el inicio de la main
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // intent para iniciar la main
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);

                //cierra la actividad para que el usuario no pueda "regresar" a ella
                finish();
            }
        }, SPLASH_DURATION);
    }
}