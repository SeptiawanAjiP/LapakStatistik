package com.example.ajipradana.lapak.UserInterface;

/**
 * Created by Septiawan Aji on 4/17/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.ajipradana.lapak.Koneksi.AlamatUrl;
import com.example.ajipradana.lapak.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {

    /*
    Splasscreen untuk tampil ketika pertama kali aplikasi dibuka,
    pad splashscreen ini berisi pengecekan apakah sudah pernah login atau belum share preference
     */
    AlamatUrl alamat = new AlamatUrl();


    ArrayList<HashMap<String,String>> arrayList;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, Navigation.class);
                i.putExtra("halaman_utama",alamat.getKodeMenuUtama()); //melemparkan parameter berupa angka 0,yang digunakakn untuk mengakses koneksi.php
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}