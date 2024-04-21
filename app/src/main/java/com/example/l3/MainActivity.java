package com.example.l3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(tengoInternet1()){
            Toast.makeText(MainActivity.this, "Success Toast: Hay conexión a internet", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "Error Toast: No hay conexión a internet", Toast.LENGTH_SHORT).show();
        }

    }
    public void primoVista (View view){
        Intent intent = new Intent(this, PrimoActivity.class);
        startActivity(intent);
    }
    public void peliculaVista (View view){
        Intent intent = new Intent(this, PeliculaActivity.class);
        startActivity(intent);
    }

    public boolean tengoInternet1() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test-internet", "Internet: " + tieneInternet);

        return tieneInternet;
    }













}